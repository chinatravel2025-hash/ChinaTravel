package com.example.base.event

data class FavouriteEvent(
    var id:Long,
    var position:Int,
    var favourites:Int,
)
data class CommunityClickRefreshEvent(
   var position: Int
)

 class FindTabRefreshEvent()