package com.yupi.father.exception;

/**
 * 错误码枚举
 *
 * @author liyupi
 */
public enum ErrorCodeEnum {
    /**
     * 用户错误
     */
    REQUEST_PARAMS_ERROR(40000, "请求参数错误"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR(50000, "系统错误"),

    /**
     * 文件上传错误
     */
    FILE_UPLOAD_ERROR(50010, "文件上传错误"),

    /**
     * 未上线
     */
    CANNOT_USE(59999, "未上线");

    private final int code;

    private final String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
