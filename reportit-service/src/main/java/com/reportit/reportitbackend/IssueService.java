package com.reportit.reportitbackend;

import org.springframework.data.domain.Page;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.util.List;

public interface IssueService {

    Issue saveIssue(Issue issue);

    Page<Issue> getAllIssues(Integer page, Integer size);

    List<Issue> getAllIssuesByLocation(Point p, Distance d, Integer page, Integer size);

    long upvote(String id);

    long downvote(String id);

    void updateIssueStatus(String issueID, StatusEnum status);
}
