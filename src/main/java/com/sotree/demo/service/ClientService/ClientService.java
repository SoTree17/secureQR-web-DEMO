package com.sotree.demo.service.ClientService;

import com.sotree.demo.domain.ClientSide.QrDTO;
import crypto.SecureQrCryptoArray;

import java.io.IOException;

public interface ClientService {
    byte[] createSecureQRCode(SecureQrCryptoArray arr, QrDTO qrDTO) throws IOException;
    void createQRImage(byte[] qr_byte_arr, String path);
    boolean isNull(QrDTO qrDTO);
    String randomImgName(String imageformat);
}
