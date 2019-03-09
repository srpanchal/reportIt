package com.reportit.reportitbackend.impl;

import com.reportit.reportitbackend.Issue;
import com.reportit.reportitbackend.IssueRepository;
import com.reportit.reportitbackend.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void saveIssue(Issue issue) {
        issueRepository.save(issue);
    }

    @Override
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    @Override
    public List<Issue> getAllIssuesByLocation(Point p, Distance d) {
      return issueRepository.findByLocationNear(p, d);
    }

    @Override
    public void upvote(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update();
        update.inc("votes");

        mongoOperations.findAndModify(
                query, update,
                new FindAndModifyOptions().returnNew(true), Issue.class);
    }

    @Override
    public void downvote(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update();
        update.inc("votes", -1);

        mongoOperations.findAndModify(
                query, update,
                new FindAndModifyOptions().returnNew(true), Issue.class);
    }
}
