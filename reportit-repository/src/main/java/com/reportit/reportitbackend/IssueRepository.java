package com.reportit.reportitbackend;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends MongoRepository<Issue, String> {

  List<Issue> findByLocationNear(Point p, Distance d, PageRequest pageRequest);
}
