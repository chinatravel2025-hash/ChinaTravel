
import com.aws.bean.entities.user.OrderDTO
import com.example.base.base.IMInfo
import com.example.base.base.UserInfo
import com.example.http.api.ResponseResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface LoginAPI {

    @POST("/user/mail/fetch_captcha")
    fun fetchCaptcha(@Body params: Map<String,String>): Call<ResponseResult<Any>>
    @PUT("/user/username")
    fun userName(@Body params: Map<String,String>): Call<ResponseResult<Any?>>
    @POST("/user/mail/check_captcha")
    fun checkCaptcha(@Body params: Map<String,String>): Call<ResponseResult<Any>>
    @GET("/user/info")
    fun getUserInfo(): Call<ResponseResult<UserInfo?>>

    @POST("/user/mail/register")
    fun registerByEmail(@Body params: Map<String,String>): Call<ResponseResult<UserInfo?>>

    @POST("/user/mail/reset_pwd")
    fun resetPwdByEmail(@Body params: Map<String,String>): Call<ResponseResult<UserInfo?>>


    @POST("/user/mail/login")
    fun loginByEmail(@Body params: Map<String,String>): Call<ResponseResult<UserInfo?>>

    @GET("/order")
    fun getOrderList(): Call<ResponseResult<OrderDTO?>>

    @POST("/im/sign")
    fun imSigh(): Call<ResponseResult<IMInfo?>>
}