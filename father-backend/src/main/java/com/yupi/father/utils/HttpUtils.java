package com.yupi.father.utils;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Http 请求工具类
 *
 * @author liyupi
 **/
public class HttpUtils {

    /**
     * Get 请求
     *
     * @param url
     * @param params
     * @return Json String
     * @throws URISyntaxException
     */
    public static String doGet(String url, Map<String, String> params) throws URISyntaxException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 设置请求
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            for (Entry<String, String> entry : params.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        // 请求
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放连接
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                    httpClient.close();
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }
}
