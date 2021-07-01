package com.example.testall.upload;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class UploadController {
    @RequestMapping(path = "/singlefileupload", method = RequestMethod.POST)
    public ResponseEntity<String> processFile(@RequestParam("file") MultipartFile file) throws IOException {

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

    @RequestMapping(path = "/multiplefileupload", method = RequestMethod.POST)
    public ResponseEntity<String> processFile(@RequestParam("files") List<MultipartFile> files) throws IOException {

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
}
