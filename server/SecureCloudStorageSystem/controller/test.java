package com.sklois.SecureCloudStorageSystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.sklois.SecureCloudStorageSystem.entity.Metadata;
import com.sklois.SecureCloudStorageSystem.service.AliDockingCloudStorageSerImpl;
import com.sklois.SecureCloudStorageSystem.service.AmazonDockingCloudStorageSerImpl;
import com.sklois.SecureCloudStorageSystem.service.BaiduDockingCloudStorageSerImpl;
import com.sklois.SecureCloudStorageSystem.service.SwiftDockingCloudStorageSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class test {
    @Autowired
    AmazonDockingCloudStorageSerImpl amazonDockingCloudStorageSerimpl;

    @Autowired
    AliDockingCloudStorageSerImpl aliDockingCloudStorageSer;
    @Autowired
    BaiduDockingCloudStorageSerImpl baiduDockingCloudStorageSer;
    @Autowired
    SwiftDockingCloudStorageSerImpl swiftDockingCloudStorageSer;

    @RequestMapping("/amazontest")
    @ResponseBody
    public Object amazontest() throws ClientException {
        Map<String, String> ret = amazonDockingCloudStorageSerimpl.stsService();
        return ret;
    }


    @RequestMapping("/alitest")
    @ResponseBody
    public Object alitest() throws ClientException {
        Map<String, String> ret = aliDockingCloudStorageSer.stsService();
        return ret;
    }

    @RequestMapping("/baidutest")
    @ResponseBody
    public Object baidutest() throws ClientException {
        Map<String, String> ret = baiduDockingCloudStorageSer.stsService();
        return ret;
    }


    @RequestMapping("/teststs")
    @ResponseBody
    public Object teststs(@RequestBody JSONObject json, HttpServletRequest request, HttpServletResponse response,
                          HttpSession session) throws ClientException {

        if (null == json) {
            return null;
        }
        String stsaccessid = json.getString("accessKeyId");
        String stsaccesskey = json.getString("accessKeySecret");
        String stsaccesstocken = json.getString("tokenSecret");

        aliDockingCloudStorageSer.putobject(stsaccessid, stsaccesskey, stsaccesstocken);
        return "ssss";
    }

    @RequestMapping("/swifttest")
    @ResponseBody
    public Object swifttest() throws IOException {


        swiftDockingCloudStorageSer.uploaddata(null, "123123");
        return null;
    }
}
