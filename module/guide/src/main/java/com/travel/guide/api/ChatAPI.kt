
import com.example.http.api.ResponseResult
import com.travel.guide.api.IMChatInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatAPI {

    @GET("/customer/list")
    fun customerList(): Call<ResponseResult<IMChatInfo?>>
}