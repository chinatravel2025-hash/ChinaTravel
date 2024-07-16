package com.aws.bean.api

import org.json.JSONObject

/**
 * @author li yi
 * 返回给H5的对象
 */
class JSResult(var code: String?, var actoin: String?, var data: String?) {

    fun toJSONObject(): JSONObject {
        //return JSONObject(GsonUtil.toJson(this))
        return JSONObject()
    }
}