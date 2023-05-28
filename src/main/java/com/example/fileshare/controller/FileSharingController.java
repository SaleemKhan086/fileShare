package com.example.fileshare.controller;

import com.example.fileshare.service.FileSharingService;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class FileSharingController {

    @Autowired
    private FileSharingService fileSharingService;

    @PostMapping(value = "/upload",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
        fileSharingService.uploadFile(file);

        return ResponseEntity.ok("File Uploaded Successfully!");
    }


}
