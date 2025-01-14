package com.chang.log.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Payload {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String mertInfo;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String headerInfo;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String salesInfo;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String payInfo;
}
