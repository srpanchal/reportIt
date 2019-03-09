package com.reportit.reportitbackend;

import java.io.IOException;
import java.util.List;

public interface ImageProcessService {
    List<String> getImageLabels(String fileName) throws IOException;
    boolean isNotSafe(String filePath) throws IOException;

}
