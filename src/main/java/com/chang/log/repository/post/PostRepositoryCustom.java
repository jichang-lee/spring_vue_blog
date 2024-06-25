package com.chang.log.repository.post;

import com.chang.log.domain.Post;
import com.chang.log.request.post.PostSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostRepositoryCustom {

    Page<Post> getList(int page , int size , String title, String content, String writer);
}
