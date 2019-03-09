package com.reportit.reportitbackend;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
@RequestMapping(value = (DepartmentController.BASE_PATH))
@Api(value = "Department controller")
public class DepartmentController {

    public static final String BASE_PATH = "/department";

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.POST, value = "/save",
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "save")
    public void saveUser(@RequestBody Department department) {
        departmentService.saveDepartment(department);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/getAll",
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getAll")
    public List<Department> getAllIssues(){
        return departmentService.getAllDepartments();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByCategoryAndRegion",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getByCategoryAndRegion")
    public List<Department> getByCategoryAndRegion(@RequestParam String category, @RequestParam String region){
        return departmentService.getByCategoryAndRegion(category, region);
    }

  @RequestMapping(method = RequestMethod.POST, value = "/getRelevantDepts", produces =
      MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "getByCategoryAndRegion")
  public List<Department> getRelevantDepts(@RequestBody String message) {
    return departmentService.getRelevantDepts(message);
  }
}
