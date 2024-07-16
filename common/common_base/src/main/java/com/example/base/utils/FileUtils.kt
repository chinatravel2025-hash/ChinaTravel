package com.example.base.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.text.TextUtils
import java.io.*
import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.MessageDigest
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream


object FileUtils {
    fun createDirDirectory(path: String) {
        File(path).let {
            if (!it.exists()) {
                it.mkdirs()
            }
        }
    }

    fun md5(file: File): String {
        try {
            val buffer = ByteArray(1024)
            var len: Int
            val digest = MessageDigest.getInstance("MD5")
            val inStream = FileInputStream(file)
            while (inStream.read(buffer).also { len = it } != -1) {
                digest.update(buffer, 0, len)
            }
            inStream.close()
            val bigInt = BigInteger(1, digest.digest())
            return bigInt.toString(16).uppercase()
        } catch (e: Exception) {
        }
        return ""
    }


    fun getFileName(path: String): String? {
        val start = path.lastIndexOf("/")
        val end = path.lastIndexOf(".")
        return if (start != -1 && end != -1) {
            path.substring(start + 1, end)
        } else {
            null
        }
    }


    fun getContentType(filename: String?): String? {
        var type: String? = null
        var path: Path? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            path = Paths.get(filename)
            try {
                type = Files.probeContentType(path)
            } catch (e: IOException) {
            }
        }
        return type
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径+文件名 如：data/user/0/com.test/files/abc.txt
     * @param newPath String 复制后路径+文件名 如：data/user/0/com.test/cache/abc.txt
     * @return `true` if and only if the file was copied;
     * `false` otherwise
     */
    fun copyFile(oldPath: String?, newPath: String?): Boolean {
        return try {
            val oldFile = File(oldPath)
            if (!oldFile.exists()) {
                return false
            } else if (!oldFile.isFile) {
                return false
            } else if (!oldFile.canRead()) {
                return false
            } else if (oldPath == newPath) {
                return true
            }
            if (newPath == null) {
                return false
            } else {
                val newFile = File(newPath)
                newFile.delete()
                val newDirPath = newPath.replaceAfter("/${getFileName(newPath)}", "")
                val newFileDir = File(newDirPath)
                if (!newFileDir.exists()) {
                    newFileDir.mkdirs()
                }
                val fileInputStream = FileInputStream(oldPath)
                val fileOutputStream = FileOutputStream(newPath)
                val buffer = ByteArray(1024)
                var byteRead: Int
                while (-1 != fileInputStream.read(buffer).also { byteRead = it }) {
                    fileOutputStream.write(buffer, 0, byteRead)
                }
                fileInputStream.close()
                fileOutputStream.flush()
                fileOutputStream.close()
                true
            }

        } catch (e: java.lang.Exception) {
            false
        }
    }

    /**
     * 复制文件夹及其中的文件
     *
     * @param oldPath String 原文件夹路径 如：data/user/0/com.test/files
     * @param newPath String 复制后的路径 如：data/user/0/com.test/cache
     * @return `true` if and only if the directory and files were copied;
     * `false` otherwise
     */
    fun copyFolder(oldPath: String, newPath: String): Boolean {
        return try {
            val newFile = File(newPath)
            if (!newFile.exists()) {
                if (!newFile.mkdirs()) {

                    return false
                }
            }
            val oldFile = File(oldPath)
            val files = oldFile.list()
            var temp: File
            for (file in files) {
                temp = if (oldPath.endsWith(File.separator)) {
                    File(oldPath + file)
                } else {
                    File(oldPath + File.separator.toString() + file)
                }
                if (temp.isDirectory) {   //如果是子文件夹
                    copyFolder("$oldPath/$file", "$newPath/$file")
                } else if (!temp.exists()) {

                    return false
                } else if (!temp.isFile) {

                    return false
                } else if (!temp.canRead()) {

                    return false
                } else {
                    val fileInputStream = FileInputStream(temp)
                    val fileOutputStream = FileOutputStream(newPath + "/" + temp.name)
                    val buffer = ByteArray(1024)
                    var byteRead: Int
                    while (fileInputStream.read(buffer).also { byteRead = it } != -1) {
                        fileOutputStream.write(buffer, 0, byteRead)
                    }
                    fileInputStream.close()
                    fileOutputStream.flush()
                    fileOutputStream.close()
                }

                /* 如果不需要打log，可以使用下面的语句
                     if (temp.isDirectory()) {   //如果是子文件夹
                         copyFolder(oldPath + "/" + file, newPath + "/" + file);
                     } else if (temp.exists() && temp.isFile() && temp.canRead()) {
                         FileInputStream fileInputStream = new FileInputStream(temp);
                         FileOutputStream fileOutputStream = new FileOutputStream(newPath + "/" + temp.getName());
                         byte[] buffer = new byte[1024];
                         int byteRead;
                         while ((byteRead = fileInputStream.read(buffer)) != -1) {
                             fileOutputStream.write(buffer, 0, byteRead);
                         }
                         fileInputStream.close();
                         fileOutputStream.flush();
                         fileOutputStream.close();
                     }
                      */
            }
            true
        } catch (e: java.lang.Exception) {
            false
        }
    }

    fun getImageSize(path: String): Pair<Int,Int> {
        val options = BitmapFactory.Options()
        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */

        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options) // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */

