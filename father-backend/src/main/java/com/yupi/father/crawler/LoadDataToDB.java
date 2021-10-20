package com.yupi.father.crawler;

import com.yupi.father.constant.MyConstant;
import com.yupi.father.manager.TencentCosManager;
import com.yupi.father.model.entity.Emoji;
import com.yupi.father.service.EmojiService;
import com.yupi.father.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 结果转存数据库（单次执行）
 *
 * @author liyupi
 */
@Component
@Slf4j
@RestController("/job")
public class LoadDataToDB {

    @Resource
    private EmojiService emojiService;

    @Resource
    private TencentCosManager tencentCosManager;

    /**
     * 初始化 DB 数据
     * @throws IOException
     */
    @GetMapping("/loadData")
    public String loadData() throws IOException, InterruptedException {
        List<String> lines = Files.readAllLines(Paths.get("换成自己的地址"));
        final String regString = "description='(.*)', imageUrl='(.*)'";
        Pattern pattern = Pattern.compile(regString);
        int currentNum = 0;
        int succeedNum = 0;
        for (String line : lines) {
            log.info("currentNum = " + (++currentNum));
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String name = matcher.group(1);
                String url = matcher.group(2);
                if (StringUtils.isAnyBlank(name, url) || name.length() > 40) {
                    continue;
                }
                File downloadFile;
                try {
                    downloadFile = FileUtils.downloadFile(url);
                } catch (IOException e) {
                    log.error("fileDownload error, currentNum = " + currentNum);
                    continue;
                }
                String fileName = System.currentTimeMillis() / 1000 + "-" + downloadFile.getName();
                // 图片上传
                String resultPath = tencentCosManager.uploadFile(fileName, downloadFile);
                // 清理临时文件
                boolean deleteResult = downloadFile.delete();
                if (!deleteResult) {
                    log.error("fileDelete error", downloadFile.getName());
                }
                Emoji emoji = new Emoji();
                emoji.setUrl(resultPath);
                emoji.setName(name);
                emoji.setUserId(MyConstant.SYSTEM_USER_ID);
                emojiService.save(emoji);
                log.info("succeedNum = " + (++succeedNum));
                Thread.sleep(100);
            }
        }
        return "ok";
    }
}
