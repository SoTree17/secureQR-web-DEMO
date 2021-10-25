package com.sotree.demo.service.QrService;

import com.sotree.demo.domain.QrDTO;
import crypto.SecureQrCryptoArray;

import java.io.IOException;

public interface QrService {
    byte[] createSecureQRCode(SecureQrCryptoArray arr, QrDTO qrDTO) throws IOException;
    void createQRImage(byte[] qr_byte_arr, String path);
    boolean isNull(QrDTO qrDTO);
    String randomImgName(String imageformat);
}
