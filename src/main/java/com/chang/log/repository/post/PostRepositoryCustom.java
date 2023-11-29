package com.chang.log.repository.post;

import com.chang.log.domain.Post;
import com.chang.log.request.post.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
