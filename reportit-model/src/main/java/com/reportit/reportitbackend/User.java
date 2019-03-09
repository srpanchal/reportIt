package com.reportit.reportitbackend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = User.COLLECTION_NAME_USER)
public class User {

    public static final String COLLECTION_NAME_USER = "user_collection";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String userName;
    private String profilePic;

    @DBRef
    private List<Issue> issuesReported;

}
