package com.sklois.SecureCloudStorageSystem.service;

import com.obs.services.ObsClient;
import com.obs.services.model.GetObjectRequest;
import com.obs.services.model.ObsObject;
import com.obs.services.model.ProgressListener;
import com.obs.services.model.ProgressStatus;
import com.sklois.SecureCloudStorageSystem.publiccloudstorage.AccessAccount;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class HuaweiDockingCloudStorageSerImpl implements DockingCloudStorageSer {
    static String endPoint = AccessAccount.huaweiendpoint;
    static String ak = AccessAccount.huaweiaccessKeyId;
    static String sk = AccessAccount.huaweiaccessKeySecret;
    static String bucketname = "beijing2";

    @Override
    public void uploaddata(MultipartFile file, String fileuniqueid) throws IOException {
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        obsClient.putObject(bucketname, fileuniqueid,
                file.getInputStream());
    }

    @Override
    public InputStream downloaddata(String fileuniqueid) {
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        GetObjectRequest request = new GetObjectRequest(bucketname, fileuniqueid);
        request.setProgressListener(new ProgressListener() {
            public void progressChanged(ProgressStatus status) {
                // 获取下载平均速率
                System.out.println("AverageSpeed:" + status.getAverageSpeed());
                // 获取下载进度百分比
                System.out.println("TransferPercentage:" + status.getTransferPercentage());
            }
        });
        // 每上传1MB数据反馈下载进度
        request.setProgressInterval(1024 * 1024);
        ObsObject obsObject = obsClient.getObject(request);
        // 读取对象内容
        InputStream input = obsObject.getObjectContent();
        return input;
    }

    @Override
    public void deletedata(String fileuniqueid) {
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        obsClient.deleteObject(bucketname, fileuniqueid);
    }
}
