package com.example.base.weiget.permission;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.peanutmusic.base.R;

public class PermissionView extends FrameLayout {

    public PermissionView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.dialog_permission_content, this);
        LinearLayout flContainer = (LinearLayout) findViewById(R.id.fl_container);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvContent = (TextView) findViewById(R.id.tv_content);
    }


}
