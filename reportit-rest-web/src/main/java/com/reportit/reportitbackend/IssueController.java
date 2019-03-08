package com.reportit.reportitbackend;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController(value = IssueController.BASE_PATH)
public class IssueController {

    public static final String BASE_PATH = "/issue";

    @RequestMapping(method = RequestMethod.GET, value = "/save",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveIssue(){

    }
}
