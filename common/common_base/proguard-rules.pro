-keep class com.umeng.**{*;}

-keepclassmembers class*{
public <init> (org.json.JSONObject);
}

-keepclassmembers enum*{
public static **[] values();
public static ** valueOf(java.lang.String);
}
-keep class android.support.v8.renderscript.** { *; }
-keep class androidx.renderscript.** { *; }
-keep class com.tencent.qcloud.** { *; }
-keep class com.tencent.timpush.** { *; }