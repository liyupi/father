package com.yupi.father.model.enums;

/**
 * 表情审核状态枚举
 *
 * @author liyupi
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
public enum EmojiReviewStatusEnum {

    UN_REVIEW(0, "待审核"),
    REVIEW_PASS(1, "审核通过"),
    REVIEW_REJECT(2, "审核拒绝");

    private final int value;

    private final String text;

    EmojiReviewStatusEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
