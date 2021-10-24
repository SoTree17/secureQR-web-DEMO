package com.sotree.demo.controller;

import com.sotree.demo.domain.QrDTO;
import com.sotree.demo.service.QrService.QrService;
import com.sotree.demo.service.httpRequesting.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


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
    public String requestQR(QrDTO qrDTO) throws IOException, ScriptException {

        String requestPATH = "api/v1/secureQR/generator";
        String localPath="C:\\TestQR\\qrImg\\Server-Test.png";
        //String staticPath = "src/main/resources/static/img/secureQR_image.png";
        //String staticPath = "secureQR_image.png";
        log.info(qrDTO.toString());
        byte[] result = requestService.requestQrImage(qrDTO, requestPATH);
        try {
            qrService.createQRImage(result, localPath);
        }catch(Exception e){
            e.printStackTrace();
        }

        return "redirect:";
    }

    @GetMapping("/display")
    public ResponseEntity<Resource> display(@RequestParam(value="filename") String filename){
        String path = "C:\\TestQR\\qrImg\\";
        Resource resource = new FileSystemResource(path+filename);

        if(!resource.exists()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try{
            filePath = Paths.get(path+filename);
            header.add("Content-Type", Files.probeContentType(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(resource, header, HttpStatus.OK);

    }
}
