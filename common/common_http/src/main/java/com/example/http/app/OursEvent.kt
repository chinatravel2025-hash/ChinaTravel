package com.example.http.app


class LoginImOutEvent

class LoginImLoginEvent
class LoginImStateEvent(val state:Int){
    companion object{
        const val success = 0
        const val fail = 1
    }
}

class LogoutEvent

class SpotEvent(val showHide:Boolean)
class OURSPhoneCancelEvent
class OURSPhoneCancelDialogEvent
