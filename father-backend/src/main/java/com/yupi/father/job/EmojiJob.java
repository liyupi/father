package com.yupi.father.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.father.model.entity.Emoji;
import com.yupi.father.service.EmojiService;
import com.yupi.father.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 表情相关任务
 *
 * @author liyupi
 */
@Component
@Slf4j
public class EmojiJob {

    @Resource
    private EmojiService emojiService;

    /**
     * 检测表情（过滤空数据）
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    private void checkEmoji() {
        log.info("checkEmojiJob 开始");
        // 1. 获取 2 天内的数据
        QueryWrapper<Emoji> emojiQueryWrapper = new QueryWrapper<>();
        emojiQueryWrapper.gt("updateTime", DateUtils.getBeforeDate(2));
        List<Emoji> emojiList = emojiService.list(emojiQueryWrapper);
        // 2. 检测
        if (CollectionUtils.isEmpty(emojiList)) {
            log.info("暂无表情");
            return;
        }
        // 有问题的数据总数
        int errorNum = 0;
        // 成功处理的数据总数
        int succeedNum = 0;
        // 3. 处理
        for (Emoji emoji : emojiList) {
            if (StringUtils.isAnyBlank(emoji.getName(), emoji.getUrl())) {
                log.error("表情数据异常 {}", emoji);
                errorNum++;
                boolean deleteResult = emojiService.removeById(emoji.getId());
                if (deleteResult) {
                    succeedNum++;
                }
            }
        }
        // 4. 通知（日志）
        log.info("checkEmojiJob 结束，异常数据 {} 个，已修复 {} 个", errorNum, succeedNum);
    }

}
