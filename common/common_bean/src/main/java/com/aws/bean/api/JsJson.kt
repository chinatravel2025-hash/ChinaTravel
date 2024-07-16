package com.aws.bean.api


class JsJson(val action: String?,
             val url: String?){
    companion object {
        const val ACTION_CODE_TASK = "task"
        const val ACTION_CODE_JUMP = "jump"
    }
}

