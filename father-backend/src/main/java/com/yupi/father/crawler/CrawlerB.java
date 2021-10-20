package com.yupi.father.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CrawlerB
 *
 * @author liyupi
 * @description 单次执行
 */
public class CrawlerB extends CrawlerTemplate {

    protected CrawlerB() {
        super("B", 1, 3632);
    }

    @Override
    List<Picture> doCrawler(int pageNum) {
        Document doc;
        try {
            doc = Jsoup.connect(String.format("目标网站?page=%s", pageNum)).get();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        Elements elements = doc.select(".image_dta");
        return elements.stream().map(element -> {
            String imageUrl = element.attr("data-original");
            String description = element.attr("alt");
            Picture picture = new Picture();
            picture.setDescription(description);
            picture.setImageUrl(imageUrl);
            return picture;
        }).collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        CrawlerB crawlerB = new CrawlerB();
        crawlerB.run();
    }

}
