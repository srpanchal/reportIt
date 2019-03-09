package com.reportit.reportitbackend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = Issue.COLLECTION_NAME_ISSUE)
public class Issue {
    public static final String COLLECTION_NAME_ISSUE = "issue_collection";

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

    private String title;
    private String description;
    private List<String> images = new ArrayList<>();
    private StatusEnum status;
    private String category;
    private long votes = 0L;
    private GeoJsonPoint location;
    private String address;
}
