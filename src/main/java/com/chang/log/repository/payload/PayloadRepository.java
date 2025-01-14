package com.chang.log.repository.payload;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chang.log.domain.Payload;

public interface PayloadRepository extends JpaRepository<Payload,Long> {
}
