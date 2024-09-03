
import com.coder.vincent.smart_toast.SmartToast
import com.example.base.base.User
import com.example.base.base.UserInfo

import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.travel.user.api.UserImpApi


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {
    companion object {
        val loginRepository = LoginRepository()
    }



    private val loginAPI = RequestManager.build(UserImpApi().host()).create(LoginAPI::class.java)

    /**
     * 获取邮箱
     */
    fun fetchCaptcha(mail:String,callback:(Boolean)->Unit) {
        loginAPI.fetchCaptcha(hashMapOf(
            "mail" to mail,
            "tag" to "register",
        )).enqueue(object :
            Callback<ResponseResult<Any>> {
            override fun onResponse(
                call: Call<ResponseResult<Any>>,
                response: Response<ResponseResult<Any>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    callback.invoke(true)
                }else{
                    SmartToast.classic().showInCenter(response.body()?.message?:"请重新尝试")
                }
            }
            override fun onFailure(call: Call<ResponseResult<Any>>, t: Throwable) {
                SmartToast.classic().showInCenter(t.message?:"请重新尝试")

            }
        })
    }


    fun checkCaptcha(mail:String,captcha:String, callback:(Boolean)->Unit) {
        loginAPI.checkCaptcha(hashMapOf(
            "mail" to mail,
            "tag" to "register",
            "captcha" to captcha,
        )).enqueue(object :
            Callback<ResponseResult<Any>> {
            override fun onResponse(
                call: Call<ResponseResult<Any>>,
                response: Response<ResponseResult<Any>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    callback.invoke(true)
                }else{
                    SmartToast.classic().showInCenter(response.body()?.message?:"请重新尝试")
                }
            }
            override fun onFailure(call: Call<ResponseResult<Any>>, t: Throwable) {
                SmartToast.classic().showInCenter(t.message?:"请重新尝试")

            }
        })
    }



    fun registerByEmail(mail:String,password:String,confirmPassword:String, callback:(Boolean)->Unit) {
        loginAPI.registerByEmail(hashMapOf(
            "mail" to mail,
            "password" to password,
            "confirm_password" to confirmPassword,
        )).enqueue(object :
            Callback<ResponseResult<UserInfo?>> {
            override fun onResponse(
                call: Call<ResponseResult<UserInfo?>>,
                response: Response<ResponseResult<UserInfo?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    response.body()?.data?.let {
                        User.saveUserInfo(it)
                        callback.invoke(true)
                    }
                }else{
                    SmartToast.classic().showInCenter(response.body()?.message?:"请重新尝试")
                }
            }
            override fun onFailure(call: Call<ResponseResult<UserInfo?>>, t: Throwable) {
                SmartToast.classic().showInCenter(t.message?:"请重新尝试")

            }
        })
    }

    /**
     *  {"mail":"852436078@qq.com","password":"qwer1234"}
     */
    fun loginByEmail(mail:String,password:String ,callback:(Boolean)->Unit) {
        loginAPI.loginByEmail(hashMapOf(
            "mail" to mail,
            "password" to password,
        )).enqueue(object :
            Callback<ResponseResult<UserInfo?>> {
            override fun onResponse(
                call: Call<ResponseResult<UserInfo?>>,
                response: Response<ResponseResult<UserInfo?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    response.body()?.data?.let {
                        User.saveUserInfo(it)
                        callback.invoke(true)
                    }
                }else{
                    SmartToast.classic().showInCenter(response.body()?.message?:"请重新尝试")
                }
            }
            override fun onFailure(call: Call<ResponseResult<UserInfo?>>, t: Throwable) {
                SmartToast.classic().showInCenter(t.message?:"请重新尝试")

            }
        })
    }


}