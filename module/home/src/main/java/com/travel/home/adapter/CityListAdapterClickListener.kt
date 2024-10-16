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



