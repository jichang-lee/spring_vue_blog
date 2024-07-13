package com.chang.log.response;

import lombok.Builder;
import lombok.Data;

@Data
public class FileResponse {

    private String originalFilename;
    private String storedFilename;
    private String filePath;
    private int size;

    @Builder
    public FileResponse(String originalFilename, String storedFilename, String filePath, int size) {
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
        this.filePath = filePath;
        this.size = size;
    }
}
