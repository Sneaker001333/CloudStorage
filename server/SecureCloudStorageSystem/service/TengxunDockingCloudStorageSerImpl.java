package com.sklois.SecureCloudStorageSystem.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.sklois.SecureCloudStorageSystem.publiccloudstorage.AccessAccount;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class TengxunDockingCloudStorageSerImpl implements DockingCloudStorageSer {
    String bucketName = "rusteze-1252850265";

    @Override
    public void uploaddata(MultipartFile file, String fileuniqueid) throws IOException {
        COSCredentials cred = new BasicCOSCredentials(AccessAccount.tengxunsecretId, AccessAccount.tengxunsecretKey);
        // 采用了新的 region 名字，可用 region 的列表可以在官网文档中获取，也可以参考下面的 XML SDK 和 JSON SDK 的地域对照表
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        COSClient cosclient = new COSClient(cred, clientConfig);
        PutObjectResult putObjectResult =
                cosclient.putObject(bucketName, fileuniqueid, file.getInputStream(),null);
        String etag = putObjectResult.getETag(); // 获取文件的 etag
    }

    @Override
    public InputStream downloaddata(String fileuniqueid) {
        COSCredentials cred = new BasicCOSCredentials(AccessAccount.tengxunsecretId, AccessAccount.tengxunsecretKey);
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        COSClient cosclient = new COSClient(cred, clientConfig);
        InputStream input = null;
        // 方法1 获取下载输入流
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileuniqueid);
        COSObject cosObject = cosclient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        return cosObjectInput;
    }

    @Override
    public void deletedata(String fileuniqueid) {
        COSCredentials cred = new BasicCOSCredentials(AccessAccount.tengxunsecretId, AccessAccount.tengxunsecretKey);
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        COSClient cosclient = new COSClient(cred, clientConfig);
        cosclient.deleteObject(bucketName,fileuniqueid);
    }
}
