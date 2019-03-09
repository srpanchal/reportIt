package com.reportit.reportitbackend;

import java.util.List;

public interface DepartmentService {

    void saveDepartment(Department department);

    List<Department> getAllDepartments();
}
