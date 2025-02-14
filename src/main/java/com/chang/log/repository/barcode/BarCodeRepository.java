package com.chang.log.repository.barcode;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BarCodeRepository extends JpaRepository<BarCode,Long> {
	Optional<BarCode> findByBarcode(String barcode);
}
