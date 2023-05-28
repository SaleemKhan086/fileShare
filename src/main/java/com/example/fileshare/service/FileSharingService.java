package com.example.fileshare.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;

@Service
public class FileSharingService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket-name}")
    private String BUCKET_NAME;

    public void uploadFile(MultipartFile file){
        File convertedFile = converFile(file);
        amazonS3.putObject(BUCKET_NAME,Instant.now()+"_"+file.getOriginalFilename(),convertedFile);
        convertedFile.delete();
    }

    public File downloadFile(String keyname){
        try{
            final S3Object s3Object = amazonS3.getObject(BUCKET_NAME, keyname);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            File file = new File(keyname);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = inputStream.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            inputStream.close();
            fos.close();
            return file;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String keyname) {
        amazonS3.deleteObject(BUCKET_NAME, keyname);
    }

    private File converFile(MultipartFile file){
        try {
            File convertedFile = new File(Instant.now()+"_"+file.getOriginalFilename());
            convertedFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convertedFile);
            fos.write(file.getBytes());
            return convertedFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
