package com.sotree.demo.service.ServerService;

import com.sotree.demo.domain.ClientSide.QrDTO;
import crypto.SecureQrCryptoArray;

import java.io.IOException;

public interface ServerService {
    byte[] createSecureQRCode(SecureQrCryptoArray arr, QrDTO qrDTO) throws IOException;
    void createQRImage(byte[] qr_byte_arr, String path);
    boolean isNull(QrDTO qrDTO);
}
