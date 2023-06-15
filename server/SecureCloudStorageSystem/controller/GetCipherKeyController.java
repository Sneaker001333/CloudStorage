package com.sklois.SecureCloudStorageSystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.sklois.SecureCloudStorageSystem.entity.RegisterUser;
import com.sklois.SecureCloudStorageSystem.intercepter.Auth;
import com.sklois.SecureCloudStorageSystem.service.AuditSer;
import com.sklois.SecureCloudStorageSystem.service.AuditSerImpl;
import com.sklois.SecureCloudStorageSystem.service.DatabaseSer;
import com.sklois.SecureCloudStorageSystem.service.DockingSecurityCloudSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@RestController
public class GetCipherKeyController {


    @Autowired
    DockingSecurityCloudSer dockingsecuritycloudser;

    @RequestMapping("/getkey")
    @ResponseBody
    @Auth(normaluser = "普通用户")
    public Object getkey(@RequestBody JSONObject json, HttpServletRequest request, HttpServletResponse response,
                         HttpSession session) {
        if (null == json) {
            return null;
        }
        String sessionid  = request.getHeader("sessionid");
        System.out.println("json is " + json.toJSONString());
        String method = json.getString("method");
        String version = json.getString("version");
        String timestamp = json.getString("timestamp");
        JSONObject requestjsonobj = json.getJSONObject("request");
        String fileuniqueid = requestjsonobj.getString("fileuniqueid");
        String filekey = dockingsecuritycloudser.getkey(sessionid, fileuniqueid);
        JSONObject ret = new JSONObject();
        ret.put("fileuniqueid",fileuniqueid);
        ret.put("fileencryptkey",filekey);
        return response("getkey", "success", "8000",
                "getkey success", ret);
    }

    private JSONObject response(String method, String result, String code,
                                String message, JSONObject details) {
        JSONObject ret = new JSONObject();
        ret.put("method", method);
        ret.put("result", result);
        ret.put("code", code);
        ret.put("message", message);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now(); // get the current date and time
        String currenttimestr = currentTime.format(formatter);
        ret.put("timestamp", currenttimestr);
        ret.put("details", details);
        return ret;
    }
}
