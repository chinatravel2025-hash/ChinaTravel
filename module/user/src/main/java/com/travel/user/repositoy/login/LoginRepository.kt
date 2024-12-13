import com.coder.vincent.smart_toast.SmartToast
import com.example.base.base.IMInfo
import com.example.base.base.User
import com.example.base.base.UserInfo
import com.example.base.utils.AppCodeUtils
import com.example.base.utils.MD5Util

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
    fun fetchCaptcha(mail: String,style: String, callback: (Boolean) -> Unit) {
        loginAPI.fetchCaptcha(
            hashMapOf(
                "mail" to mail,
                "tag" to style,
            )
        ).enqueue(object :
            Callback<ResponseResult<Any>> {
            override fun onResponse(
                call: Call<ResponseResult<Any>>,
                response: Response<ResponseResult<Any>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    callback.invoke(true)
                    //该邮箱已注册，请前往登录
                } else if (response.body()?.code=="${AppCodeUtils.EMAIL_HAS_CREATE_ERROR}"){
                    SmartToast.classic().showInCenter("The email address is registered")
                    callback.invoke(false)
                } else {
                    SmartToast.classic().showInCenter(response.body()?.message ?: "Please try again")
                }
            }

            override fun onFailure(call: Call<ResponseResult<Any>>, t: Throwable) {
                SmartToast.classic().showInCenter(t.message ?: "Please try again")

            }
        })
    }


    fun checkCaptcha(mail: String, captcha: String, style: String, callback: (Boolean) -> Unit) {
        loginAPI.checkCaptcha(
            hashMapOf(
                "mail" to mail,
                "tag" to  style,
                "captcha" to captcha,
            )
        ).enqueue(object :
            Callback<ResponseResult<Any>> {
            override fun onResponse(
                call: Call<ResponseResult<Any>>,
                response: Response<ResponseResult<Any>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    callback.invoke(true)
                } else {
                    callback.invoke(false)
                    SmartToast.classic().showInCenter(response.body()?.message ?: "Please try again")
                }
            }

            override fun onFailure(call: Call<ResponseResult<Any>>, t: Throwable) {
                callback.invoke(false)
                SmartToast.classic().showInCenter(t.message ?: "Please try again")

            }
        })
    }


    fun registerByEmail(
        mail: String,
        password: String,
        confirmPassword: String,
        callback: (Boolean) -> Unit
    ) {
        val md5 = MD5Util.md5(password)?:""
        val cmd5 = MD5Util.md5(confirmPassword)?:""

        loginAPI.registerByEmail(
            hashMapOf(
                "mail" to mail,
                "password" to md5,
                "confirm_password" to cmd5,
            )
        ).enqueue(object :
            Callback<ResponseResult<UserInfo?>> {
            override fun onResponse(
                call: Call<ResponseResult<UserInfo?>>,
                response: Response<ResponseResult<UserInfo?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    response.body()?.data?.let {
                        imSigh(it, callback)
                    }
                } else {
                    SmartToast.classic().showInCenter(response.body()?.message ?: "Please try again")
                }
            }

            override fun onFailure(call: Call<ResponseResult<UserInfo?>>, t: Throwable) {
                SmartToast.classic().showInCenter(t.message ?: "Please try again")

            }
        })
    }

    /**
     * 重置密码
     */
    fun resetPwdByEmail(
        mail: String,
        password: String,
        confirmPassword: String,
        callback: (Boolean) -> Unit
    ) {
        val md5 = MD5Util.md5(password)?:""
        val cmd5 = MD5Util.md5(confirmPassword)?:""
        loginAPI.resetPwdByEmail(
            hashMapOf(
                "mail" to mail,
                "password" to md5,
                "confirm_password" to cmd5,
            )
        ).enqueue(object :
            Callback<ResponseResult<UserInfo?>> {
            override fun onResponse(
                call: Call<ResponseResult<UserInfo?>>,
                response: Response<ResponseResult<UserInfo?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    callback.invoke(true)
                } else {
                    SmartToast.classic().showInCenter(response.body()?.message ?: "Please try again")
                }
            }

            override fun onFailure(call: Call<ResponseResult<UserInfo?>>, t: Throwable) {
                SmartToast.classic().showInCenter(t.message ?: "Please try again")

            }
        })
    }


    /**
     *  {"mail":"852436078@qq.com","password":"qwer1234"}
     */
    fun loginByEmail(mail: String, password: String, callback: (Boolean) -> Unit) {
        val md5 = MD5Util.md5(password)?:""
        loginAPI.loginByEmail(
            hashMapOf(
                "mail" to mail,
                "password" to md5,
            )
        ).enqueue(object :
            Callback<ResponseResult<UserInfo?>> {
            override fun onResponse(
                call: Call<ResponseResult<UserInfo?>>,
                response: Response<ResponseResult<UserInfo?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    response.body()?.data?.let {
                        imSigh(it, callback)
                    }
                } else {
                    SmartToast.classic().showInCenter(response.body()?.message ?: "Please try again")
                }
            }

            override fun onFailure(call: Call<ResponseResult<UserInfo?>>, t: Throwable) {
                SmartToast.classic().showInCenter(t.message ?: "Please try again")

            }
        })
    }

    fun imSigh(user: UserInfo?, callback: (Boolean) -> Unit) {
        User.tpToken = user?.token?:""
        loginAPI.imSigh().enqueue(object :
            Callback<ResponseResult<IMInfo?>> {
            override fun onResponse(
                call: Call<ResponseResult<IMInfo?>>,
                response: Response<ResponseResult<IMInfo?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    response.body()?.data?.let {
                        user?.let { user ->
                            user.imToken = it.sign
                            User.saveUserInfo(user)
                            callback.invoke(true)
                        }
                    }
                } else {
                    SmartToast.classic().showInCenter(response.body()?.message ?: "Please try again")
                }
            }

            override fun onFailure(call: Call<ResponseResult<IMInfo?>>, t: Throwable) {
                SmartToast.classic().showInCenter(t.message ?: "Please try again")

            }
        })
    }

}