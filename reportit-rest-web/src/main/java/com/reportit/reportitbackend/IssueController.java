package com.reportit.reportitbackend;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequestMapping(value = (IssueController.BASE_PATH))
public class IssueController {

    public static final String BASE_PATH = "/issue";

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    private Issue convertToEntity(IssueModel issueModel) {
//        return Issue.builder()
//                .id(issueModel.getId())
//                .title(issueModel.getTitle())
//                .description(issueModel.getDescription())
//                .category(issueModel.getCategory())
//                .status(issueModel.getStatus())
//                .build();
        Issue issue = new Issue();
        BeanUtils.copyProperties(issueModel, issue, "images");

        for(byte[] bytes : issueModel.getImages()){
            try {
                InputStream in = new ByteArrayInputStream(bytes);
                BufferedImage bImageFromConvert = ImageIO.read(in);
                String path = "/opt/images/" + issueModel.getTitle() + RandomStringUtils.random(10);
                ImageIO.write(bImageFromConvert, "jpg", new File(path));
                issue.getImages().add(path);
            } catch (IOException e) {
                log.error("error in saving image", e);
            }
        }

        return issue;
    }

    private IssueModel convertToWebModel(Issue issue) {
//        return IssueModel.builder()
//                .id(issue.getId())
//                .title(issue.getTitle())
//                .description(issue.getDescription())
//                .category(issue.getCategory())
//                .status(issue.getStatus())
//                .build();

        IssueModel issueModel = new IssueModel();
        BeanUtils.copyProperties(issue, issueModel, "images");
        for(String path: issue.getImages()){
            try {
                BufferedImage originalImage = ImageIO.read(new File(path));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(originalImage, "jpg", baos);
                baos.flush();
                issueModel.getImages().add(baos.toByteArray());
                baos.close();
            } catch (IOException e) {
                log.error("error in getting image", e);
            }
        }
        return issueModel;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<IssueModel> getAllIssues(){
        log.info("getAll");
        return issueService.getAllIssues().stream().map(this::convertToWebModel).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/image",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String image() throws IOException {
        //log.info(System.getProperty("java.class.path"));
        byte[] imageInByte;
        BufferedImage originalImage = ImageIO.read(new File(
                "/opt/images/1.jpg"));

        // convert BufferedImage to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", baos);
        baos.flush();
        imageInByte = baos.toByteArray();
        baos.close();

        // convert byte array back to BufferedImage
        InputStream in = new ByteArrayInputStream(imageInByte);
        BufferedImage bImageFromConvert = ImageIO.read(in);

        ImageIO.write(bImageFromConvert, "jpg", new File(
                "/opt/images/2.jpg"));
        return "";
    }

  @RequestMapping(method = RequestMethod.POST, value = "/save", produces =
      MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public void saveIssue(@RequestBody Issue issue, @RequestParam("lat") double latitude,
      @RequestParam("long") double longitude, @RequestParam String userId) {
    issue.setLocation(new GeoJsonPoint(longitude, latitude));
    issueService.saveIssue(issue);
    userService.addReportedIssue(userId, issue);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/get/nearest", produces =
      MediaType.APPLICATION_JSON_VALUE)
  public final List<Issue> getLocationsByProximity(@RequestParam("lat") Double latitude,
      @RequestParam("long") Double longitude, @RequestParam("d") double distance) {
    return this.issueService.getAllIssuesByLocation(new Point(latitude, longitude),
        new Distance(distance, Metrics.KILOMETERS));
  }
}
