package com.sklois.SecureCloudStorageSystem.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
//import com.amazonaws.services.securitytoken.*;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
import com.sklois.SecureCloudStorageSystem.publiccloudstorage.AccessAccount;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
@Service
public class AmazonDockingCloudStorageSerImpl implements DockingCloudStorageSer {

    static AmazonS3 s3;
//    static TransferManager tx;
    private static String AWS_ACCESS_KEY = AccessAccount.awssecretId;
    private static String AWS_SECRET_KEY = AccessAccount.awssecretKey;
    static final String bucketName = "sklois";

    @Override
    public void uploaddata(MultipartFile file, String fileuniqueid) throws IOException {
        AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials));
        // 设置S3的地区
        builder.setRegion(Regions.AP_SOUTHEAST_1.getName());
        AmazonS3 s3Client = builder.build();
        s3Client.putObject(new PutObjectRequest(bucketName, fileuniqueid,
                file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
    @Override
    public InputStream downloaddata(String fileuniqueid) {
        AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials));
        // 设置S3的地区
        builder.setRegion(Regions.AP_SOUTHEAST_1.getName());
        AmazonS3 s3Client = builder.build();
        // 本地路径
        S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, fileuniqueid));
        InputStream input = null;
        input = object.getObjectContent();

        return input;
    }

    @Override
    public void deletedata(String fileuniqueid) {
        AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials));
        // 设置S3的地区
        builder.setRegion(Regions.AP_SOUTHEAST_1.getName());
        AmazonS3 s3Client = builder.build();
        s3Client.deleteObject(bucketName, fileuniqueid);
    }

    public Map<String, String> stsService(){

        AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_SOUTHEAST_1.getName()).build();
        GetSessionTokenRequest getSessionTokenRequest = new GetSessionTokenRequest().withDurationSeconds(7200);
        GetSessionTokenResult sessionTokenResult = stsClient.getSessionToken(getSessionTokenRequest);
        Credentials sessionCredentials =sessionTokenResult
                .getCredentials().withSessionToken(sessionTokenResult.getCredentials().getSessionToken())
                .withExpiration(sessionTokenResult.getCredentials().getExpiration());

//        BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(
//                sessionCredentials.getAccessKeyId(),sessionCredentials.getSecretAccessKey(),
//                sessionCredentials.getSessionToken()
//        );

        System.out.println("==================================");
        System.out.println("GetSessionToken result:");
        System.out.println("    accessKeyId:  " + sessionCredentials.getAccessKeyId());
        System.out.println("    secretAccessKey:  " + sessionCredentials.getSecretAccessKey());
        System.out.println("    securityToken:  " + sessionCredentials.getSessionToken());
        System.out.println("    expiresAt:  " + sessionCredentials.getExpiration().toString());
        System.out.println("==================================");


        Map<String, String> result = new HashMap<String, String>();
        result.put("accessKeyId", sessionCredentials.getAccessKeyId());
        result.put("accessKey", sessionCredentials.getSecretAccessKey());
        result.put("tokenSecret", sessionCredentials.getSessionToken());
        result.put("expiration", sessionCredentials.getExpiration().toString());
        return result;
    }
}
