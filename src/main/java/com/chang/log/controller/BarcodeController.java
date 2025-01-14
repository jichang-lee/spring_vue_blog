package com.chang.log.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chang.log.response.BarCodeResponse;
import com.chang.log.response.PayInfoResponse;
import com.chang.log.service.BarcodeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/barcode")
@Slf4j
public class BarcodeController {

	private final BarcodeService barcodeService;

	@GetMapping("/write")
	public ResponseEntity<byte[]> generateBarcode(@RequestParam("code") String code) throws Exception {
		barcodeService.saveBarcodeImage(code);
		byte[] bytes = generateImageBytes(code);
		log.info("bytes ={}", bytes);
		return ResponseEntity.status(HttpStatus.OK).body(bytes);
	}

	@PostMapping("/read")
	public ResponseEntity<BarCodeResponse> readBarcode(@RequestParam("code") String code) throws Exception {
		BarCodeResponse barCodeResponse = barcodeService.readBarcode(code);
		barcodeService.generatePayloadInfo();
		try {
			return ResponseEntity.status(HttpStatus.OK).body(barCodeResponse);
		} catch (Exception e) {
			// throw new Exception("Error while decoding barcode: " + e.getMessage(), e);
			log.info("e ={}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(barCodeResponse);
		}
	}

	private byte[] generateImageBytes(String code) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		BufferedImage bufferedImage = barcodeService.generateBarcode(code);
		ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}

	@GetMapping("/payInfo")
	public ResponseEntity<PayInfoResponse> payInfo(@RequestParam("id") Long id) {
		PayInfoResponse payInfoResponse = barcodeService.payloadInfo(id);
		try {
			return ResponseEntity.status(HttpStatus.OK).body(payInfoResponse);
		}catch (Exception e) {
			log.info("e= {}",e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(payInfoResponse);
		}
	}

}
