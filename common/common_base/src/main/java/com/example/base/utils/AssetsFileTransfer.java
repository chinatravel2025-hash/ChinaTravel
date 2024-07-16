package com.example.base.utils;
import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 对Assets的文件做拷贝
 */
public class AssetsFileTransfer {
    public static final String TAG = "kiwi::AssetsFileTransfer";

    /**
     * 拷贝文件
     * @param context
     * @param assetsFilePath
     * @param targetFileFullPath
     */
    public static void copyFileFromAssets(Context context, String assetsFilePath, String targetFileFullPath) {

        try {
            if(assetsFilePath.endsWith(File.separator))
            {
                assetsFilePath = assetsFilePath.substring(0,assetsFilePath.length()-1);
            }
            String fileNames[] = context.getAssets().list(assetsFilePath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {
                File file = new File(targetFileFullPath);
                file.mkdirs();
                for (String fileName : fileNames) {
                    copyFileFromAssets(context, assetsFilePath + File.separator + fileName, targetFileFullPath + File.separator + fileName);
                }
            } else {//如果是文件

                File file = new File(targetFileFullPath);
//                File fileTemp = new File(targetFileFullPath+".temp");
//                if(file.exists())
//                {
//                    Log.d("Tag","文件存在");
//                    return;
//                }
                file.getParentFile().mkdir();

                InputStream is = context.getAssets().open(assetsFilePath);

                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();

//                fileTemp.renameTo(file);
            }

        } catch (Exception e) {

        }
    }

    private static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    /**
     * 把assets/${filePath}目录中的所有内容拷贝到 手机的Storage的${destPath}/目录中
     *
     * @param activity activity 使用CopyFiles类的Activity
     * @param filePath String 相对于Android APK内的assets目录的文件路径,如：AIModel.bundle
     * @param destPath String 拷贝的目标， 如：/data/data/包名/files/assets/
     */
    public static void copyAssetsDir2Phone(Context activity, String filePath, String destPath) {
        try {
            String[] fileList = activity.getAssets().list(filePath);
            if (fileList.length > 0) {//如果是目录
                File file = new File(activity.getFilesDir().getAbsolutePath() + File.separator + destPath + File.separator + filePath);
                if (file.exists()) {
                    deleteAllFiles(file);
                }
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileList) {
                    if(!filePath.isEmpty()) { ;
                        copyAssetsDir2Phone(activity, filePath + File.separator + fileName, destPath);
                    }else{
                        copyAssetsDir2Phone(activity, fileName, destPath);
                    }

                }
            } else {//如果是文件
                InputStream inputStream = activity.getAssets().open(filePath);
                File file = new File(activity.getFilesDir().getAbsolutePath() + File.separator + destPath + File.separator + filePath);
                if (file.exists()) {
                    boolean delete = file.delete();
                }
                if (!file.exists() || file.length() == 0) {
                    FileOutputStream fos = new FileOutputStream(file);
                    int len = -1;
                    byte[] buffer = new byte[1024];
                    while ((len = inputStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.flush();
                    inputStream.close();
                    fos.close();
                }
            }
        } catch (IOException e) {
        }
    }

    /**
     * 将文件从assets目录，考贝到 /data/data/包名/files/ 目录中。assets 目录中的文件，会不经压缩打包至APK包中，使用时还应从apk包中导出来
     *
     * @param fileName 文件名,如aaa.txt
     */
    public static void copyAssetsFile2Phone(Activity activity, String fileName) {
        try {
            InputStream inputStream = activity.getAssets().open(fileName);
            //getFilesDir() 获得当前APP的安装路径 /data/data/包名/files 目录
            File file = new File(activity.getFilesDir().getAbsolutePath() + File.separator + fileName);
            if (!file.exists() || file.length() == 0) {
                FileOutputStream fos = new FileOutputStream(file);//如果文件不存在，FileOutputStream会自动创建文件
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.flush();//刷新缓存区
                inputStream.close();
                fos.close();
            }
        } catch (IOException e) {
        }
    }
}
