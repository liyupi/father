package com.yupi.father.manager;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

/**
 * 腾讯云对象存储管理类
 *
 * @author liyupi
 */
@Component
@Slf4j
public class TencentCosManager {

    /**
     * 当前环境
     */
    @Value("${spring.profiles.active:dev}")
    private String currentEnv;

    /**
     * 存储桶
     */
    private final String bucketName = "yupi-file-1256524210";

    private static final String CDN_PATH = "http://yupi-file-1256524210.file.myqcloud.com/";

    /**
     * 存储路径
     */
    private final  String originPath = "prod".equals(currentEnv) ? "father/img" : "father/test/img";

    @Resource
    private COSClient cosClient;

    /**
     * 上传文件
     * @param fileName
     * @param file
     * @return
     */
    public String uploadFile(String fileName, File file) throws CosClientException {
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = originPath + "/" + fileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        cosClient.putObject(putObjectRequest);
        return CDN_PATH + key;
    }
}
