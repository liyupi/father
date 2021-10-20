package com.yupi.father.crawler;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yupi.father.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * CrawlerJob
 *
 * @author liyupi
 * @description 单次执行
 */
public class CrawlerA extends CrawlerTemplate {

    private final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    protected CrawlerA() {
        super("A", 1, 140);
    }

    @Override
    List<Picture> doCrawler(int pageNum) {
        String s;
        try {
            s = HttpUtils.doGet(String.format("目标网站?page=%s", pageNum), null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        if (StringUtils.isNotBlank(s)) {
            PictureResponse pictureResponse = gson.fromJson(s, PictureResponse.class);
            return pictureResponse.getData();
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        CrawlerA crawlerA = new CrawlerA();
        crawlerA.run();
    }
}
