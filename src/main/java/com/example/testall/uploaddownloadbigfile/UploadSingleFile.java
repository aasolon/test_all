package com.example.testall.uploaddownloadbigfile;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class UploadSingleFile {

    public static void main(String[] args) {
        uploadMultipart();
//        uploadOctetStream();
    }

    private static void uploadMultipart() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("X-URL", "http://localhost:8081");
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource("VID_20190524_215856.mp4"));
//        body.add("file", new FileSystemResource("test_file.txt"));
        body.add("111", "222");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

//        String serverUrl = "http://localhost:8082/single-file-upload";
        String serverUrl = "http://localhost:8082/apache-commons-upload-proxy";
//        String serverUrl = "http://localhost:8082/apache-commons-upload-1";
//        String serverUrl = "http://localhost:8082/apache-commons-upload-to-file";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
        int i = 0;
    }

    private static void uploadOctetStream() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        restTemplate.setRequestFactory(requestFactory);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        FileSystemResource fileSystemResource = new FileSystemResource("test_file.txt");
//        FileSystemResource fileSystemResource = new FileSystemResource("VID_20190524_215856.mp4");
        HttpEntity requestEntity = new HttpEntity<>(fileSystemResource, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/octet-stream",
                HttpMethod.POST, requestEntity, String.class);
    }
}
