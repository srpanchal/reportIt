package com.reportit.reportitbackend;

import java.util.List;

public interface IssueService {
    void saveIssue(Issue issue);

    List<Issue> getAllIssues();
}
