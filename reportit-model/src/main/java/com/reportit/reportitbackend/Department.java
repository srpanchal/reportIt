package com.reportit.reportitbackend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = Department.COLLECTION_NAME_DEPARTMENT)
public class Department {
    public static final String COLLECTION_NAME_DEPARTMENT = "department_collection";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Version
    private Long version;

    @CreatedDate
    private Date createdDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private Date updatedDate;

    private String name;

    private String description;

    private List<String> issueTypes;

    private Map<PlatformEnum, String> contactInfo;

    private List<String> region;
}
