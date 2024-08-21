package com.example.base.common.v2t.im.core.interfaces;

import android.os.Bundle;

public abstract class TUIServiceCallback {
    public abstract void onServiceCallback(int errorCode, String errorMessage, Bundle bundle);
}
