package com.reportit.reportitbackend;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.util.List;

public interface IssueService {

    void saveIssue(Issue issue);

    List<Issue> getAllIssues();

    List<Issue> getAllIssuesByLocation(Point p, Distance d);

    void upvote(String id);

    void downvote(String id);
}
