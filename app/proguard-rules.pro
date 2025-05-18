

#基础

#<基础>
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep public class * extends androidx.appcompat.app.AppCompatActivity

#视图中的get 和 set 不能混淆, 不然无法工作
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, java.lang.Boolean);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}



#保留R文件中的静态字段和方法
-keepclassmembers class *.R$ {
    public static <fields>;
    public static <methods>;
}

#parcelable 中的CREATOR类
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}
-keep class * implements android.os.Parcelable { *;}
-keep class * implements java.io.Serializable { *;}


-keepclasseswithmembernames class * {
    native <methods>;
}


#反射问题.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}



-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

-keep class com.tencent.imsdk.** { *; }


#databinding 组件相关的
-keep class androidx.databinding.** { *; }
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keepattributes *Annotation*
-keepattributes javax.xml.bind.annotation.*
-keepattributes javax.annotation.processing.*
-keepclassmembers class * extends java.lang.Enum { *; }
-keepclasseswithmembernames class android.**
-keepclasseswithmembernames interface android.**
-keepclassmembers class * {
    @androidx.databinding.* *;
}
-keepclassmembers class * {
    @androidx.annotation.** *;
}
-keepclassmembers class * {
    @androidx.databinding.** *;
}

################ ViewBinding & DataBinding ###############
-keepclassmembers class * implements androidx.databinding.ViewDataBinding{
  public static * inflate(android.view.LayoutInflater);
  public static * inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
  public static * bind(android.view.View);
}








#eventbus 必须要，否则GG
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# If using AsyncExecutord, keep required constructor of default event used.
# Adjust the class name if a custom failure event type is used.
-keepclassmembers class org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
# Accessed via reflection, avoid renaming or removal
-keep class org.greenrobot.eventbus.android.AndroidComponentsImpl



# 环信，几乎就是全部类了
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**



#lottie 库，主要是用到了反射.
-keep class com.airbnb.lottie.** { *;}




# 阿里路由库，不能混淆，尤其是生成的服务
-keep class com.alibaba.android.** { * ;}
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
# If you use the byType method to obtain Service, add the following rules to protect the interface:
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
# If single-type injection is used, that is, no interface is defined to implement IProvider, the following rules need to be added to protect the implementation
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider






# 支付宝SDK不混淆
-keep class com.alipay.** { *; }
-dontwarn com.alipay.**


# Gson库不混淆
-keep class com.google.gson.** { *; }
-keepclassmembers class com.google.gson.** {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keepattributes *Annotation*
-dontwarn com.google.gson.**








#做了一些处理
-keep class com.aws.bean.** {
    *;
}

-keep class *.entities.** {
    *;
}

-keep class * implements com.aws.bean.proguard.ProguardKeepUnit {
    *;
}




#unity sdk
-keep class bitter.jnibridge.* { *; }
-keep class com.unity3d.player.* { *; }
-keep interface com.unity3d.player.IUnityPlayerLifecycleEvents { *; }
-keep class org.fmod.* { *; }
-keep class com.google.androidgamesdk.ChoreographerCallback { *; }
-keep class com.google.androidgamesdk.SwappyDisplayManager { *; }
-ignorewarnings






#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}
# Uncomment for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule






#XRouter:使用Fragment拦截器
-keep class com.alibaba.android.arouter.facade.Postcard{*;}
#毛玻璃效果组件库
-keep class android.support.v8.renderscript.** { *; }
-keep class androidx.renderscript.** { *; }
-keep class com.iflytek.**{*;}
-keep class io.agora.**{*;}
-keep class **.zego.**{*;}
#视频播放组件
-keep class com.shuyu.gsyvideoplayer.video.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.video.**
-keep class com.shuyu.gsyvideoplayer.video.base.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.video.base.**
-keep class com.shuyu.gsyvideoplayer.utils.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.utils.**
-keep class tv.danmaku.ijk.** { *; }
-dontwarn tv.danmaku.ijk.**
-keep class com.google.android.exoplayer2.** {*;}
-keep interface com.google.android.exoplayer2.**

-keep class com.shuyu.alipay.** {*;}
-keep interface com.shuyu.alipay.**



#'com.github.centerzx:ShapeBlurView:1.0.5'
-keep class android.support.v8.renderscript.** { *; }
-keep class androidx.renderscript.** { *; }

-keep class com.umeng.**{*;}

-keepclassmembers class *{
public <init> (org.json.JSONObject);
}



#腾讯bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}


#百度新闻源
-ignorewarnings
-dontwarn com.baidu.mobads.sdk.api.**
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}




