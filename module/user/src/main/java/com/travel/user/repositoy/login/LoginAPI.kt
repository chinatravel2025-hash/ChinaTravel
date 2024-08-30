
import com.aws.bean.entities.user.MailLoginDTO
import com.example.http.api.ResponseResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {

    @POST("/user/mail/fetch_captcha")
    fun fetchCaptcha(@Body params: Map<String,String>): Call<ResponseResult<Any>>


    @POST("/user/mail/check_captcha")
    fun checkCaptcha(@Body params: Map<String,String>): Call<ResponseResult<Any>>

    @POST("/user/mail/register")
    fun registerByEmail(@Body params: Map<String,String>): Call<ResponseResult<MailLoginDTO?>>


}