package com.yupi.father.model.request;

import lombok.Data;

/**
 * 表情审核封装类
 *
 * @author liyupi
 */
@Data
public class ReviewEmojiRequest {

    /**
     * 名称
     */
    private int id;

    /**
     * 审核状态
     */
    private int reviewStatus;
}
