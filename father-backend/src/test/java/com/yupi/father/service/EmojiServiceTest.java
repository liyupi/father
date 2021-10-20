package com.yupi.father.service;

import com.yupi.father.model.entity.Emoji;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
class EmojiServiceTest {

    /**
     * 测试更新的数据 id
     */
    private static final int TEST_UPDATE_ID = 1;

    /**
     * 测试删除的数据 id
     */
    private static final int TEST_DELETE_ID = 2;

    @Resource
    private EmojiService emojiService;

    /**
     * 测试插入
     */
    @Test
    void testSave() {
        Emoji emoji = new Emoji();
        emoji.setUrl("test");
        emoji.setName("test");
        emoji.setUpdateTime(new Date());
        boolean save = emojiService.save(emoji);
        Assert.isTrue(save, "");
    }

    /**
     * 测试更新
     */
    @Test
    void testUpdate() {
        Emoji emoji = new Emoji();
        emoji.setId(TEST_UPDATE_ID);
        emoji.setUrl("testUpdate");
        emoji.setName("testUpdateName");
        emoji.setUpdateTime(new Date());
        boolean b = emojiService.updateById(emoji);
        Assert.isTrue(b, "");
    }

    /**
     * 测试查询列表
     */
    @Test
    void testList() {
        List<Emoji> list = emojiService.list();
        Assert.notEmpty(list, "");
    }

    /**
     * 测试查询单条数据
     */
    @Test
    void testOne() {
        Emoji byId = emojiService.getById(TEST_UPDATE_ID);
        Assert.notNull(byId, "");
    }

    /**
     * 测试删除
     */
    @Test
    void delete() {
        emojiService.removeById(TEST_DELETE_ID);
    }
}