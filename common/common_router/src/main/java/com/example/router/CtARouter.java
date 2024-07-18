package com.example.router;

import android.app.Application;

import com.alibaba.android.arouter.exception.InitException;
import com.alibaba.android.arouter.launcher.ARouter;

public class CtARouter {
    public static ARouter getInstance(){
        ARouter aRouter = null;
        try{
            aRouter = ARouter.getInstance();
        }catch (InitException e){
         //   init();
        }
        if(aRouter == null){
            aRouter = ARouter.getInstance();
        }
        return aRouter;
    }

    /**
     * 初始化ARout
     */
    public static boolean init(Application application ) {
        if (application != null) {
            if (BuildConfig.DEBUG) {   // 这两行必须写在init之前，否则这些配置在init过程中将无效
                ARouter.openLog();     // 打印日志
                ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            }
            ARouter.init(application);
            return true;
        }
        return false;
    }
}
