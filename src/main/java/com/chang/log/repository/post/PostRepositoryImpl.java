package com.chang.log.repository.post;

import static com.chang.log.domain.QPost.*;

import com.chang.log.domain.Post;

import com.chang.log.domain.QPost;
import com.chang.log.request.post.PostSearch;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Post> getList (PostSearch postSearch) {
        long totalCount = queryFactory.select(post.count())
            .from(post)
            .fetchFirst();

        List<Post> items = queryFactory.selectFrom(post)
            .limit(postSearch.getSize())
            .offset(postSearch.getOffset())
            .orderBy(post.id.desc())
            .fetch();

        return new PageImpl<>(items, postSearch.getPageable(), totalCount);
    }
}

