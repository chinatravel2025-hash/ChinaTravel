package com.example.base.common.v2t.im.commom;

import android.content.Context;

import com.example.base.common.v2t.im.core.interfaces.TUIInitializer;
import com.example.base.base.App;


public class TIMCommonService implements TUIInitializer {

    @Override
    public void init(Context context) {

    }

    public static Context getAppContext() {
        return App.getContext();
    }
}