-keep class com.baidu.mobads.** { *; }
-keep class com.style.widget.** {*;}
-keep class com.component.** {*;}
-keep class com.baidu.ad.magic.flute.** {*;}
-keep class com.baidu.mobstat.forbes.** {*;}
#9.22版本新增加混淆，9.25版本不再要求
-keep class android.support.v7.widget.RecyclerView {*;}
-keepnames class android.support.v7.widget.RecyclerView$* {
    public <fields>;
    public <methods>;
}
-keep class android.support.v7.widget.LinearLayoutManager {*;}
-keep class android.support.v7.widget.PagerSnapHelper {*;}
-keep class android.support.v4.view.ViewCompat {*;}
-keep class android.support.v4.util.LongSparseArray {*;}
-keep class android.support.v4.util.ArraySet {*;}
-keep class android.support.v4.view.accessibility.AccessibilityNodeInfoCompat {*;}

#如果接入微信小游戏调起，需按微信要求添加以下keep
-keep class com.tencent.mm.opensdk.** {
    *;
}
-keep class com.tencent.wxop.** {
    *;
}
-keep class com.tencent.mm.sdk.** {
    *;
}





#protobuf 相关的实体类，反序列化用的.
-keep class com.example.**{
    *;
}
#google 相关的一些代码，保留.
-keep class com.google.** { *; }

-keep class com.google.f



#极光一键登录
  -dontoptimize
        -dontpreverify

        -dontwarn cn.jpush.**
        -keep class cn.jpush.** {*;}
        -dontwarn cn.jiguang.**
        -keep class cn.jiguang.** {*;}

        -dontwarn cn.com.chinatelecom.**
        -keep class cn.com.chinatelecom.** {*;}
        -dontwarn com.ct.**
        -keep class com.ct.** {*;}
        -dontwarn a.a.**
        -keep class a.a.** {*;}
        -dontwarn com.cmic.**
        -keep class com.cmic.** {*;}
        -dontwarn com.unicom.**
        -keep class com.unicom.** {*;}
        -dontwarn com.sdk.**
        -keep class com.sdk.** {*;}

        -dontwarn com.sdk.**
        -keep class com.sdk.** {*;}


        #极光推送
        -dontwarn com.vivo.push.**
        -keep class com.vivo.push.**{*; }
        -keep class com.vivo.vms.**{*; }


        -dontwarn com.coloros.mcsdk.**
        -keep class com.coloros.mcsdk.** { *; }

        -dontwarn com.heytap.**
        -keep class com.heytap.** { *; }

        -dontwarn com.mcs.**
        -keep class com.mcs.** { *; }


        -dontwarn com.meizu.cloud.**
        -keep class com.meizu.cloud.** { *; }

        -ignorewarnings
        -keepattributes *Annotation*
        -keepattributes Exceptions
        -keepattributes InnerClasses
        -keepattributes Signature
        -keepattributes SourceFile,LineNumberTable
        -keep class com.hianalytics.android.**{*;}
        -keep class com.huawei.updatesdk.**{*;}
        -keep class com.huawei.hms.**{*;}


# 这是linkedme
-keep class com.microquation.linkedme.android.** { *; }





-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.hihonor.push.**{*;}


-keep class com.tencent.qapmsdk.**{*;}
# 如需要网络监控，请确保okhttp3不被混淆
-keep class okhttp3.**{*;}