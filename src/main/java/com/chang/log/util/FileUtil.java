package com.chang.log.util;

import com.chang.log.response.FileResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

//Bean
@Component
public class FileUtil {

    @Value("${file.baseDir}")
    private String uploadDir;

    public FileResponse getFile(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString().replace("-", " ");

        String originalFilename = file.getOriginalFilename();

        assert originalFilename != null : "업로드한 파일명이 비어있습니다.";
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = uuid + extension;
        Path directory = Paths.get(uploadDir);
        //디렉토리 없으면 생성
        //현재 로컬 환경 경로는 맥으로 되어있는데 실무에서는 S3 경로
        Files.createDirectories(directory);
        Path path = directory.resolve(newFileName);
        file.transferTo(path);

        return FileResponse.builder()
                .originalFilename(originalFilename)
                .storedFilename(newFileName)
                .size((int) file.getSize())
                .filePath(path.toString())
                .build();
    }
}
