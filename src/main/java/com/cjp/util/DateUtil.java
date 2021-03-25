package com.cjp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * 日期工具类
 */
public class DateUtil {
    /**
     * 得到当前时间的时间字符串（到秒）
     */
    public static String getCurrentDateStr(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
        return sdf.format(date);
    }


    public static String formatDate(Date date,String format){
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if(date!=null){
            result=sdf.format(date);
        }

        return result;
    }
}
