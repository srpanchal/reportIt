package com.reportit.reportitbackend;

import java.io.IOException;
import java.util.List;

public interface ImageProcessService {
    List<String> getImageLabels(byte []imageBytes) throws IOException;
    boolean isNotSafe(byte []imageBytes) throws IOException;

}
