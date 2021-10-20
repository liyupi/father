package com.yupi.father.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author liyupi
 */
public class DateUtils {

    /**
     * 获取当前时间的前 N 天
     * @param days 天数
     * @return
     */
    public static Date getBeforeDate(int days){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        return calendar.getTime();
    }

}
