package com.xidian.bookstore.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    /**
     * 获取特定格式时间的字符串形式
     * @param pattern 特定格式
     * @return 时间的字符串
     */
    public static String dateToString(String pattern){
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date());
    }

    /**
     * 获取当前时间的Timestamp类型
     * @return
     */
    public static Timestamp getTimestamp(){
        return new Timestamp(new Date().getTime());
    }

    /**
     * 获取特定时间的long型
     * @param date
     * @return
     */
    public static long getLongTime(Date date){
        return date.getTime();
    }


}
