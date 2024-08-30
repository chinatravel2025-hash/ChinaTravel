
import androidx.lifecycle.MutableLiveData
import com.coder.vincent.smart_toast.SmartToast

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
    fun fetchCaptcha(callback:(Boolean)->Unit) {
        loginAPI.fetchCaptcha(hashMapOf(
            "mail" to "852436078@qq.com",
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



    fun registerByEmail() {
        loginAPI.registerByEmail(hashMapOf(
            "mail" to "2343454",
            "password" to "wo1111111w",
            "confirm_password" to "wo1111111w",
        )).enqueue(object :
            Callback<ResponseResult<Any>> {
            override fun onResponse(
                call: Call<ResponseResult<Any>>,
                response: Response<ResponseResult<Any>>
            ) {
                if (response.body()?.isSuccessful == true) {
                }else{
                    SmartToast.classic().showInCenter(response.body()?.message?:"请重新尝试")
                }
            }
            override fun onFailure(call: Call<ResponseResult<Any>>, t: Throwable) {
                SmartToast.classic().showInCenter(t.message?:"请重新尝试")

            }
        })
    }




}