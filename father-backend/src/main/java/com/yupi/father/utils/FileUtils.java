package com.yupi.father.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 文件工具类
 *
 * @author liyupi
 */
public class FileUtils {

    /**
     * 下载远程图片至到本地
     *
     * @param remoteUrl
     * @throws IOException
     */
    public static File downloadFile(String remoteUrl) throws IOException {
        // 构造URL
        URL url = new URL(remoteUrl);
        // 打开连接
        URLConnection con = url.openConnection();
        con.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        con.setConnectTimeout(50000);
        con.setReadTimeout(50000);
        // 输入流
        InputStream is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        String fileSuffix = FileUtils.getFileSuffix(remoteUrl);
        File file = File.createTempFile("fatherTmpFile", fileSuffix);
        try (FileOutputStream os = new FileOutputStream(file, true)) {
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        }
        is.close();
        return file;
    }

    public static String getFileSuffix(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return "";
        }
        return filePath.substring(filePath.lastIndexOf("."));
    }

}
