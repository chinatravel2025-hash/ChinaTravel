package com.aws.bean.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

/**
 * Desc:
 * <p>
 * Author: li yi
 * Date: 2020/9/9
 * Copyright: Copyright (c) 2016-2020
 * Update Comments:
 * 构建配置参见:
 *
 * @author GuoFeiFei
 */
public class GsonUtil {

    private static final Gson INSTANCE = new GsonBuilder().registerTypeAdapter(Double.class, (JsonSerializer) (src, typeOfSrc, context) -> {
        if(src instanceof Double){
            Double s = (Double) src;
            if (s == s.longValue()) return new JsonPrimitive(s.longValue());
            return new JsonPrimitive(s);
        }
        return null;
    }).create();


    public static Gson getInstance() {
        return INSTANCE;
    }

    /**
     * 获取Gson实例，由于Gson是线程安全的，这里共同使用同一个Gson实例
     */
    public static String toJson(Object c) {
        return INSTANCE.toJson(c);
    }

    public static String toJsonSelf(Object c) {
        try {
            return INSTANCE.toJson(c);
        } catch (Exception e) {
        }
        return null;
    }

    public static <T> T fromJson(String json, Type type) throws JsonSyntaxException {
        return INSTANCE.fromJson(json, type);
    }

    public static <T> T fromJsonSelf(String json, Type type) throws JsonSyntaxException {
        try {
            return INSTANCE.fromJson(json, type);
        } catch (Exception e) {
        }
        return null;
    }

    public static <T> T fromJson(JsonElement json, Class<T> clazz) throws JsonSyntaxException {
        return INSTANCE.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonSyntaxException {
        return INSTANCE.fromJson(json, clazz);
    }

    public static <T> T fromJsonSelf(String json, Class<T> clazz) {
        try {
            return INSTANCE.fromJson(json, clazz);
        } catch (Exception e) {
            e.toString();
        }
        return null;
    }

    public static JsonElement replaceKey(String json) {
        JsonElement source = new JsonParser().parse(json);
        if (source == null || source.isJsonNull()) {
            return JsonNull.INSTANCE;
        }
        if (source.isJsonPrimitive()) {
            return source;
        }
        if (source.isJsonArray()) {
            JsonArray jsonArr = source.getAsJsonArray();
            JsonArray jsonArray = new JsonArray();
            jsonArr.forEach(item -> {
                jsonArray.add(replaceKey(item));
            });
            return jsonArray;
        }
        if (source.isJsonObject()) {
            JsonObject jsonObj = source.getAsJsonObject();
            Iterator<Map.Entry<String, JsonElement>> iterator = jsonObj.entrySet().iterator();
            JsonObject newJsonObj = new JsonObject();
            iterator.forEachRemaining(item -> {
                String key = item.getKey();
                JsonElement value = item.getValue();
                key = key.replaceAll("_","");
                newJsonObj.add(key, replaceKey(value));
            });

            return newJsonObj;
        }
        return JsonNull.INSTANCE;
    }

    public static JsonElement replaceKey(JsonElement source) {
        if (source == null || source.isJsonNull()) {
            return JsonNull.INSTANCE;
        }
        if (source.isJsonPrimitive()) {
            return source;
        }
        if (source.isJsonArray()) {
            JsonArray jsonArr = source.getAsJsonArray();
            JsonArray jsonArray = new JsonArray();
            jsonArr.forEach(item -> {
                jsonArray.add(replaceKey(item));
            });
            return jsonArray;
        }
        if (source.isJsonObject()) {
            JsonObject jsonObj = source.getAsJsonObject();
            Iterator<Map.Entry<String, JsonElement>> iterator = jsonObj.entrySet().iterator();
            JsonObject newJsonObj = new JsonObject();
            iterator.forEachRemaining(item -> {
                String key = item.getKey();
                JsonElement value = item.getValue();
                key = key.replaceAll("_","");
                newJsonObj.add(key, replaceKey(value));
            });

            return newJsonObj;
        }
        return JsonNull.INSTANCE;
    }

}
