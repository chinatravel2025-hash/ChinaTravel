package com.aws.bean.entities.user

data class MailLoginDTO(
   var mail:String,
   var token:String,
   var uid:String,
)
