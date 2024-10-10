import com.aws.bean.entities.home.CityDTO
import com.aws.bean.entities.home.ObjectType
import com.aws.bean.util.ObjectTypeUtil
import com.coder.vincent.smart_toast.SmartToast
import com.example.base.base.IMInfo
import com.example.base.base.User
import com.example.base.base.UserInfo

import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.travel.home.api.HomeImpApi


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {
    companion object {
        val homeRepository = HomeRepository()
    }


    private val homeAPI = RequestManager.build(HomeImpApi().host()).create(HomeAPI::class.java)


    fun addFavorite(objectType: ObjectType,id:Long, callback: (Boolean) -> Unit) {
        homeAPI.addFavorite(ObjectTypeUtil.getObjectType(objectType),id).enqueue(object :
            Callback<ResponseResult<Any?>> {
            override fun onResponse(
                call: Call<ResponseResult<Any?>>,
                response: Response<ResponseResult<Any?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    callback.invoke(true)
                }
            }

            override fun onFailure(call: Call<ResponseResult<Any?>>, t: Throwable) {

            }
        })
    }



    fun cancelFavorite(objectType: ObjectType,id:Long, callback: (Boolean) -> Unit) {
        homeAPI.cancelFavorite(ObjectTypeUtil.getObjectType(objectType),id).enqueue(object :
            Callback<ResponseResult<Any?>> {
            override fun onResponse(
                call: Call<ResponseResult<Any?>>,
                response: Response<ResponseResult<Any?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    if (response.body()?.isSuccessful == true) {
                        callback.invoke(true)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseResult<Any?>>, t: Throwable) {

            }
        })
    }


}