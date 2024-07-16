package com.example.base.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description:获取根目录
 * @Author: li_yi
 * @CreateDate: 2023/2/22 13:38
 */
public class PathUtil {
    public static File getRootFile(Context context) {
        File file;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            File externalFileDir = context.getExternalFilesDir(null);
            do {
                externalFileDir = Objects.requireNonNull(externalFileDir).getParentFile();
            } while(Objects.requireNonNull(externalFileDir).getAbsolutePath().contains("/Android"));
            file = Objects.requireNonNull(externalFileDir);
        } else {
            file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile().toURI());
        }
        return file;
    }

    public static File getExternalFilesDir(Context context) {
        return context.getExternalFilesDir(null);
    }

    /**
     * 读取SD卡中文本文件
     *
     * @param fileName
     * @return
     */
    public static String readSDFile(Context context,String fileName) {
        StringBuffer sb = new StringBuffer();
        File file = new File(getExternalFilesDir(context) + "/" + fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            int c;
            while ((c = fis.read()) != -1) {
                sb.append((char) c);
            }
            fis.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return sb.toString();
    }


    /**
     * 读取SD卡中文本文件
     *
     * @param fileName
     * @return
     */
    public static List<String> readSensitiveFile(Context context,String fileName) {
        List<String> data = new ArrayList<>();
        File file = new File(getExternalFilesDir(context) + "/" + fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {

                data.add(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return data;
    }
}
