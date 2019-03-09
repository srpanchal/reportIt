package com.reportit.reportitbackend;

import java.util.List;

public interface DepartmentService {

    void saveDepartment(Department department);

    List<Department> getAllDepartments();

    List<Department> getByCategoryAndRegion(String category, String region);
}
