package com.example.base.weiget.permission;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.peanutmusic.base.R;


public class PermissionDialogFragment extends BaseDialogFragment {
    private View view;
    private static PermissionDialogFragment dialogFragment;
    private final static String Key = "PermissionDialogFragment";

    public static PermissionDialogFragment init() {
        if (dialogFragment == null) {
            dialogFragment = new PermissionDialogFragment();
        }
        return dialogFragment;
    }
    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        view =inflater.inflate(R.layout.dialog_permission_content, container,false);
        LinearLayout flContainer = view.findViewById(R.id.fl_container);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvContent = view.findViewById(R.id.tv_content);
        return view;
    }


}
