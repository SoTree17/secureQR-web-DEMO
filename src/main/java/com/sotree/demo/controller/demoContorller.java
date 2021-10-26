package com.sotree.demo.controller;

import com.sotree.demo.domain.ClientSide.AdditionDTO;
import com.sotree.demo.domain.ClientSide.QrDTO;
import com.sotree.demo.service.ClientService.ClientService;
import com.sotree.demo.service.PathDetertimant.PathDeterminant;
import com.sotree.demo.service.httpRequesting.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.OS;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import qr.reading.Reader;

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
    private ClientService clientService;
    @Autowired
    private RequestService requestService;

    //OS별 저장 경로
    PathDeterminant pathDeterminant = new PathDeterminant();


    /* 컨트롤러내에서 사용되는 변수들 */
    private StringBuffer stringBuffer = new StringBuffer();
    private String DIRECTORY = pathDeterminant.getOS_TYPE();
    private String FILENAME; // requestQR 시 마다, 현재시간에 따라 랜덤하게 파일이름이 결정되어 지정됨.
    private String read; // secureQR-module의 Reader라이브러리를 읽어서 data형태 저장

    @RequestMapping(value = "/")
    public String home() {
        return "index";
    }

    @PostMapping("/requestQR")
    public String requestQR(QrDTO qrDTO, RedirectAttributes rttr) throws IOException, ScriptException {

        final String requestPATH = "api/v1/secureQR/generator";

        FILENAME = clientService.randomImgName(".png");
        //String staticPath = "src/main/resources/static/img/secureQR_image.png";
        //String staticPath = "secureQR_image.png";
        log.info(qrDTO.toString());

        byte[] result = requestService.requestQrImage(qrDTO, requestPATH); // 입력받은 endpoint의 지정된 api 호출
        try {
            clientService.createQRImage(result, DIRECTORY + FILENAME);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Reader reader = new Reader();
        read = reader.readSecureQRCode(DIRECTORY + FILENAME);
        log.info(read);

        rttr.addFlashAttribute("imgName", "?filename=" + FILENAME);
        rttr.addFlashAttribute("addCrypto", stringBuffer.toString());
        rttr.addFlashAttribute("qrDetail", read);
        return "redirect:";
    }

    /**
     * 서버에 연결해서, 해싱 방식, 암호화 방식을 추가하는 로직 처리
     * 토큰 정보 SOTREE17_SERVER_REQUEST 가 필요함
     * @param aDTO
     * @param rttr
     * @return
     * @throws IOException
     */
    @PostMapping("/addCrypto")
    public String addCrypto(AdditionDTO aDTO, RedirectAttributes rttr) throws IOException {
        String requestPATH = "api/v1/secureQR/addCrypto";
        log.info(aDTO.toString());

        int statusCode = requestService.requestAddCrypto(aDTO, requestPATH);
        HttpStatus status = HttpStatus.valueOf(statusCode);

        stringBuffer.append(status + " \n");  // Status log 용

        if (status == HttpStatus.OK) {
            log.info("addCrypto Success");
        } else if (status == HttpStatus.UNAUTHORIZED) {
            log.info("Unauthorized Access - Wrong Token value " + aDTO.getToken());
        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            log.info("Internal Server Error");
        } else if (status == HttpStatus.BAD_REQUEST) {
            log.info("Not supported Hashing or Crypto method");
        } else {
            log.info("Not defined Status code-");
        }

        /* 앞서 만들어진 값이 있다면 같이 redirect하게 해줌 */
        rttr.addFlashAttribute("imgName", "?filename=" + FILENAME);
        rttr.addFlashAttribute("addCrypto", stringBuffer.toString());
        rttr.addFlashAttribute("qrDetail", read);

        return "redirect:";
    }

    /**
     * 로컬 디스크내의 이미지 처리를 위해서 필요
     * 뷰에서 /display?filename=[원하는 파일이름.png] 와 같이 작성하면,
     * 해당 부분에서 그러한 파일 자원이 있는지 지정된 경로 path 에서 탐색하고
     * 응답해줌.
     *
     * @param filename
     * @return Resource와 Header값을 기준으로 반환
     * @throws IOException
     */
    @GetMapping("/display")
    public ResponseEntity<Resource> display(@RequestParam(value = "filename") String filename) throws IOException {
        /*String path = "C:\\TestQR\\qrImg\\";*/
        Resource resource = new FileSystemResource(DIRECTORY + filename);

        if (!resource.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(DIRECTORY + filename);
            header.add("Content-Type", Files.probeContentType(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(resource, header, HttpStatus.OK);

    }
}
