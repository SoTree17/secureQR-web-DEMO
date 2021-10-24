package com.sotree.demo.controller;

import com.sotree.demo.service.QrService.QrService;
import crypto.SecureQrCryptoArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class demoContorller {

    //Service instance here
    @Autowired
    private QrService qrService;
    private SecureQrCryptoArray arr = new SecureQrCryptoArray();


    @RequestMapping(value="/")
    public String home() {
        return "index";
    }



}
