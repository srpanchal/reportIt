package com.reportit.reportitbackend.impl;

import com.reportit.reportitbackend.DepartmentRepository;
import com.reportit.reportitbackend.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;


}
