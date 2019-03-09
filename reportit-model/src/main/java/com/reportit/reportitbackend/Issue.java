package com.reportit.reportitbackend;

import lombok.AllArgsConstructor;
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
import java.util.Date;
import java.util.List;

@Data
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
    private List<String> images;
    private StatusEnum status;
    private String category;
    private Long votes;
    private GeoJsonPoint location;

    public void upvote() {
      this.votes += 1;
    }

    public void downvote() {
      this.votes += 1;
    }
}
