package com.reportit.reportitbackend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueModel implements Serializable {
    private static final long serialVersionUID = 112233L;
    private String id;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String title;
    private String description;
    private List<String> images = new ArrayList<>();
    private StatusEnum status;
    private String category;
    private GeoJsonPoint location;
    private long votes = 0L;
    private String address;
    private Double latitude;
    private Double longitude;
    private String userId;
}
