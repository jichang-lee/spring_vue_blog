package com.chang.log.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.chang.log.domain.BarCode;
import com.chang.log.domain.Payload;
import com.chang.log.repository.barcode.BarCodeRepository;
import com.chang.log.repository.payload.PayloadRepository;
import com.chang.log.response.BarCodeResponse;
import com.chang.log.response.PayInfoResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BarcodeService {

	private final BarCodeRepository barCodeRepository;
	private final PayloadRepository payloadRepository;

	//디렉토리 저장
	// public void saveBarcodeImage(String code) throws Exception {
	// 	BufferedImage bufferedImage = generateBarcode(code);
	// 	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	// 	byte[] byteArray = byteArrayOutputStream.toByteArray();
	//
	// 	// 바이트 배열 크기 로그
	// 	log.info("Generated barcode byte array size: {}", byteArray.length);
	// 	File file = new File("/Users/4usoft/Desktop/receipt/log/barcode.png");
	// 	ImageIO.write(bufferedImage,"PNG",file);
	// }
	//
	public BufferedImage generateBarcode(String code) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
		//오류 수정 수준 설정 L -> 낮은 수준
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		//바코드 여백 설정 1
		hints.put(EncodeHintType.MARGIN, 1);
		//바코드 생성
		MultiFormatWriter writer = new MultiFormatWriter();
		int wid = 300;
		int hei = 100;
		// 생성 후 bit로 변환
		BitMatrix encode = writer.encode(code, BarcodeFormat.CODE_128, wid, hei, hints);
		//bufferImage 말아서 리턴
		return MatrixToImageWriter.toBufferedImage(encode);
	}

	public void saveBarcodeImage(String code) throws Exception {
		// 바코드를 바이트 배열로 생성
		byte[] barcodeBytes = generateBarcodeBytes(code);

		// 파일로 저장 test
		try (FileOutputStream fos = new FileOutputStream("/Users/4usoft/Desktop/receipt/log/barcode.png")) {
			fos.write(barcodeBytes);
		}

		// 또는, 로그로 바이트 배열 크기 출력
		log.info("Barcode byte array size: {}", barcodeBytes.length);
	}

	public byte[] generateBarcodeBytes(String code) throws Exception {
		BufferedImage bufferedImage = generateBarcode(code);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		// 이미지 파일 형식으로 PNG로 저장
		ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream);

		// 바이트 배열로 변환
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		String byteToBase64 = Base64.getEncoder().encodeToString(byteArray);

		BarCode barcode = BarCode.builder()
			.barcode(code)
			.barcodeByte(byteArray)
			.barcodeByteBase64(byteToBase64)
			.build();
		barCodeRepository.save(barcode);

		// 바이트 배열 크기 로그
		log.info("Generated barcode byte array size: {}", byteArray.length);

		return byteArray;
	}

	//바코드 읽기
	public BarCodeResponse readBarcode(String code) throws Exception {

		BarCode barCode = barCodeRepository.findByBarcode(code)
			.orElse(null);
		log.info("barcode = {}",barCode.getBarcode());
		log.info("barcodeByte = {}",barCode.getBarcodeByte());
		log.info("barcodeBase64 = {}",barCode.getBarcodeByteBase64());

		// ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(barCode.getBarcodeByte());
		// BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
		//
		// // BufferedImage가 null인 경우 오류 처리
		// if (bufferedImage == null) {
		// 	throw new Exception("Error: Failed to decode the barcode image. The image is invalid or the format is unsupported.");
		// }
		//
		// // BufferedImage를 LuminanceSource로 변환
		// BufferedImageLuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
		//
		// // BinaryBitmap 생성
		// BinaryBitmap binaryBitmap = new BinaryBitmap(new GlobalHistogramBinarizer(luminanceSource));
		//
		// // 바코드 리더 생성
		// Reader reader = new MultiFormatReader();
		//
		// // 바코드 읽기
		// Result result = reader.decode(binaryBitmap);

		// 바코드 결과 반환
		// return result.getText(); // 바코드 값 반환

		BarCodeResponse barCodeResponse = new BarCodeResponse();
		barCodeResponse.setId(barCode.getId());
		barCodeResponse.setBarcodeByteBase64(barCode.getBarcodeByteBase64());
		return barCodeResponse;
	}

	public void generatePayloadInfo() {
		String a = "$$$$$$$$$$$$$$$$ 영수증 회사명 :  \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "제 주 국 제 자 유 도 시 개 발 센 터 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "제주특별자치도 제주시 임항로 111 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "사업자등록번호 : 616-82-15454 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "대 표 : 문 대 림  TEL : 064-740-9935 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "$$$$$$$$$$$$$$$$ 영수증 헤더 :  \n"
			+ "\n"
			+ "---------------------------------------- \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "구매한도(년6회,600달러/1회)는 JDC와 JTO \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "면세점이 통합·운영되오니 한도 확인 후, \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "JDC지정면세점을 이용해 주시기 바랍니다. \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "       [ 이용문의 : 064)740-9900 ]       \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "---------------------------------------- \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "DATE : 21/04/29   BILL No : 1-004-00002 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "고 객 님 : 심지유 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "편    명 : SH2061 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "담  당  자: 유진아               \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "$$$$$$$$$$$$$$$$ 영수증 판매정보 :  \n"
			+ "\n"
			+ "---------------------------------------- \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "상품바코드     상품명                    \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "   단가  수량   할인    판매($)  판매(W) \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "---------------------------------------- \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "1111903100018   GERA100V1                \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ " $142.00   1  -$21.30  $120.70  W134,070 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "1121115700036   7THL05                   \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "  $20.00   1   -$4.00   $16.00   W17,770 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "1121115700037   7THL08                   \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "  $20.00   1   -$4.00   $16.00   W17,770 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "1124706200738   SHMATTE LIPSTICK MULL IT \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "  $27.00   1   -$5.40   $21.60   W23,990 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "1032204000005   Dom Perignon Blanc 2010 75 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ " $188.00   1  -$28.20  $159.80  W177,500 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "9000000000001 쇼핑백(대) \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "           1     0.00               W100 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "TOT($) :   6    62.90             334.19 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "TOT(元):   6   407.31           2,164.05 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "TOT(W) :   6   69,870            371,200 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "$$$$$$$$$$$$$$$$ 영수증 결제정보 :  \n"
			+ "\n"
			+ "---------------------------------------- \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "카드 번호   : 625132******7220 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "카드 명칭   : [비씨카드사] 부산비씨카드 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "승인 번호   : 32953109 [KICC] \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "할부 월수   : 일시불 [서명없음] \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "매입사제출 \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "[ 카드 ] :      W371,200($1 = 1,110.80) \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "        **************************       \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "\u001B|cA심지유 고객님 연간 총 6회중 1회 방문\u001B|bC \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "\u001B|cA당회 잔여금액   $425.70  W472,868입니다.\u001B|bC \n"
			+ "\n"
			+ " \n"
			+ " \n"
			+ "\n"
			+ "       **************************      ";

		// 1. 가공
		String processed = Arrays.stream(a.split("\n"))
			.map(String::trim) // 각 줄의 양쪽 공백 제거
			.filter(line -> !line.isEmpty()) // 빈 줄 제거
			.collect(Collectors.joining("\n")); // 줄바꿈 유지하며 합치기

		// 2. 항목 분리
		String[] split = processed.split("\\$\\$\\$\\$\\$\\$\\$\\$\\$\\$\\$\\$\\$\\$\\$\\$");
		List<String> list = Arrays.stream(split)
			.map(String::trim) // 각 섹션의 공백 제거
			.filter(f -> !f.isEmpty()) // 빈 섹션 제거
			.toList();
		/**
		 1. 모든 영수증 폼 항목이 동일한가 혹은 섹션이 무조건 4개인가 ? --> header,sales 등
		 2. 달러 모양 특수문자가 모두 정확히 16개로 날라오는가?
		 */
		// if(list.size() != 4) {
		// 	throw new IllegalArgumentException("영수증 정보가 옳바르지 않습니다.");
		// }

		// 3. 디비 저장
		Payload payload = Payload.builder()
			.mertInfo(list.get(0))
			.headerInfo(list.get(1))
			.salesInfo(list.get(2))
			.payInfo(list.size() > 3 ? list.get(3) : "")   //만약 항목이 비어있을 수 있다고 한다면
			.build();

		payloadRepository.save(payload);
	}

	public PayInfoResponse payloadInfo(Long id) {
		Payload payload = payloadRepository.findById(id)
			.orElseThrow(()-> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

		/**
		 * 회사 정보
		 */
		String mertInfo = payload.getMertInfo();
		String companyNamePattern = "영수증 회사명\\s*:\\s*(.*)";
		String resultCompany = returnFirstLine(mertInfo, companyNamePattern);
		String resultCompanyAddress = returnSecondLine(mertInfo, companyNamePattern);
		String businessNumber = "사업자등록번호\\s*:\\s*(.*)";
		String resultBusinessNumber = returnFirstLine(mertInfo, businessNumber);
		String resultTel = returnSecondLine(mertInfo, businessNumber);

		/**
		 * 헤더 정보
		 */
		String headerInfo = payload.getHeaderInfo();
		String paymentInfo = "(구매한도.*?바랍니다\\.)";  //괄호로 그룹화
		String resultPaymentInfo = returnFirstThreeLines(headerInfo, paymentInfo);

		return PayInfoResponse.builder()
			.company(resultCompany)
			.companyAddress(resultCompanyAddress)
			.businessNumber(resultBusinessNumber)
			.companyTel(resultTel)
			.paymentInfo(resultPaymentInfo)
			.build();
	}

	private static String returnFirstLine(String text,String pattern) {
		//패턴을 매개변수로 받아서 줄바꿈 문자도 포함
		Pattern regex = Pattern.compile(pattern, Pattern.DOTALL);
		log.info("regex ={}",regex);
		Matcher matcher = regex.matcher(text);
		log.info("matcher ={}",matcher);
		if (matcher.find()) {
			return matcher.group(1).split("\n")[0].trim();  // 첫 번째 줄만 반환
		} else {
			return "firstLine is null";
		}
	}
	private static String returnSecondLine(String text,String pattern) {
		//패턴을 매개변수로 받아서 줄바꿈 문자도 포함
		Pattern regex = Pattern.compile(pattern, Pattern.DOTALL);
		log.info("regex ={}",regex);
		Matcher matcher = regex.matcher(text);
		log.info("matcher ={}",matcher);
		if (matcher.find()) {
			return matcher.group(1).split("\n")[1].trim();  // 두 번째 줄만 반환
		} else {
			return "seceondLine is null";
		}
	}


	private static String returnFirstThreeLines(String text, String pattern) {
		// 패턴을 매개변수로 받아서 줄바꿈 문자도 포함
		Pattern regex = Pattern.compile(pattern, Pattern.DOTALL);
		Matcher matcher = regex.matcher(text);

		if (matcher.find()) {
			// 첫 번째 캡처 그룹을 가져와서 처리
			String[] lines = matcher.group(1).split("\n");
			StringBuilder result = new StringBuilder();

			// 첫 번째, 두 번째, 세 번째 줄을 하나의 String으로 결합
			for (int i = 0; i < Math.min(3, lines.length); i++) {
				result.append(lines[i].trim()).append(" ");  // 각 줄을 공백으로 구분
			}

			return result.toString().trim();  // 마지막에 불필요한 공백을 제거
		} else {
			return "구매한도 정보 없음";
		}
	}
}
