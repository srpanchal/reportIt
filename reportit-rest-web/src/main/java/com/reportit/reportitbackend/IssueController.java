package com.reportit.reportitbackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
@RequestMapping(value = (IssueController.BASE_PATH))
public class IssueController {

    public static final String BASE_PATH = "/issue";

    @Autowired
    private IssueService issueService;

    @RequestMapping(method = RequestMethod.POST, value = "/save",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveIssue(@RequestBody Issue issue){
        issueService.saveIssue(issue);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Issue> getAllIssues(){
        return issueService.getAllIssues();
    }
}
