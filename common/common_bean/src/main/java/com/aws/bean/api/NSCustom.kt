package com.aws.bean.api

import org.json.JSONObject
import java.io.Serializable
/**
 * @author li yi
 * 埋点公共model
 */
class NSCustom(var page_id: String?, var element_position: String?, var element_content: String?) : Serializable {

    constructor(page_id: String?, element_position: String?, element_content: String?, extra_event_map: JSONObject)
            : this(page_id, element_position, element_content) {
        this.extra_event_map = extra_event_map
    }

    private var extra_event_map  = JSONObject()

    fun toJSONObject(): JSONObject {
        //return JSONObject(GsonUtil.toJson(this))
        return JSONObject()
    }
}