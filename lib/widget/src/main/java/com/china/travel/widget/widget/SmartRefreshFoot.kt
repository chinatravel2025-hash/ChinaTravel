package com.china.travel.widget.widget

import android.content.Context
import android.util.AttributeSet
import com.scwang.smartrefresh.layout.footer.ClassicsFooter

class SmartRefreshFoot : ClassicsFooter {
    constructor(context: Context) : super(context)  {

        mTextFinish = "Completed"
        mTextFailed="Load Fail"
        mTextLoading="Loading"
        mTextNothing="Nothing"




    }


    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

}