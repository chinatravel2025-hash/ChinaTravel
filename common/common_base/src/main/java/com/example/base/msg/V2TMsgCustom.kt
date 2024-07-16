package com.example.base.msg

import com.google.gson.internal.LinkedTreeMap


class V2TMsgCustom constructor(
    var type: Int,
    var data: V2TDiyDataVO? = null,
)

class V2TMsgResultCustom constructor(
    var type: Int,
    var data: LinkedTreeMap<String, Any?>? = null,
)
class V2TMsgCustomData constructor(
    var type: Int? = null,
    var data: String? = null,
)