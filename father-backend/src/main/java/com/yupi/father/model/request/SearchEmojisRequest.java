package com.yupi.father.model.request;

import com.yupi.father.base.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表情搜索封装类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchEmojisRequest extends PageRequest {

    /**
     * 名称
     */
    private String name;

    /**
     * 标签
     */
    private String tag;

    /**
     * 审核状态
     */
    private Integer reviewStatus;
}