        return Pair(options.outWidth,options.outHeight)

    }

    /**
     * 压缩文件和文件夹
     *
     * @param srcFileString 要压缩的文件或文件夹
     * @param zipFileString 压缩完成的Zip路径
     * @throws Exception
     */
    fun zipFolder(srcFileString: String?, zipFileString: String?) {
        try {
            //创建ZIP
            val outZip = ZipOutputStream(FileOutputStream(zipFileString))
            //创建文件
            val file = File(srcFileString)
            zipFiles(file.parent + File.separator, file.name, outZip)
            //完成和关闭
            outZip.finish()
            outZip.close()
        }catch (e:Exception){
        }
    }

    /**
     * 压缩文件
     *
     * @param folderString
     * @param fileString
     * @param zipOutputSteam
     * @throws Exception
     */
    private fun zipFiles(
        folderString: String,
        fileString: String,
        zipOutputSteam: ZipOutputStream?
    ) {
        if (zipOutputSteam == null) return
        val file = File(folderString + fileString)
        if (file.isFile) {
            val zipEntry = ZipEntry(fileString)
            val inputStream = FileInputStream(file)
            zipOutputSteam.putNextEntry(zipEntry)
            var len: Int
            val buffer = ByteArray(4096)
            while ((inputStream.read(buffer).also { len = it }) != -1) {
                zipOutputSteam.write(buffer, 0, len)
            }
            zipOutputSteam.closeEntry()
        } else {
            //文件夹
            val fileList = file.list()
            //没有子文件和压缩
            if (fileList.size <= 0) {
                val zipEntry = ZipEntry(fileString + File.separator)
                zipOutputSteam.putNextEntry(zipEntry)
                zipOutputSteam.closeEntry()
            }
            //子文件和递归
            for (i in fileList.indices) {
                zipFiles("$folderString$fileString/", fileList[i], zipOutputSteam)
            }
        }
    }

    /**
     * 解压zip到指定的路径
     * @param zipFileString  ZIP的名称
     * @param outPathString   要解压缩路径
     * @throws Exception
     */
    fun unZipFolder(zipFileString: String?, outPathString: String, context: Context?) {
        val inZip = ZipInputStream(FileInputStream(zipFileString))
        var zipEntry: ZipEntry? = null
        var szName = ""
        while (inZip.nextEntry?.also { zipEntry = it } != null) {
            szName = zipEntry?.name?:""
            if (zipEntry?.isDirectory == true) {
                //获取部件的文件夹名
                szName = szName.substring(0, szName.length - 1)
                val folder = File(outPathString + File.separator.toString() + szName)
                folder.mkdirs()
            } else {
                val file = File(outPathString + File.separator.toString() + szName)
                if (!file.exists()) {
                    file.parentFile.mkdirs()
                    file.createNewFile()
                }
                // 获取文件的输出流
                val out = FileOutputStream(file)
                var len: Int
                val buffer = ByteArray(1024)
                // 读取（字节）字节到缓冲区
                while (inZip.read(buffer).also { len = it } != -1) {
                    // 从缓冲区（0）位置写入（字节）字节
                    out.write(buffer, 0, len)
                    out.flush()
                }
                out.close()
            }
        }
        inZip.close()
    }

    fun unZipFolder(zipFileString: String?, outPathString: String, szName: String) {
        var szName = szName
        val inZip = ZipInputStream(FileInputStream(zipFileString))
        var zipEntry: ZipEntry? = null
        while (inZip.nextEntry?.also { zipEntry = it } != null) {
            szName = zipEntry?.name?:""
            if (zipEntry?.isDirectory == true) {
                //获取部件的文件夹名
                szName = szName.substring(0, szName.length - 1)
                val folder = File(outPathString + File.separator.toString() + szName)
                folder.mkdirs()
            } else {
                val file = File(outPathString + File.separator.toString() + szName)
                if (!file.exists()) {
                    file.parentFile.mkdirs()
                    file.createNewFile()
                }
                // 获取文件的输出流
                val out = FileOutputStream(file)
                var len: Int
                val buffer = ByteArray(1024)
                // 读取（字节）字节到缓冲区
                while (inZip.read(buffer).also { len = it } != -1) {
                    // 从缓冲区（0）位置写入（字节）字节
                    out.write(buffer, 0, len)
                    out.flush()
                }
                out.close()
            }
        }
        inZip.close()
    }

    fun getFontPath(c:Context,name : String?):String{
        if(TextUtils.isEmpty(name)){
            return ""
        }
        return File("${PathUtil.getExternalFilesDir(c).absolutePath}${File.separator}fonts${File.separator}${name ?: ""}").absolutePath
    }

    fun getFontPathExists(c:Context,name : String?):Boolean{
        return File("${PathUtil.getExternalFilesDir(c).absolutePath}${File.separator}fonts${File.separator}${name ?: ""}").exists()
    }

    fun getAudioPath(c:Context,name: String):String{
        return "${PathUtil.getExternalFilesDir(c) ?: ""}/data/wake/${name}.pcm"
    }
    fun getVideoPath(c:Context,name: String):String{
        return "${PathUtil.getExternalFilesDir(c) ?: ""}/data/video/${name}.mp4"
    }
    fun getSnapshotPath(c:Context,name: String):String{
        return "${PathUtil.getExternalFilesDir(c) ?: ""}/data/snapshot/${name}.jpg"
    }
}