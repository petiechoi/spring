package com.modim.spring.global.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class S3Config {
    @Value("${cloud.aws.credentials.access-key}")
    private String iamAccessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String iamSecretKey;
    @Value("${cloud.aws.region.static}")
    private String region;

//    @Bean
//    public AmazonS3Client amazonS3Client() {
//        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(iamAccessKey, iamSecretKey);
//        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
//                .withRegion(region).enablePathStyleAccess()
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                .build();
//    }

    @Bean
    public AmazonS3 AmazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(iamAccessKey, iamSecretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
