package com.example.testall.uploaddownloadbigfile;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class UploadController {

    private final NativeWebRequest nativeWebRequest;

    public UploadController(NativeWebRequest nativeWebRequest) {
        this.nativeWebRequest = nativeWebRequest;
    }

    @RequestMapping(path = "/single-file-upload", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@RequestParam(name = "filename1") MultipartFile file) throws IOException {

        byte[] bytes = file.getBytes();

        System.out.println("File Name: " + file.getOriginalFilename());
        System.out.println("File Content Type: " + file.getContentType());
//        System.out.println("File Content:\n" + new String(bytes));

        File tmpFile = File.createTempFile("download", "tmp");
        FileOutputStream out = new FileOutputStream(tmpFile, true);
        StreamUtils.copy(bytes, out);
        out.close();

        return new ResponseEntity<>("Successful", null, HttpStatus.OK);
    }

    @RequestMapping(path = "/multiple-file-upload", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@RequestParam("files") List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {
            byte[] bytes = file.getBytes();

            System.out.println("File Name: " + file.getOriginalFilename());
            System.out.println("File Content Type: " + file.getContentType());
//            System.out.println("File Content:\n" + new String(bytes));

            File tmpFile = File.createTempFile("download", "tmp");
            FileOutputStream out = new FileOutputStream(tmpFile, true);
            StreamUtils.copy(bytes, out);
            out.close();
        }

        return (new ResponseEntity<>("Successful", null, HttpStatus.OK));
    }

    @PostMapping(value = "/octet-stream", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public String post(HttpServletRequest request, @RequestHeader HttpHeaders headers) throws IOException {
        File tmpFile = File.createTempFile("download", "tmp");
        try (FileOutputStream out = new FileOutputStream(tmpFile, true)) {
            IOUtils.copy(request.getInputStream(), out);
        }
        return "FILE UPLOADED";
    }

    @PostMapping("/apache-commons-upload-proxy")
    public void uploadFileApacheCommonsUploadProxy(HttpServletRequest request) throws IOException, FileUploadException {
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator iterStream = upload.getItemIterator(request);
        while (iterStream.hasNext()) {
            FileItemStream item = iterStream.next();
            if (!item.isFormField()) {
                try (InputStream uploadedStream = item.openStream()) {
                    RestTemplate restTemplate = new RestTemplate();
                    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
                    requestFactory.setBufferRequestBody(false);
                    restTemplate.setRequestFactory(requestFactory);

                    // 1.
//                    ResponseEntity<String> response = proxyAsMultipart(restTemplate, item, uploadedStream);

                    // 2.
                    ResponseEntity<String> response = proxyAsOctetStream(restTemplate, uploadedStream);

                    System.out.println("Response after proxying file: " + response.getBody());
                }
            } else {
                try (InputStream uploadedStream = item.openStream()) {
                    String formFieldValue = Streams.asString(uploadedStream);
                    System.out.println("Form field \"" + item.getFieldName() + "\": " + formFieldValue);
                }
            }
        }
    }

    @PostMapping(value = "/apache-commons-upload-to-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadFileApacheCommonsUploadToFile() throws IOException, FileUploadException {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator iterStream = upload.getItemIterator(request);
        while (iterStream.hasNext()) {
            FileItemStream item = iterStream.next();
            String name = item.getFieldName();
            if (!item.isFormField()) {
                File tmpFile = File.createTempFile("download", "tmp");
                try (InputStream uploadedStream = item.openStream();
                     FileOutputStream out = new FileOutputStream(tmpFile, true)) {
                    IOUtils.copy(uploadedStream, out);
                }
            } else {
                try (InputStream uploadedStream = item.openStream()) {
                    String formFieldValue = Streams.asString(uploadedStream);
                    System.out.println("Form field " + formFieldValue);
                }
            }
        }

        return "FILE SAVED ON DISK";
    }

    private ResponseEntity<String> proxyAsMultipart(RestTemplate restTemplate, FileItemStream item, InputStream uploadedStream) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // This nested HttpEntiy is important to create the correct
        // Content-Disposition entry with metadata "name" and "filename"
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename(item.getName())
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity fileEntity = new HttpEntity<>(new InputStreamResource(uploadedStream), fileMap);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);

        HttpEntity requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/apache-commons-upload-to-file",
                HttpMethod.POST, requestEntity, String.class);
        return response;
    }

    private ResponseEntity<String> proxyAsOctetStream(RestTemplate restTemplate, InputStream uploadedStream) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity requestEntity = new HttpEntity<>(new InputStreamResource(uploadedStream), headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/octet-stream",
                HttpMethod.POST, requestEntity, String.class);
        return response;
    }
}
