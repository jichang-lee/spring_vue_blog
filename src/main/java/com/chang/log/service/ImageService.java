package com.chang.log.service;

import com.chang.log.domain.Image;
import com.chang.log.domain.User;
import com.chang.log.repository.ImageRepository;
import com.chang.log.request.user.ProfileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public String uploadImage(MultipartFile file, User user) throws Exception{
        if(file.isEmpty()){
            return null;
        }
        String originName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String extension = originName.substring(originName.lastIndexOf("."));
        String saveName = uuid + extension;
        String savePath = uploadPath + saveName;

        file.transferTo(new File(saveName));

        Image upload = Image.builder()
                .url(savePath)
                .user(user)
                .build();

        imageRepository.save(upload);

        return savePath;

    }
}
