
import com.aws.bean.entities.home.BannerDTO
import com.aws.bean.entities.home.CityDTO
import com.aws.bean.entities.home.CityItem
import com.aws.bean.entities.home.PlaceDTO
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.TravelProductDTO
import com.aws.bean.entities.home.TravelProductItem
import com.example.base.base.IMInfo
import com.example.base.base.UserInfo
import com.example.http.api.ResponseResult
import com.google.gson.internal.LinkedTreeMap
import org.checkerframework.checker.units.qual.A
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeAPI {
    @GET("/config")
    fun getAppConfig(): Call<ResponseResult<LinkedTreeMap<String, String>?>>

    @GET("/banner")
    fun getHomeBannerList(@Query("page") page: Int, @Query("size") size: Int ): Call<ResponseResult<BannerDTO>>

    @GET("/city-list")
    fun getHomeCityList(@Query("page") page: Int, @Query("size") size: Int ): Call<ResponseResult<CityDTO?>>

    @GET("/favorite/city")
    fun getFavoriteCityList(@Query("page") page: Int, @Query("size") size: Int ): Call<ResponseResult<CityDTO?>>


    @GET("/city-list/{id}")
    fun getHomeCityDetail(@Path("id") id:Long): Call<ResponseResult<CityItem?>>

    @GET("/travel-products")
    fun getHomeTravelProducts(@Query("page") page: Int, @Query("size") size: Int, @Query("id") id: Long? = null ): Call<ResponseResult<TravelProductDTO?>>

    @GET("/favorite/travel-products")
    fun getFavoriteTravelProducts(@Query("page") page: Int, @Query("size") size: Int ): Call<ResponseResult<TravelProductDTO?>>

    @GET("/travel-products/{id}")
    fun getTravelProductDetails(@Path("id") id:Long): Call<ResponseResult<TravelProductItem?>>

    @GET("/travel-trip/{id}")
    fun getTravelTripDetails(@Path("id") id:Long): Call<ResponseResult<TravelProductItem?>>


    @GET("/place/{placeType}")
    fun getHomePlaceType( @Path("placeType") objectType: String,@Query("page") page: Int, @Query("size") size: Int ,@Query("city_id") cityId: Long ): Call<ResponseResult<PlaceDTO?>>

    @GET("/place")
    fun getHomeAllPlaceType(@Query("page") page: Int, @Query("size") size: Int ,@Query("city_id") cityId: Long ): Call<ResponseResult<PlaceDTO?>>
    @GET("/favorite/place")
    fun getFavoritePlace(@Query("page") page: Int, @Query("size") size: Int ): Call<ResponseResult<PlaceDTO?>>

    @GET("/place/{placeType}/{id}")
    fun getPlaceDetails( @Path("placeType") objectType: String,@Path("id") id:Long): Call<ResponseResult<PlaceItem?>>

    @POST("/favorite/{objectType}/{id}")
    fun addFavorite(@Path("objectType") objectType: String,@Path("id") id: Long): Call<ResponseResult<Any?>>

    @POST("/favorite/{objectType}/cancel/{id}")
    fun cancelFavorite(@Path("objectType") objectType: String,@Path("id") id: Long): Call<ResponseResult<Any?>>

    @DELETE("/user")
    fun closeUser(): Call<ResponseResult<Any?>>



}