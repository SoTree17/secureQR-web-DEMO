package com.sotree.demo.service.QrService;

import com.sotree.demo.domain.QrDTO;
import crypto.SecureQrCryptoAES256;
import crypto.SecureQrCryptoArray;
import crypto.SecureQrHashMD5;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qr.generating.Generator;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class QrService_Implement implements QrService {
    Generator gen = new Generator();

    @Override
    public byte[] createSecureQRCode(SecureQrCryptoArray arr, QrDTO qrDTO) throws IOException {
        // 테스트 용 arr 초기화
        SecureQrCryptoAES256 aes256 = new SecureQrCryptoAES256();
        aes256.setKey("00000000000000000000000000000000");
        arr.add(new SecureQrHashMD5(), aes256);

        int d_index = arr.addData(qrDTO.getData());

        if (!isNull(qrDTO)) {
            return gen.createSecureQRCode(arr, qrDTO.getAuthUrl(),
                    qrDTO.getC_index(), d_index, qrDTO.getWidth(), qrDTO.getHeight());
        } else {
            return null;
        }
    }

    @Override
    public void createQRImage(byte[] qr_byte_arr, String path) {
        if (gen.createSecureQRImage(qr_byte_arr, 0, path))
            log.info("이미지 생성 완료");
        else
            log.info("이미지 생성 실패");
    }

    @Override
    public boolean isNull(QrDTO qrDTO) {
        return qrDTO.getAuthUrl().equals("") && qrDTO.getData().equals("") && qrDTO.getHeight() == 0 && qrDTO.getWidth() == 0;
    }
}
