package com.reportit.reportitbackend.impl;

import com.reportit.reportitbackend.Department;
import com.reportit.reportitbackend.DepartmentService;
import com.reportit.reportitbackend.Issue;
import com.reportit.reportitbackend.IssueRepository;
import com.reportit.reportitbackend.IssueService;
import com.reportit.reportitbackend.PublisherService;
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

import java.util.List;

@Service
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
        if(page != null && size != null){
            return issueRepository.findAll(new PageRequest(page, size));
        }
        return new PageImpl<>(issueRepository.findAll());
    }

    @Override
    public List<Issue> getAllIssuesByLocation(Point p, Distance d) {
      return issueRepository.findByLocationNear(p, d);
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
        if(issue.getVotes() > upvotesLimit){
            //tweet/email
            List<Department> departments = departmentService.getByCategoryAndRegion(issue.getCategory(), issue.getAddress());
            if(!CollectionUtils.isEmpty(departments)){

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
}
