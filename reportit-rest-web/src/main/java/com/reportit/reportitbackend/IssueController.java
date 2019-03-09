package com.reportit.reportitbackend;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequestMapping(value = (IssueController.BASE_PATH))
@Api(value = "Issue controller")
public class IssueController {

    public static final String BASE_PATH = "/issue";

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @Value("${image.server.path}")
    private String imageServerPath;

    @Value("${user.proximityinkms}")
    private Double userProximity;

    @Value(("${image.directory}"))
    private String imageDirectory;

    private Issue convertToEntity(IssueModel issueModel) {
//        return Issue.builder()
//                .id(issueModel.getId())
//                .title(issueModel.getTitle())
//                .description(issueModel.getDescription())
//                .category(issueModel.getCategory())
//                .status(issueModel.getStatus())
//                .build();
        Issue issue = new Issue();
        BeanUtils.copyProperties(issueModel, issue);

//        BeanUtils.copyProperties(issueModel, issue, "images");

//        for(byte[] bytes : issueModel.getImages()){
//            try {
//                InputStream in = new ByteArrayInputStream(bytes);
//                BufferedImage bImageFromConvert = ImageIO.read(in);
//                String path = "/opt/images/" + issueModel.getTitle() + RandomStringUtils.random(10);
//                ImageIO.write(bImageFromConvert, "jpg", new File(path));
//                issue.getImages().add(path);
//            } catch (IOException e) {
//                log.error("error in saving image", e);
//            }
//        }

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
        if(!CollectionUtils.isEmpty(issue.getImages())){
            issueModel.setImages(issue.getImages().stream().map(u -> imageServerPath + u).collect(Collectors.toList()));
        }

//        BeanUtils.copyProperties(issue, issueModel, "images");
//        for(String path: issue.getImages()){
//            try {
//                BufferedImage originalImage = ImageIO.read(new File(path));
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                ImageIO.write(originalImage, "jpg", baos);
//                baos.flush();
//                issueModel.getImages().add(baos.toByteArray());
//                baos.close();
//            } catch (IOException e) {
//                log.error("error in getting image", e);
//            }
//        }
        return issueModel;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getAll")
    public Page<IssueModel> getAllIssues(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size){
        log.info("getAll");
        Page<Issue> issues = issueService.getAllIssues(page, size);
        return new PageImpl<>(issues.getContent().stream().map(issue->convertToWebModel(issue)).collect(Collectors.toList()), issues.getPageable(), issues.getTotalElements());
    }

  @RequestMapping(method = RequestMethod.POST, value = "/save", produces =
      MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public void saveIssue(@RequestBody IssueModel issueModel) {
        Issue issue = convertToEntity(issueModel);
    issue.setLocation(new GeoJsonPoint(issueModel.getLongitude(), issueModel.getLatitude()));
    issueService.saveIssue(issue);
    userService.addReportedIssue(issueModel.getUserId(), issue);
 //   userService.getFCMTokensOfNearbyUsers(issue.getLocation(), userProximity);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/get/nearest", produces =
      MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "nearest")
  public final List<Issue> getLocationsByProximity(@RequestParam(value = "lat", defaultValue = "12.91835") Double latitude,
      @RequestParam(value = "long", defaultValue = "77.64905") Double longitude, @RequestParam(value = "d", defaultValue = "5") double distance, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
    return this.issueService.getAllIssuesByLocation(new Point(latitude, longitude),
        new Distance(distance, Metrics.KILOMETERS), page, size);
  }

  @PostMapping("/uploadFile")
  public String singleFileUpload( @RequestParam("file") MultipartFile file){
      int imageCode = UUID.randomUUID().hashCode();

      try {
          byte[] bytes=file.getBytes();
          InputStream in = new ByteArrayInputStream(bytes);
          BufferedImage bImageFromConvert = ImageIO.read(in);
          ImageIO.write(bImageFromConvert, "jpg", new File(
              imageDirectory+imageCode+".jpg"));
      } catch (IOException e) {
          e.printStackTrace();
      }
        return  imageCode+".jpg";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/upvote")
  @ApiOperation(value = "upvote")
    public long upvote(@RequestParam String id){
        return issueService.upvote(id);
  }

    @RequestMapping(method = RequestMethod.GET, value = "/downvote")
    @ApiOperation(value = "downvote")
    public long downvote(@RequestParam String id){
        return issueService.downvote(id);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/image",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "image")
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

    @PostMapping("/updateStatus/{issueId}")
    public void updateIssueStatus(@PathVariable("issueId")String issueId, @RequestParam StatusEnum status){
        issueService.updateIssueStatus(issueId, status);
    }
}
