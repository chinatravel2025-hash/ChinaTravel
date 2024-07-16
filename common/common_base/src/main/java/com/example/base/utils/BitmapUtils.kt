package com.example.base.utils

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import com.example.base.base.App
import com.example.peanutmusic.base.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.ByteBuffer


object BitmapUtils {
    /*
       * 保存文件，文件名为当前日期
       */
    fun saveBitmap(bitmap: Bitmap, bitName: String , context: Context): String? {
        val fileName: String
        val file: File
        val brand = Build.BRAND
        fileName = if (brand == "xiaomi") { // 小米手机brand.equals("xiaomi")
            Environment.getExternalStorageDirectory().path + "/DCIM/Camera/" + bitName
        }
        else if (brand.equals("Huawei", ignoreCase = true) && !OSUtil.isHarmonyOs()) {//华为 这里鸿蒙系统跟普通华为不一样
            Environment.getExternalStorageDirectory().path + "/DCIM/Camera/" + bitName
        }
        else if (brand.equals("Huawei", ignoreCase = true) && OSUtil.isHarmonyOs()) {//华为 这里鸿蒙系统跟普通华为不一样
            Environment.getExternalStorageDirectory().path + "/DCIM/" + bitName
        }
        else { // Meizu 、Oppo
            Environment.getExternalStorageDirectory().path + "/DCIM/" + bitName
        }
        if (Build.VERSION.SDK_INT >= 29) {
            saveSignImage(bitName, bitmap ,context)
            return "${fileName}.jpg"
        } else {
            file = File(fileName)
        }
        if (file.exists()) {
            file.delete()
        }
        val out: FileOutputStream
        try {
            out = FileOutputStream(file)
            // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush()
                out.close()
                // 插入图库
                if (Build.VERSION.SDK_INT >= 29) {
                    val values = ContentValues()
                    values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                } else {
                    MediaStore.Images.Media.insertImage(context.contentResolver, file.absolutePath, bitName, null)
                }
            }
        } catch (e: FileNotFoundException) {

            return null
        } catch (e: IOException) {

            return null
        } catch (e: Exception) {

            return null

// 发送广播，通知刷新图库的显示
        }
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://$fileName")))
        return "${fileName}.jpg"
    }

    //将文件保存到公共的媒体文件夹
    //这里的filepath不是绝对路径，而是某个媒体文件夹下的子路径，和沙盒子文件夹类似
    //这里的filename单纯的指文件名，不包含路径
    fun saveSignImage(fileName: String?, bitmap: Bitmap , context: Context) {
        try {
            //设置保存参数到ContentValues中
            val contentValues = ContentValues()
            //设置文件名
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            //兼容Android Q和以下版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //android Q中不再使用DATA字段，而用RELATIVE_PATH代替
                //RELATIVE_PATH是相对路径不是绝对路径
                //DCIM是系统文件夹，关于系统文件夹可以到系统自带的文件管理器中查看，不可以写没存在的名字
                contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/")
                //contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Music/signImage");
            } else {
                contentValues.put(MediaStore.Images.Media.DATA, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path)
            }
            //设置文件类型
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/JPEG")
            //执行insert操作，向系统文件夹中添加文件
            //EXTERNAL_CONTENT_URI代表外部存储器，该值不变
            val uri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            if (uri != null) {
                //若生成了uri，则表示该文件添加成功
                //使用流将内容写入该uri中即可
                val outputStream: OutputStream? = context.contentResolver.openOutputStream(uri)
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                    outputStream.flush()
                    outputStream.close()
                }
            }
        } catch (e: Exception) {
        }
    }
    fun captureView(view: View): Bitmap? {
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        // 重新测量一遍View的宽高
        view.measure(View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(view.height, View.MeasureSpec.EXACTLY))
        // 确定View的位置
        view.layout(view.x.toInt(), view.y.toInt(), view.x.toInt() + view.measuredWidth,
                view.y.toInt() + view.measuredHeight)
        // 生成View宽高一样的Bitmap
        val bitmap = Bitmap.createBitmap(view.drawingCache, 0, 0, view.measuredWidth,
                view.measuredHeight)
        view.isDrawingCacheEnabled = false
        view.destroyDrawingCache()
        return bitmap
    }

    fun byteToBitmap(byteArray: ByteArray?): Bitmap? {
        val stitchBmp = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val buffer = ByteBuffer.wrap(byteArray)
        stitchBmp.copyPixelsFromBuffer(buffer)
        return stitchBmp
    }

    fun convertToWebp(source: File,outFile:File, quality: Int  = 75):Boolean{

        var bmp:Bitmap? = null
        try{

           if(source == null || outFile == null){
               return false
           }
            bmp = BitmapFactory.decodeFile(source.absolutePath)
            FileOutputStream(outFile).use { out ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    bmp?.compress(
                        Bitmap.CompressFormat.WEBP_LOSSY, // or WEBP_LOSSY
                        quality,out)
                } else {
                    bmp?.compress(Bitmap.CompressFormat.WEBP,quality,out)
                }
            }

        }catch (e:Throwable){
            LogUtils.e("webp",e?.message?: "convert excetpion")
            return false
        }finally {
            bitmapCycle(bmp)
        }

        return true;
    }


    fun bitmapCycle(bmp:Bitmap?){
        if(bmp != null && !bmp.isRecycled){
            bmp.recycle()
        }
    }



     fun copyDrawable(drawable: Drawable): Drawable?{

        if(drawable is BitmapDrawable){
            var originalDrawable = drawable; // 原始的 BitmapDrawable
            var originalBitmap = originalDrawable.getBitmap();
            // 创建一个完全拷贝的 Bitmap
            var copiedBitmap = originalBitmap.copy(originalBitmap.getConfig(), originalBitmap.isMutable());
            // 使用新的 Bitmap 创建一个新的 BitmapDrawable
            var copiedDrawable =  BitmapDrawable(null, copiedBitmap);

            return copiedDrawable
        }

        return null

    }


    // Convert Bitmap to byte array
    fun bitmapToBytes(bitmap: Bitmap): ByteArray? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }


    fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap? {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = pixels.toFloat()
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = App.getApp().resources.getColor( R.color.transparent)
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }


    /**
     * filePath 获取宽高.
     * 获取文件宽高.
     */
    fun decodeBitmapBoundFromFile(
        filePath: String
    ): BitmapFactory.Options? {
        if (!File(filePath).exists()) {
            return null
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)
        return options
    }


    @JvmStatic
    @Throws(java.lang.Exception::class)
    fun compress(
        path: String,
        targetW: Int,
        targetH: Int,
        config: Bitmap.Config?
    ): Bitmap? {
        val newOpts = BitmapFactory.Options()
        newOpts.inJustDecodeBounds = true
        newOpts.inPreferredConfig = config
        newOpts.inDither = true
        BitmapFactory.decodeFile(path,newOpts)
        val w = newOpts.outWidth
        val h = newOpts.outHeight
        val hh = (if (targetH <= 0) 640 else targetH).toFloat()
        val ww = (if (targetW <= 0) 480 else targetW).toFloat()
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        var be = 1 //be=1表示不缩放
        if (w > h && w > ww) { //如果宽度大的话根据宽度固定大小缩放
            be = (newOpts.outWidth / ww).toInt()
        } else if (w < h && h > hh) { //如果高度高的话根据宽度固定大小缩放
            be = (newOpts.outHeight / hh).toInt()
        }
        if (be <= 0) be = 1
        newOpts.inSampleSize = be //设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false
        newOpts.inPreferredConfig = config
        val bitmap: Bitmap = BitmapFactory.decodeFile(path,newOpts)
        LogUtils.i("BitmapUtil", "size = " + bitmap.width + " * " + bitmap.height)
        return bitmap //压缩好比例大小后再进行质量压缩
    }



}