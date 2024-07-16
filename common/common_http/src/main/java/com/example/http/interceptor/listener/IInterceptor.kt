package com.example.http.interceptor.listener

import java.lang.Exception

/**
 * 希望是http 通讯的拦截器接口，当前希望是能给所有接口做某个特殊拦截
 * 注意调用线程... 注意调用线程.. 需要自己在某些地方做线程切换.
 */
interface IInterceptor {


    /**
     * 慎重设置true A -> B -> C -> D ，如果传递到C 返回true D 不会执行了, A 返回true BCD 不会执行了
     * true : 表示直接拦截后，不再向外传递事件
     * false: 拦截代码和后续代码会都会执行
     */
    fun onIntercept(res:InterceptResponse?):Boolean


}

data class InterceptResponse(val code:Int,val methodName:String, val data: Any)