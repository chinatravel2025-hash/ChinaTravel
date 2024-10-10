package com.aws.bean.util

import com.aws.bean.entities.home.ObjectType
import com.aws.bean.entities.home.PlaceType

 object ObjectTypeUtil {

    fun getObjectType(type: ObjectType):String{
      return  when(type){
            ObjectType.SHOP->"shop"
            ObjectType.SIGHT->"sight"
            ObjectType.RESTAURANT->"restaurant"
            ObjectType.TRAVEL_PRODUCTS->"travel-products"
            ObjectType.CITY->"city"
            else->"travel_products"

        }

    }
    fun getPlaceType(type: PlaceType):String{
        return  when(type){
            PlaceType.SHOP->"shop"
            PlaceType.SIGHT->"sight"
            PlaceType.RESTAURANT->"restaurant"
            else->"sight"

        }

    }
}