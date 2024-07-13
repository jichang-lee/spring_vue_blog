package com.chang.log.request.post;

import com.chang.log.domain.Comment;
import com.chang.log.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreate {

    private String title;
    private String content;
    private String writer;
    private LocalDate createAt;
    private User user;
//    private List<MultipartFile> postFileList;

}
