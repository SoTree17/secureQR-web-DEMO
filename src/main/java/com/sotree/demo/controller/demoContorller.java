package com.sotree.demo.controller;

import com.sotree.demo.domain.QrDTO;
import com.sotree.demo.service.QrService.QrService;
import com.sotree.demo.service.httpRequesting.RequestService;
import crypto.SecureQrCryptoArray;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class demoContorller {

    //Service instance here
    @Autowired
    private QrService qrService;
    @Autowired
    private RequestService requestService;
    //private SecureQrCryptoArray arr = new SecureQrCryptoArray();


    @RequestMapping(value="/")
    public String home() {
        return "index";
    }

    @PostMapping("/requestQR")
    public String requestQR(QrDTO qrDTO) throws IOException {

        String requestPATH = "api/v1/secureQR/generator";
        log.info(qrDTO.toString());
        byte[] result = requestService.requestQrImage(qrDTO, requestPATH);
        try {
            qrService.createQRImage(result, "src/main/resources/static/img/secureQR_image.png");
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:";
    }

}
