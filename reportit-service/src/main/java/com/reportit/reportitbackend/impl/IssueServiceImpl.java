package com.reportit.reportitbackend.impl;

import com.reportit.reportitbackend.Department;
import com.reportit.reportitbackend.DepartmentService;
import com.reportit.reportitbackend.Issue;
import com.reportit.reportitbackend.IssueRepository;
import com.reportit.reportitbackend.IssueService;
import com.reportit.reportitbackend.PlatformEnum;
import com.reportit.reportitbackend.PublisherService;
import com.reportit.reportitbackend.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Value("${upvotes.limit}")
    private int upvotesLimit;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PublisherService publisherService;

    @Override
    public Issue saveIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    @Override
    public Page<Issue> getAllIssues(Integer page, Integer size) {
        List<StatusEnum> statusEnums = new ArrayList<>();
        statusEnums.add(StatusEnum.REJECTED);
        if(page != null || size != null){
            return issueRepository.findByStatusNotIn(statusEnums,new PageRequest(page, size));
        }
        List<Issue>  issues = issueRepository.findByStatusNotIn(statusEnums);
        return new PageImpl<>(issues, new PageRequest(0, 10),issues.size());
    }

    @Override
    public List<Issue> getAllIssuesByLocation(Point p, Distance d, Integer page, Integer size) {
      return issueRepository.findByLocationNear(p, d, new PageRequest(page, size));
    }

    @Override
    public long upvote(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update();
        update.inc("votes");

        Issue issue = mongoOperations.findAndModify(
                query, update,
                new FindAndModifyOptions().returnNew(true), Issue.class);
        if(issue.getVotes() >= upvotesLimit){
            //tweet/email
            List<Department> departments = departmentService.getByCategoryAndRegion(issue.getCategory(), issue.getAddress());
            if(!CollectionUtils.isEmpty(departments) && !CollectionUtils.isEmpty(issue.getImages())){
                List<String> tweeterHandles = departments.stream().map(d -> d.getContactInfo()
                        .get(PlatformEnum.TWITTER)).filter(Objects::nonNull)
                        .collect(Collectors.toList());
                String tweet = publisherService.createTweet(issue, tweeterHandles);
                publisherService.sendTweet(tweet, issue.getImages().get(0));
                List<String> emailIds = departments.stream().map(d -> d.getContactInfo()
                        .get(PlatformEnum.EMAIL)).filter(Objects::nonNull)
                        .collect(Collectors.toList());
                publisherService.sendEmailWithAttachment(String.join(",", emailIds),
                        "report.it.pls@gmail.com", "ReportIt Issue", tweet, issue.getImages().get(0));
            }
        }
        return issue.getVotes();
    }

    @Override
    public long downvote(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update();
        update.inc("votes", -1);

        Issue issue = mongoOperations.findAndModify(
                query, update,
                new FindAndModifyOptions().returnNew(true), Issue.class);

        return issue.getVotes();
    }

    @Override
    public void updateIssueStatus(String issueID, StatusEnum status) {
        Optional<Issue> optional = issueRepository.findById(issueID);
        if(optional.isPresent()){
            Issue issue = optional.get();
            issue.setStatus(status);
            issueRepository.save(issue);
        }
    }
}
