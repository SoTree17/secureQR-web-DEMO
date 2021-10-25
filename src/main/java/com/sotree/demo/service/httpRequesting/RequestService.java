package com.sotree.demo.service.httpRequesting;

import com.sotree.demo.domain.AdditionDTO;
import com.sotree.demo.domain.QrDTO;

import java.io.IOException;

public interface RequestService {
    byte[] requestQrImage(QrDTO qrDTO, String resourcePath) throws IOException;
    int requestAddCrypto(AdditionDTO aDTO, String resourPath) throws IOException;
}
