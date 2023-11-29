package com.chang.log.repository.post;

import com.chang.log.domain.Post;
import com.chang.log.domain.QPost;
import com.chang.log.request.post.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
