package com.chang.log.repository.post;

import com.chang.log.domain.Post;

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
    public Page<Post> getList (int page , int size, String title, String content, String writer) {

        QPost qPost = QPost.post;
        JPAQuery<Post> jpaQuery = queryFactory.selectFrom(qPost);
        JPAQuery<Long> countQuery = queryFactory.select(qPost.count()).from(qPost);

        if(!title.isEmpty()) {
            jpaQuery.where(qPost.title.like("%"+title+"%"));
            countQuery.where(qPost.title.like("%"+title+"%"));
        }
        if(!content.isEmpty()) {
            jpaQuery.where(qPost.content.like("%"+content+"%"));
            countQuery.where(qPost.content.like("%"+content+"%"));
        }
        if(!writer.isEmpty()) {
            jpaQuery.where(qPost.writer.like("%"+writer+"%"));
            countQuery.where(qPost.writer.like("%"+writer+"%"));
        }
        // 모든 요소를 순회함, 대량의 데이터를 집계할 때 좋지않음
//        long total = countQuery.stream().count();
        long total = countQuery.fetchOne();

        List<Post> posts = jpaQuery
                .offset((long) size * (page - 1))
                .limit(size)
                .orderBy(qPost.createAt.desc())
                .fetch();

        Pageable pageable = PageRequest.of(page, size);

        return new PageImpl<>(posts, pageable, total);

    }
}

