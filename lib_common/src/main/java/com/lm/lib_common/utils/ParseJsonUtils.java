package com.lm.lib_common.utils;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class ParseJsonUtils {

    /**
     * 根据Key获取Json中的Value
     * @param jsonStr json字符串
     * @param key Key值
     **/
    public static String getStringByKey(String jsonStr,String key){
        String value = null;
        try {
            JSONObject jo = new JSONObject(jsonStr);
            value = jo.getString(key);
        }catch (Exception e){
        }
        return value;
    }

    /**
     * 获取Bean实体
     * @param jsonStr json字符串
     * @param bean 实体Class
     **/
    public static <T> T getBean(String jsonStr,Class<T> bean){
        try {
            return new Gson().fromJson(jsonStr, bean);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取Bean实体 List集合
     * @param jsonStr
     * @param type new TypeToken<List<T>>() {}.getType()
     **/
    public static <T> List<T> getBeanList(String jsonStr,Type type){
        try {
            return new Gson().fromJson(jsonStr,type);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取Bean实体
     * @param bean 实体Class
     **/
    public static String getjsonStr(Object bean){
        try {
            return new Gson().toJson(bean);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
