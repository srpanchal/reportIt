package com.reportit.reportitbackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping(value = (DepartmentController.BASE_PATH))
public class DepartmentController {

    public static final String BASE_PATH = "/department";

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.POST, value = "/save",
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveUser(@RequestBody Department department) {
        departmentService.saveDepartment(department);
    }

}
