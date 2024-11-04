import com.aws.bean.entities.home.ObjectType
import com.aws.bean.util.ObjectTypeUtil
import com.example.base.localstore.MMKVSpUtils
import com.example.base.utils.AppConfig
import com.example.base.utils.AppConfig.APP_PIC
import com.example.http.RequestManager
import com.example.http.api.ResponseResult
import com.google.gson.internal.LinkedTreeMap
import com.travel.home.api.HomeImpApi


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {
    companion object {
        val homeRepository = HomeRepository()
    }


    private val homeAPI = RequestManager.build(HomeImpApi().host()).create(HomeAPI::class.java)

    fun getAppConfig() {
        homeAPI.getAppConfig().enqueue(object :
            Callback<ResponseResult<LinkedTreeMap<String, String>?>> {
            override fun onResponse(
                call: Call<ResponseResult<LinkedTreeMap<String, String>?>>,
                response: Response<ResponseResult<LinkedTreeMap<String, String>?>>
            ) {
                if (response.body()?.isSuccessful == true) {
                    val map = response.body()?.data
                    val picUrl = map?.get("pic_base_url")
                    MMKVSpUtils.putString(APP_PIC, picUrl)
                    AppConfig.appPicUrl()
                }
            }

            override fun onFailure(call: Call<ResponseResult<LinkedTreeMap<String, String>?>>, t: Throwable) {

            }
        })
    }

    fun addFavorite(objectType: ObjectType, id: Long, callback: (Boolean) -> Unit) {
        homeAPI.addFavorite(ObjectTypeUtil.getObjectType(objectType), id).enqueue(object :
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


    fun cancelFavorite(objectType: ObjectType, id: Long, callback: (Boolean) -> Unit) {
        homeAPI.cancelFavorite(ObjectTypeUtil.getObjectType(objectType), id).enqueue(object :
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