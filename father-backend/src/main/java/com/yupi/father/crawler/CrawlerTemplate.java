package com.yupi.father.crawler;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CrawlerTemplate
 *
 * @author liyupi
 * @description 单次执行
 */
public abstract class CrawlerTemplate {

    private final String name;

    private final int startPage;

    private final int maxPage;

    protected CrawlerTemplate(String name, int startPage, int maxPage) {
        this.name = name;
        this.startPage = startPage;
        this.maxPage = maxPage;
    }

    /**
     * 图片
     */
    @Data
    protected static final class Picture {
        private String description;
        private String imageUrl;

        @Override
        public String toString() {
            return "Picture{" +
                    "description='" + description + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    '}';
        }
    }

    /**
     * 用于接受响应值
     */
    @Data
    protected static final class PictureResponse {
        private List<Picture> data;
    }

    /**
     * 具体抓取
     * @param pageNum
     * @return
     */
    abstract List<Picture> doCrawler(int pageNum);

    /**
     * 执行
     * @throws IOException
     * @throws InterruptedException
     */
    public void run() throws IOException, InterruptedException {
        List<String> lines = new ArrayList<>();
        for (int i = startPage; i <= maxPage; i++) {
            System.out.println(String.format("page %s start", i));
            List<Picture> pictureList = doCrawler(i);
            for (Picture picture : pictureList) {
                lines.add(picture.toString());
            }
            System.out.println(String.format("page %s end, succeedNum = %s", i, lines.size()));
            Thread.sleep((long) (500 + Math.random() * 500));
        }
        Collections.shuffle(lines);
        // 导出
        Files.write(Paths.get(String.format("crawler-%s.txt", name)), lines);
    }
}
