
import com.aws.bean.entities.home.BannerDTO
import com.aws.bean.entities.home.CityDTO
import com.aws.bean.entities.home.CityItem
import com.aws.bean.entities.home.PlaceDTO
import com.aws.bean.entities.home.TravelProductDTO
import com.example.base.base.IMInfo
import com.example.base.base.UserInfo
import com.example.http.api.ResponseResult
import org.checkerframework.checker.units.qual.A
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeAPI {

    @GET("/banner")
    fun getHomeBannerList(@Query("page") page: Int, @Query("size") size: Int ): Call<ResponseResult<BannerDTO>>

    @GET("/city-list")
    fun getHomeCityList(@Query("page") page: Int, @Query("size") size: Int ): Call<ResponseResult<CityDTO?>>

    @GET("/city-list/{id}")
    fun getHomeCityDetail(@Path("id") id:Long): Call<ResponseResult<CityItem?>>

    @GET("/travel-products")
    fun getHomeTravelProducts(@Query("page") page: Int, @Query("size") size: Int ): Call<ResponseResult<TravelProductDTO?>>


    @GET("/place/{placeType}")
    fun getHomePlaceType( @Path("placeType") objectType: String,@Query("page") page: Int, @Query("size") size: Int ,@Query("city_id") cityId: Long ): Call<ResponseResult<PlaceDTO?>>

    @GET("/place")
    fun getHomeAllPlaceType(@Query("page") page: Int, @Query("size") size: Int ,@Query("city_id") cityId: Long ): Call<ResponseResult<PlaceDTO?>>


    @POST("/favorite/{objectType}/{id}")
    fun addFavorite(@Path("objectType") objectType: String,@Path("id") id: Long): Call<ResponseResult<Any?>>

    @POST("/favorite/{objectType}/cancel/{id}")
    fun cancelFavorite(@Path("objectType") objectType: String,@Path("id") id: Long): Call<ResponseResult<Any?>>



}