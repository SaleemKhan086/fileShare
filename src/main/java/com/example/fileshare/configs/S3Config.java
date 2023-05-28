package com.example.fileshare.configs;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.credentials.access-key}")
    private String accessKey;
    @Value("${aws.credentials.secret-key}")
    private String secretKey;

    @Bean
    public AmazonS3 getAmazonS3Client(){
        AmazonS3Client.builder();
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(
                        new AWSStaticCredentialsProvider(getAWSCredentials())
                )
                .build();
    }

    @Bean
    public AWSCredentials getAWSCredentials() {
        return new BasicAWSCredentials(accessKey,secretKey);
    }

}
