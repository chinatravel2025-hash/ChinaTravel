package com.example.base.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author liyi
 */
public class ChannelInfo {
    private static final String CHANNEL_KEY = "channel_";

    private static final String DEFAULT_CHANNEL = "ours";

    private static final String CHANNEL_REPLACE = "META-INF/" + CHANNEL_KEY;

    private static String extractedChannel = null;

    public static String getChannelName(Context context) {
        if (extractedChannel != null) {
            return extractedChannel;
        }
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        String ret = "ours";
        ZipFile zipfile = null;

        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration entries = zipfile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String entryName = entry.getName();
                if (entryName.startsWith(CHANNEL_REPLACE)) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException var18) {
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException var17) {
                }
            }

        }
        String channel = ret.replace(CHANNEL_REPLACE, "");
        extractedChannel = channel;
        return channel;
    }


    public static String getChannelNameFromAssets(){
        try{
            String jsonString = JsonUtil.fileReadFromAssets("channel.json");

            if(TextUtils.isEmpty(jsonString)){
                return  DEFAULT_CHANNEL;
            }
            // 创建 Gson 对象
            Gson gson = new Gson();

            // 创建 Type 对象，用于指定要解析的数据类型
            Type type = new TypeToken<HashMap<String, Object>>() {}.getType();

            // 使用 Gson 解析 JSON 字符串为 HashMap
            HashMap<String,Object> map = gson.fromJson(jsonString, type);
            if(map != null ){
                Object channelConfig = map.get("channel_name");
                if(channelConfig != null){
                    return channelConfig.toString();
                }
            }
        }catch (Exception e){

        }

        return DEFAULT_CHANNEL;


    }

}
