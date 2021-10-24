package com.sotree.demo.service.httpRequesting;

import com.google.gson.Gson;
import com.sotree.demo.domain.QrDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestService_Impl implements RequestService {

    @Override
    public byte[] requestQrImage(QrDTO qrDTO, String resourcePath) throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();

        // HTTP info
        String BaseUrl = qrDTO.getAuthUrl();
        String requestJson = new Gson().toJson(qrDTO);
        String requestUrl = BaseUrl + resourcePath;

        //Set HTTP Header, Body
        HttpPost httpPost = new HttpPost(requestUrl);
        StringEntity entity = new StringEntity(requestJson);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        //응답 담기
        String responseBody = "";
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        responseBody = client.execute(httpPost, responseHandler);
        log.info(responseBody);

        // Gson 이용해서 JSON -> POJO 하려 했는데 잘 안되어 문자열 파싱
        String[] temp = responseBody.split(":");
        String str_ = temp[1].substring(1, temp[1].length() - 2);
        log.info(str_);

        byte[] binary = Base64.getDecoder().decode(str_);
        client.close();

        return binary;
    }
}
