package com.yupi.father.base;

import lombok.Data;

/**
 * 通用返回类
 * @param <T>
 */
@Data
public class BaseResponse<T> {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
