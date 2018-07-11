package com.lm.lib_common.utils;


import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;

public class ParamsUtils {

  /*  *//**
     * 将map设置到参数中
     * @param map map集合
     * @return RequestParams 对象
     *//*
    public static RequestParams putMap(Map<String,String> map){
        if(null == map)
            return null;
        RequestParams params = new RequestParams();
        Set<String> keySet = map.keySet();
        for(String key:keySet){
            String value = map.get(key);
            params.put(key,value);
        }
        return params;
    }*/
    /**
     * 将map设置到参数中
     * @param
     * @return RequestParams 对象
     */
    public static FormBody putMap1(Map<String,String> param) {
        if (param==null) {
            param=new HashMap<>();
        }
        FormBody.Builder builder = new FormBody.Builder();

        Set<String> strings = param.keySet();
        if (strings==null) {
            strings=new HashSet<>();
        }
        try{

            for (String s : strings) {
                builder.add(s,param.get(s));
            }
        }
        catch (Exception ex)
        {
            Log.e("error", ex.getMessage());
        }
        return builder.build();
    }
    /**
     * 将url地址中的参数转换成map集合
     * @param url url地址
     * @return map集合
     */
    public static Map<String,String> castUrl2Map(String url){
        Map<String,String> map = null;
        int position = url.indexOf("?");
        if(position != -1) {
            map = new HashMap<>();
            url = url.substring(position + 1, url.length());
            String[] temp = url.split("&");
            for (String s : temp) {
                String[] temp2 = s.split("=");
                map.put(temp2[0], temp2[1]);
            }
        }
        return map;
    }

    /**
     * 将map设置到Url中
     * @param sourceLink 源链接地址
     * @param map map集合
     * @return 拼装后的Url地址
     */
    public static String castMap2Url(String sourceLink,Map<String,String> map){
        if(null == map)
            return sourceLink;
        StringBuilder sb = new StringBuilder();
        Set<String> keySet = map.keySet();
        int i = 0;
        for(String key:keySet){
            String value = map.get(key);
            if(i == 0){
                sb.append("?"+key+"="+value);
            }else{
                sb.append("&"+key+"="+value);
            }
            i++;
        }
        return sourceLink+sb.toString();
    }

    public void  aa()
    {
         Map<String, String> map = new HashMap<String, String>();
        map.put("bbb", "哈哈1");
        map.put("aaa", "哈哈2");
        map.put("_ccc", "哈哈3");
        map.put("dd", "哈哈4");
        map.put("cc", "哈哈5");
        List<Map.Entry<String,String>> list =
                new ArrayList<Map.Entry<String,String>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                return (o1.getKey().compareTo(o2.getKey()));
            }
        });


    }

}

