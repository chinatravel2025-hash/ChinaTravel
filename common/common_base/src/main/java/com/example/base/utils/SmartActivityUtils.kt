package com.example.base.utils

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils

class SmartActivityUtils {

    companion object {

        fun app(): Application {
            return ActivityUtils.getTopActivity().application
        }

        fun getTopActivity(): Activity {
            return ActivityUtils.getTopActivity()
        }

        fun finishActivity(activityClazz: Class<out Activity>){
            ActivityUtils.finishActivity(activityClazz)
        }

        fun finishAllActivities() {
            ActivityUtils.finishAllActivities()
        }




    }

}