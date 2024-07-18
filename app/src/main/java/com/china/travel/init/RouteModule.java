package com.china.travel.init;

import android.app.Application;

import com.example.router.CtARouter;

public class RouteModule {
    public static void init(Application application) {

        CtARouter.init(application);
      //  ARouter.init(application);
    }
}
