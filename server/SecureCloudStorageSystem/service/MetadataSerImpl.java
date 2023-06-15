package com.sklois.SecureCloudStorageSystem.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sklois.SecureCloudStorageSystem.entity.AuditEntity;
import com.sklois.SecureCloudStorageSystem.entity.Metadata;
import com.sklois.SecureCloudStorageSystem.entity.RegisterUser;
import com.sklois.SecureCloudStorageSystem.repository.MetadataRepository;
import com.sklois.SecureCloudStorageSystem.repository.RegisterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MetadataSerImpl implements MetadataSer {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MetadataRepository metadatarepository;

    @Autowired
    DockingSecurityCloudSer dockingsecuritycloudser;


    @Autowired
    private RegisterUserRepository registeruserrepository;

    public Metadata savemetadata(String metadata) {

        JSONObject jsonObject = JSONObject.parseObject(metadata);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String filename = jsonObject.getString("filename");
        long filesize = jsonObject.getLong("filesize");
        LocalDateTime ctime = LocalDateTime.parse(jsonObject.getString("ctime"), formatter);
        LocalDateTime mtime = LocalDateTime.parse(jsonObject.getString("mtime"), formatter);
        LocalDateTime lastaccesstime = LocalDateTime.parse(jsonObject.getString("lastaccesstime"), formatter);
        LocalDateTime uploadtime = LocalDateTime.parse(jsonObject.getString("uploadtime"), formatter);
        String fileowner = jsonObject.getString("fileowner");
        String plaintexthash = jsonObject.getString("plaintexthash");
        String ciphertexthash = jsonObject.getString("ciphertexthash");
        long ciphertextsize = jsonObject.getLong("ciphertextsize");
        String keyhash = jsonObject.getString("keyhash");
        String fileuniqueid = jsonObject.getString("fileuniqueid");

        String type = jsonObject.getString("type");

        String currentid = jsonObject.getString("currentid");

        String parentid = jsonObject.getString("parentid");


        Metadata findout = metadatarepository.findOneByFileuniqueid(fileuniqueid);
        if (null == findout) {
            Metadata metadataobj = new Metadata();
            metadataobj.setFilename(filename);
            metadataobj.setFilesize(filesize);
            metadataobj.setCtime(ctime);
            metadataobj.setMtime(mtime);
            metadataobj.setLastaccesstime(lastaccesstime);
            metadataobj.setUploadtime(uploadtime);
            metadataobj.setFileowner(fileowner);
            metadataobj.setPlaintexthash(plaintexthash);
            metadataobj.setCiphertexthash(ciphertexthash);
            metadataobj.setCiphertextsize(ciphertextsize);
            metadataobj.setKeyhash(keyhash);
            metadataobj.setFileuniqueid(fileuniqueid);
            metadataobj.setType(type);
            metadataobj.setCurrentid(currentid);
            metadataobj.setParentid(parentid);
            Metadata saveresult = metadatarepository.save(metadataobj);
            return saveresult;
        } else {
            return null;
        }


    }

    public List<JSONObject> folderlist(String username) {
        List<JSONObject> ret = get_folder_list("根目录", username);
        return ret;
    }

    List<JSONObject> get_folder_list(String parentid, String username) {

        List<JSONObject> listItems = new ArrayList<>();
        List<Metadata> result = metadatarepository.findAllByTypeAndParentidAndFileowner("dir", parentid, username);//
        if (null != result && result.size() > 0) {
            for (Metadata one : result) {
                if (0 == "dir".compareToIgnoreCase(one.getType())) {
                    JSONObject listItem = new JSONObject(true);
                    listItem.put("id", one.getId());
                    listItem.put("currentid", one.getCurrentid());
                    listItem.put("opened", one.isIfopened());
                    listItem.put("foldername", one.getFilename());
                    listItem.put("ctime", one.getCtime());
                    listItem.put("mtime", one.getMtime());
                    listItem.put("lastaccesstime", one.getLastaccesstime());
                    listItem.put("uploadtime", one.getUploadtime());
                    List<JSONObject> listItem_recursive = get_folder_list(one.getCurrentid(), username);
                    if (null != listItem_recursive && listItem_recursive.size() > 0) {
                        listItem.put("children", listItem_recursive);
                    }
                    listItems.add(listItem);
                }
            }
        }
        return listItems;
    }

    public List<JSONObject> filelist(
            int pageNo,
            int pageSize,
            String folderuniqueid,
            String username) {
        Page<Metadata> findoutpage = null;
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(pageNo, pageSize, sort);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)  //改变默认字符串匹配为:模糊查询
                .withMatcher("parentid", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("fileowner", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("id")
                .withIgnorePaths("ciphertextsize")
                .withIgnorePaths("currentid")
                .withIgnorePaths("filename")
                .withIgnorePaths("filesize")
                .withIgnorePaths("fileuniqueid")
                .withIgnorePaths("ifopened")
                .withIgnorePaths("ifshared"); //忽略id字段
        System.out.println("username is " + username);
        Metadata filemetadata = new Metadata();
        filemetadata.setParentid(folderuniqueid);
        filemetadata.setFileowner(username);
        Example<Metadata> ex = Example.of(filemetadata, matcher); //动态查询
        findoutpage = metadatarepository.findAll(ex, pageable);

        List<Metadata> listout = findoutpage.getContent();
        System.out.println("listout size is " + listout.size());
        List<JSONObject> listItems = new ArrayList<>();
        if (null != listout && listout.size() > 0) {
            for (Metadata one : listout) {
                JSONObject listItem = new JSONObject(true);
                listItem.put("id", one.getId());
                listItem.put("filename", one.getFilename());
                listItem.put("filesize", one.getFilesize());
                listItem.put("ctime", one.getCtime());
                listItem.put("mtime", one.getMtime());
                listItem.put("lastaccesstime", one.getLastaccesstime());
                listItem.put("uploadtime", one.getUploadtime());
                listItem.put("plaintexthash", one.getPlaintexthash());
                listItem.put("ciphertexthash", one.getCiphertexthash());
                listItem.put("ciphertextsize", one.getCiphertextsize());
                listItem.put("keyhash", one.getKeyhash());
                listItem.put("fileuniqueid", one.getFileuniqueid());
                listItem.put("type", one.getType());
                listItem.put("currentid", one.getCurrentid());
                listItem.put("parentid", one.getParentid());
                listItem.put("ifshared", one.isIfshared());
                listItem.put("opened", one.isIfopened());
                listItems.add(listItem);
            }
        }
        return listItems;
    }

    public long countByParentidAndFileowner(String parentid, String fileowner) {
        long filenumber = metadatarepository.countByParentidAndFileowner(parentid, fileowner);
        return filenumber;
    }


    public List<Metadata> datasearch(
            String sessionid,
            String mode,
            String condition,
            JSONArray keywords,
            String type,
            String fromtime,
            String totime,
            int pageNo,
            int pageSize) {
        RegisterUser fuser = registeruserrepository.findOneBySessionid(sessionid);
        StringBuffer sqlStr = new StringBuffer("select * from metadata where ");
        Map<String, Object> map = new HashMap<String, Object>();
        int index = 0;
        if (0 == mode.compareToIgnoreCase("filename")) {
            sqlStr.append("(");
            for (int i = 0; i < keywords.size(); i++) {
                if (0 == i) {
                    sqlStr.append(" filename like :" + index);
                } else {
                    if(null!=condition&&0==condition.compareToIgnoreCase("and")){
                        sqlStr.append(" and filename like :" + index);
                    }else if(null!=condition&&0==condition.compareToIgnoreCase("or")){
                        sqlStr.append(" or filename like :" + index);
                    }else{
                        sqlStr.append(" and filename like :" + index);
                    }
                }
                map.put(index + "", "%" + keywords.get(i) + "%");
                index++;
            }
            sqlStr.append(")");
        }
        else if (0 == mode.compareToIgnoreCase("cipherindex")) {
            String retstr = dockingsecuritycloudser.searchcipherindex("AND",keywords);
            JSONObject jsonObject = new JSONObject();
            jsonObject = JSONObject.parseObject(retstr);
            JSONObject details = jsonObject.getJSONObject("details");
            JSONArray searchresult = details.getJSONArray("searchresult");
            sqlStr.append("(");
            for (int i = 0; i < searchresult.size(); i++) {
                if (0 == i) {
                    sqlStr.append(" fileuniqueid = :" + index);
                } else {
                    if(null!=condition&&0==condition.compareToIgnoreCase("and")){
                        sqlStr.append(" and fileuniqueid = :" + index);
                    }else if(null!=condition&&0==condition.compareToIgnoreCase("or")){
                        sqlStr.append(" or fileuniqueid = :" + index);
                    }else{
                        sqlStr.append(" and fileuniqueid = :" + index);
                    }
                }
                JSONObject obj = searchresult.getJSONObject(i);
                map.put(index + "", obj.getString("fileuniqueid"));
                index++;
            }
            sqlStr.append(")");

        } else {

        }

        if (null != type && 0 != type.compareToIgnoreCase("")
                && 0 != type.compareToIgnoreCase("*")) {
            sqlStr.append(" and (");
            if (type.contains("p")) {
                sqlStr.append(" filename like :" + index);
                map.put(index + "", "%.png");
                index++;
                sqlStr.append("or filename like :" + index);
                map.put(index + "", "%.jpg");
                index++;
                sqlStr.append("or filename like :" + index);
                map.put(index + "", "%.jpeg");
                index++;
            }
            if (type.contains("d")) {
                sqlStr.append(" filename like :" + index);
                map.put(index + "", "%.doc");
                index++;
            }
            if (type.contains("v")) {
                sqlStr.append(" filename like :" + index);
                map.put(index + "", "%.mp4");
                index++;
            }
            if (type.contains("m")) {
                sqlStr.append(" filename like :" + index);
                map.put(index + "", "%.mp3");
                index++;
            }
            sqlStr.append(" or filename like :" + index);
            map.put(index + "", "%.cpp");
            index++;

            sqlStr.append(")");
        }


        if (null != fromtime && 0 != fromtime.compareToIgnoreCase("") &&
                null != totime && 0 != totime.compareToIgnoreCase("")) {
            sqlStr.append(" and (");
            sqlStr.append(" uploadtime between :" + index);
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime fromtimedt = LocalDateTime.parse(fromtime,df);
            map.put(index + "", fromtimedt);
            index ++;
            sqlStr.append(" and :" + index);
            LocalDateTime totimedt = LocalDateTime.parse(totime,df);
            map.put(index + "", totimedt);
            index ++;
            sqlStr.append(")");
        }
        sqlStr.append(" and ( fileowner = :" + index+" )");
        map.put(index + "", fuser.getName());
        index ++;

        String countstr = "SELECT count(*) ";
        String substring = sqlStr.substring(0, sqlStr.indexOf("from"));
        StringBuffer countSql = new StringBuffer();
        countSql.append(sqlStr);
        StringBuffer newcountSql = countSql.replace(0, substring.length(), countstr);
        System.out.println("sql is " + sqlStr.toString());
        System.out.println("countSql is " + newcountSql.toString());
        Query query = entityManager.createNativeQuery(sqlStr.toString(), Metadata.class);
        Query countQuery = entityManager.createNativeQuery(newcountSql.toString());
        for (String key : map.keySet()) {
            query.setParameter(key, map.get(key));
            countQuery.setParameter(key, map.get(key));
        }
        long total = ((BigInteger) countQuery.getSingleResult()).longValue();
        System.out.println("total is " + total);
        query.setFirstResult(pageNo * pageSize);    //设置从第几个结果开始
        query.setMaxResults(pageSize);        //设置显示几个结果
        List<Metadata> list = query.getResultList();
        return list;
    }
}
