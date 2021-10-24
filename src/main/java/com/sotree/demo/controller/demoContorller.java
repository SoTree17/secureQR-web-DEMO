package com.sotree.demo.controller;

import com.sotree.demo.domain.CryptoDTO;
import com.sotree.demo.domain.QrDTO;
import com.sotree.demo.service.QrService.QrService;
import com.sotree.demo.service.QrService.QrService_Impl;
import com.sotree.demo.service.httpRequesting.RequestService;
import crypto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Slf4j
@RequiredArgsConstructor
@Controller
public class demoContorller {

    private QrService qrService;
    private SecureQrCryptoArray arr = new SecureQrCryptoArray();
    private String cryptoStatus = "";


    @RequestMapping(value="/")
    public String home() {
        return "index";
    }

    @PostMapping("/create")
    public String createQR(QrDTO qrDTO)  {

        return "redirect:";
    }

    @RequestMapping(value = "/addCrypto", method = RequestMethod.POST)
    public ModelAndView addCryptoToArray(CryptoDTO cryptoDTO, ModelAndView mav) {
        //ModelAndView mav = new ModelAndView();
        mav.setViewName("index");

        try {
            String hash_method = cryptoDTO.getHash();
            String crypto_method = cryptoDTO.getCrypto();

            SecureQrCrypto crypto = null;
            SecureQrHash hash = null;

            if (crypto_method.equals("AES256")) crypto = new SecureQrCryptoAES256();
            else if (crypto_method.equals("RSA")) crypto = new SecureQrCryptoRSA();

            if (hash_method.equals("MD5")) hash = new SecureQrHashMD5();
            else if (hash_method.equals("SHA256")) hash = new SecureQrHashSHA256();
            else if (hash_method.equals("SHA512")) hash = new SecureQrHashSHA512();

            this.arr.add(hash, crypto);

            String tmp = "index:" + Integer.toString(arr.crypto_size() - 1) + " ( " + hash_method + ", " + crypto_method + " )\n";
            cryptoStatus += tmp;

            // log.info(cryptoStatus);

            mav.addObject("cryptoStatus", cryptoStatus);

            return mav;

        } catch (Exception e) {
            e.printStackTrace();
            return mav;
        }
    }
}
