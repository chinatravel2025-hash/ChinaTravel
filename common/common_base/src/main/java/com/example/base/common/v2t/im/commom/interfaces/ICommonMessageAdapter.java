package com.example.base.common.v2t.im.commom.interfaces;


import com.example.base.common.v2t.im.commom.bean.TUIMessageBean;

public interface ICommonMessageAdapter {
    TUIMessageBean getItem(int position);

    void onItemRefresh(TUIMessageBean messageBean);
}
