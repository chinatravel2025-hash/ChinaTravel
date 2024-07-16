package com.aws.bean.msg


/**
 *
 * type: 决定什么扩展消息类型
 * data: 具体数据.
 *
 */

class V2TMsgExtCloud constructor(
    var ours_quote_id: String? = null,//引用消息ID
    var ours_at_id: String? = null,//艾特消息ID
    var ours_voice_ext: String? = null,//型随意动
    var ours_voice_text: String? = null,//文字
    var link: String? = null,//链接
    var style: String? = null,//类型
)


//发送消息扩展
class V2SendTmsgExt constructor(
    var type: Int = 0,
    var data: Any? = null,
)

class V2TMsgExtLocal constructor(
    var msgId: String? = null,//本地ID
    var ours_upload_progress: Int? = null,//上传进度
    var ours_download_progress: Int? = null,//下载进度
    var ours_unread: Boolean? = null,//未读
){
    var ours_file_time: Long? = null
    var ours_file_path: String? = null
    var ours_width: Int? = null
    var ours_height: Int? = null
}
