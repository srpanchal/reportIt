package com.reportit.reportitbackend.impl;

import com.reportit.reportitbackend.Department;
import com.reportit.reportitbackend.DepartmentRepository;
import com.reportit.reportitbackend.DepartmentService;
import com.textrazor.AnalysisException;
import com.textrazor.NetworkException;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public List<Department> getByCategoryAndRegion(String category, String region) {
        return departmentRepository.findByIssueTypesContainingAndRegionContaining(category, region);
    }

  @Override
  public List<Department> getRelevantDepts(String message) {
    Map<Department, List<String>> map = new HashMap<>();
    List<Department> relevantDepts = new ArrayList<>();
    getAllDepartments().stream().collect(Collectors.toMap(d -> d, Department::getName));
    List<ExtractedResult> resultList = new ArrayList<>();
    for (Map.Entry<Department, List<String>> entry : map.entrySet()) {
      resultList = FuzzySearch.extractAll(message.toLowerCase(), entry.getValue(), 70);
      if(!CollectionUtils.isEmpty(resultList)){
        relevantDepts.add(entry.getKey());
      }
    }
    return relevantDepts;
  }

  public static void main(String[] args) throws NetworkException, AnalysisException {

    String message = "This road in Bellandur needs to be fixed asap .It is very dangerous water";

    Map<String, List<String>> map = new HashMap<>();
    map.put("BBMP", Arrays.asList("road", "potholes", "water"));
    map.put("police", Arrays.asList("Thief", "chain-snatching"));


    for (Map.Entry<String,List<String>> entry : map.entrySet()){
      List<ExtractedResult> resultList = FuzzySearch.extractAll(message.toLowerCase(),
          entry.getValue(), 45);
      int score = resultList.stream().mapToInt(ExtractedResult::getScore).sum();
      System.out.println(entry.getKey() + " : " + score);
    }

  }
}
