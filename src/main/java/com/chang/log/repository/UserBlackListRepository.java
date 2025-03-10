package com.chang.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chang.log.domain.UserBlackList;

public interface UserBlackListRepository extends JpaRepository<UserBlackList,Long> {
}
