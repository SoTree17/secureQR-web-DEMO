package com.sotree.demo.controller;

import com.sotree.demo.domain.ClientSide.QrDTO;
import com.sotree.demo.domain.ServerSide.QrImage;
import com.sotree.demo.domain.ServerSide.ResDTO;
import com.sotree.demo.service.ServerService.ServerService;
import com.sotree.demo.service.ServerService.SimpleToken.SimpleAuthToken;
import crypto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qr.authentication.AuthQR;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/secureQR")
@RequiredArgsConstructor
@Slf4j
public class ServerController {
    @Autowired
    private ServerService qrService;
    /**
     * 서버에서 저장하는 QR 정보들
     * 기본적으로 ArrayList의 성능과 힙메모리 공간 제약을 따름
     */
    private SecureQrCryptoArray arr = new SecureQrCryptoArray();



    /* Web Client - Server */
    @PostMapping(value = "/generator")
    public ResponseEntity<QrImage> generateQR(@RequestBody QrDTO qrDTO, HttpServletRequest httpRequest) throws Exception {
        log.info("클라이언트로부터 secureQR 이미지 생성 요청 받음");
        log.info("클라이언트 IP : " + httpRequest.getRemoteAddr());

        QrImage result = new QrImage();

        byte[] qr_byte = qrService.createSecureQRCode(arr, qrDTO);
        result.setBinary(qr_byte);
        // qrService.createQRImage(qr_byte, "C:\\TestQR\\qrImg\\Test3.png");
        log.info("클라이언트에게 secureQR 이미지 반환");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /* Android - Server */
    @PostMapping("/authQR")
    public ResponseEntity<ResDTO> authQrAndResponse(@RequestBody Map<String, String> param, HttpServletRequest httpRequest) throws Exception {
        log.info("클라이언트로부터 AUTH QR 인증 요청 받음");
        log.info("클라이언트 IP : " + httpRequest.getRemoteAddr());

        AuthQR authQR = new AuthQR(arr);

        int c_index = Integer.parseInt(param.get("c_index"));
        int d_index = Integer.parseInt(param.get("d_index"));
        String data = param.get("data");

        ResDTO res = new ResDTO();
        res.setResURL(authQR.getOriginData(data, c_index, d_index));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 서버의 SecureQrCryptoArray 에 암호화 방식 추가
     */
    @PostMapping("/addCrypto")
    public ResponseEntity addCryptoToArray(@RequestBody Map<String, String> param) throws Exception {
        try {
            String token = param.get("token");
            if (SimpleAuthToken.isOurToken(token)) {
                int c_num = Integer.parseInt(param.get("crypto"));
                int h_num = Integer.parseInt(param.get("hash"));

                SecureQrCrypto crypto = null;
                SecureQrHash hash = null;

                if (c_num == 0) crypto = new SecureQrCryptoAES256();
                else if (c_num == 1) crypto = new SecureQrCryptoRSA();
                else {
                    log.info("Not supported Crypto method");
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }

                if (h_num == 0) hash = new SecureQrHashMD5();
                else if (h_num == 1) hash = new SecureQrHashSHA256();
                else if (h_num == 2) hash = new SecureQrHashSHA512();
                else {
                    log.info("Not supported Hash method");
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }

                this.arr.add(hash, crypto);
                log.info("현재 arr 사이즈 : " + arr.crypto_size());

                return new ResponseEntity(HttpStatus.OK);
            } else {
                log.info("Unauthorized Access - Wrong Token value " + token);
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
