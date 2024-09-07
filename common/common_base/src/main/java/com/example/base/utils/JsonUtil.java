package com.example.base.utils;


import android.text.TextUtils;

import com.example.base.base.App;
import com.example.base.base.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;

import javax.annotation.Nullable;

/**
 * 获取签名工具类
 */
public class JsonUtil {

    public static String fileWriter(String fileExt,String c) {
        String fileName = fileExt+".json";
        try {
            File root = new File(App.getContext().getExternalFilesDir(null).getAbsolutePath() + File.separator + "json", String.valueOf(User.INSTANCE.getUid()));
            root.delete();
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, fileName);
            FileWriter writer = new FileWriter(file, true);
            writer.append(c);
            writer.flush();
            writer.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * 读取方法
     *
     * @param fileName
     * 文件的名称
     * @return
     */
    public static String fileRead(String fileName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            FileInputStream inStream = App.getContext().openFileInput(fileName);
            int len = 0;
            byte[] data = new byte[1024];
            while ((len = inStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return outputStream.toString();
    }


    /**
     * 读取方法
     *
     * @param fileName
     * 文件的名称
     * @return
     */
    public static String fileReadFromAssets(String fileName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            InputStream inStream =  App.getContext().getResources().getAssets().open(fileName);
//            FileInputStream fileInputStream FileInputStream(inStream);
            int len = 0;
            byte[] data = new byte[1024];
            while ((len = inStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return outputStream.toString();
    }


    public static @Nullable HashMap<String, Object> convertJsonStringToHashMap(String jsonString) {
        try{
            if (TextUtils.isEmpty(jsonString)){
                return null;
            }
            // 创建 Gson 对象
            Gson gson = new Gson();

            // 创建 Type 对象，用于指定要解析的数据类型
            Type type = new TypeToken<HashMap<String, Object>>() {}.getType();

            // 使用 Gson 解析 JSON 字符串为 HashMap
            return gson.fromJson(jsonString, type);
        }catch (Exception e){
            return null;
        }

    }

}