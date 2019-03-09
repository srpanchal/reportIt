package com.reportit.reportitbackend;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DepartmentRepository extends MongoRepository<Department, String> {
    List<Department> findByIssueTypesContainingAndRegionContaining(String issueType, String region);
}
