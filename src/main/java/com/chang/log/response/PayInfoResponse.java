package com.chang.log.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayInfoResponse {

	private String company;
	private String companyAddress;
	private String businessNumber;
	private String companyTel;
	private String paymentInfo;
}
