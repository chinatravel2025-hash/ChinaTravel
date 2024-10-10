package com.travel.home.adapter

import com.aws.bean.entities.home.CityItem
import com.aws.bean.entities.home.PlaceItem
import com.aws.bean.entities.home.TravelProductItem

interface CityListAdapterClickListener {
    fun addCityLike(position:Int, item: CityItem)
    fun cancelCityLike(position:Int,item: CityItem)
}




interface TravelProductClickListener {
    fun addProductLike(position:Int,item: TravelProductItem)
    fun cancelProductLike(position:Int,item: TravelProductItem)
}

interface ThingClickListener {
    fun addThingLike(position:Int,item: PlaceItem)
    fun cancelThingLike(position:Int,item: PlaceItem)
}
interface ShopClickListener {
    fun addShopLike(position:Int,item: TravelProductItem)
    fun cancelShopLike(position:Int,item: TravelProductItem)
}
interface RestaurantClickListener {
    fun addRestaurantLike(position:Int,item: TravelProductItem)
    fun cancelRestaurantLike(position:Int,item: TravelProductItem)
}