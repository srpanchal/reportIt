package com.reportit.reportitbackend.impl;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.Likelihood;
import com.google.cloud.vision.v1.SafeSearchAnnotation;
import com.google.protobuf.ByteString;
import com.reportit.reportitbackend.ImageProcessService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service public class ImageProcessServiceImpl implements ImageProcessService {

    private Float labellingThreshold = 0.5f;

    private Boolean blockAdult = true;
    private Boolean blockSpoof = true;
    private Boolean blockViolence = true;
    private List<Likelihood> blockLikelihood =
        Arrays.asList(Likelihood.POSSIBLE, Likelihood.VERY_LIKELY, Likelihood.LIKELY);

    @Override
    public List<String> getImageLabels(byte []imageBytes) throws IOException {

        List<String> labels = Collections.emptyList();
        // Instantiates a client
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

            // The path to the image file to annotate
            // Reads the image file into memory
            ByteString imgBytes = ByteString.copyFrom(imageBytes);

            // Builds the image annotation request
            //   List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
            //requests.add(request);

            // Performs label detection on the image file
            BatchAnnotateImagesResponse response =
                vision.batchAnnotateImages(Collections.singletonList(request));
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                } else {
                    labels = res.getLabelAnnotationsList().stream().filter(
                        entityAnnotation -> entityAnnotation.getScore() > labellingThreshold)
                        .map(entityAnnotation -> entityAnnotation.getDescription())
                        .collect(Collectors.toList());
                    //                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    //                    annotation.getDescription();
                    //                    annotation.getScore();
                    //                    annotation.getAllFields().forEach((k, v) ->
                    //                        System.out.printf("%s : %s\n", k, v.toString()));
                    //                }
                }
            }
        }
        return labels;
    }

    @Override
    public boolean isNotSafe(byte []imageBytes) throws IOException {
        ByteString imgBytes = ByteString.copyFrom(imageBytes);
        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.SAFE_SEARCH_DETECTION).build();
        AnnotateImageRequest request =
            AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse responses =
                client.batchAnnotateImages(Collections.singletonList(request));
            AnnotateImageResponse response = responses.getResponsesList().get(0);
            if (response.hasError()) {
                // out.printf("Error: %s\n", res.getError().getMessage());
                return false;
            }
            SafeSearchAnnotation annotation = response.getSafeSearchAnnotation();

            return (blockAdult && blockLikelihood.contains(annotation.getAdult())) || (blockSpoof
                && blockLikelihood.contains(annotation.getSpoof())) || (blockViolence
                && blockLikelihood.contains(annotation.getViolence()));
        }
    }
}
