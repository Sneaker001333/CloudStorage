package com.sklois.SecureCloudStorageSystem.service;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.BceCredentials;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.auth.DefaultBceSessionCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.BosObjectInputStream;
import com.baidubce.services.bos.model.BosObject;
import com.baidubce.services.bos.model.GetObjectRequest;
import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.baidubce.services.sts.StsClient;
import com.baidubce.services.sts.model.GetSessionTokenRequest;
import com.baidubce.services.sts.model.GetSessionTokenResponse;
import com.sklois.SecureCloudStorageSystem.publiccloudstorage.AccessAccount;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class BaiduDockingCloudStorageSerImpl implements DockingCloudStorageSer {
    String ENDPOINT = "bj.bcebos.com"; // 用户自己指定的域名
    String bucketname = "beijingss";
    private static final String STS_ENDPOINT = "http://sts.bj.baidubce.com";


    @Override
    public void uploaddata(MultipartFile file, String fileuniqueid) throws IOException {
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(AccessAccount.baidusecretId, AccessAccount.baidusecretKey));
        config.setEndpoint(ENDPOINT);
        BosClient client = new BosClient(config);
        PutObjectResponse putObjectFromFileResponse =
                client.putObject(bucketname, fileuniqueid, file.getInputStream());
    }

    @Override
    public InputStream downloaddata(String fileuniqueid) {
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(AccessAccount.baidusecretId, AccessAccount.baidusecretKey));
        config.setEndpoint(ENDPOINT);
        BosClient client = new BosClient(config);
        // 新建GetObjectRequest
        GetObjectRequest getObjectRequest =
                new GetObjectRequest(bucketname, fileuniqueid);
        // 下载Object到文件
        BosObject object = client.getObject(getObjectRequest);
        BosObjectInputStream outstream = object.getObjectContent();
        return outstream;
    }

    @Override
    public void deletedata(String fileuniqueid) {
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(AccessAccount.baidusecretId, AccessAccount.baidusecretKey));
        config.setEndpoint(ENDPOINT);
        BosClient client = new BosClient(config);
        client.deleteObject(bucketname, fileuniqueid);
    }


    public Map<String, String> stsService(){
        BceCredentials credentials = new DefaultBceCredentials(AccessAccount.baidusecretId, AccessAccount.baidusecretKey);
        StsClient client = new StsClient(
                new BceClientConfiguration().withEndpoint(STS_ENDPOINT).withCredentials(credentials)
        );
        GetSessionTokenResponse response = client.getSessionToken(new GetSessionTokenRequest());
        // or simply call:
        // GetSessionTokenResponse response = client.getSessionToken();
        // or you can specify limited permissions with ACL:
        // GetSessionTokenResponse response = client.getSessionToken(new GetSessionTokenRequest().withAcl("blabla"));
        // build DefaultBceSessionCredentials object from response:
        BceCredentials bosstsCredentials = new DefaultBceSessionCredentials(
                response.getAccessKeyId(),
                response.getSecretAccessKey(),
                response.getSessionToken());
        System.out.println("==================================");
        System.out.println("GetSessionToken result:");
        System.out.println("    accessKeyId:  " + response.getAccessKeyId());
        System.out.println("    secretAccessKey:  " + response.getSecretAccessKey());
        System.out.println("    securityToken:  " + response.getSessionToken());
        System.out.println("    expiresAt:  " + response.getExpiration().toString());
        System.out.println("==================================");


        Map<String, String> result = new HashMap<String, String>();
        result.put("accessKeyId", response.getAccessKeyId());
        result.put("accessKey", response.getSecretAccessKey());
        result.put("tokenSecret", response.getSessionToken());
        result.put("expiration", response.getExpiration().toString());
        return result;
    }
}

