package com.chang.log.response;

import lombok.Data;

@Data
public class BarCodeResponse {

	private Long id;
	// private String barcode;
	private String barcodeByteBase64;
}
