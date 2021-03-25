package com.cjp.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作字符串工具类
 */
public class StringUtil {
    /**
     * 在字符串前后加%
     * @param str
     * @return
     */
    public static String formatLike(String str){
        if(isNotEmpty(str)){
            return "%"+str+"%";
        }
        return null;
    }

    /**
     * 判断字符串是否为空
     *
     */
    public static boolean isNotEmpty(String str){
        if(str!=null && !" ".equals(str.trim())){
            return true;
        }
        return false;
    }
    /**
     * 判断字符串是否为空
     *
     */
    public static boolean isEmpty(String str){
        if(str==null || " ".equals(str.trim())){
            return true;
        }
        return false;
    }

    /**
     * 过滤空格
     * @param list
     * @return
     */
    public static List<String> filterWhite(List<String> list){
        List<String> resultList = new ArrayList<>();
        for(String l : list){
            if(isNotEmpty(l)){
                resultList.add(l);
            }
        }
        return resultList;
    }

}