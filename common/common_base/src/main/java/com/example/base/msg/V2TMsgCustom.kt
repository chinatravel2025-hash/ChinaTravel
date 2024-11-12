package com.example.base.msg



class V2TMsgCustom constructor(
    var type: Int,
    var data: V2TDiyDataVO? = null,
    var tripsId: String? = null,
    var image: ArrayList<Any>? = null,
    var title: String? = null,
    var about: String? = null,
)
