package com.example.testall.upload;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class UploadMultipleFile {

    public static void main(String[] args) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("X-URL", "http://localhost:8081");
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("files", new FileSystemResource("VID_20190524_215856.mp4"));
        body.add("files", new FileSystemResource("VID_20190524_215856.mp4"));
        body.add("files", new FileSystemResource("VID_20190524_215856.mp4"));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

//        String serverUrl = "http://localhost:8082/singlefileupload";
        String serverUrl = "http://localhost:8081/multiplefileupload";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
        int i = 0;
    }
}
