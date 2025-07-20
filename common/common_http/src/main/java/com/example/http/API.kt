package com.example.http



open class API {

    companion object {
       var env = "dev"
      // var env = "fat"
      //  var env = "production"

    }


    private fun dev(): String {
        return "https://app-api.chunhuo.net"
    }


    private fun fat(): String {
        return "https://app-api.chunhuo.net"

    }

    private fun release(): String {
        return "https://app-api.chunhuo.net"
    }

    open fun host(): String {
        return when (env) {
            "dev" -> dev()
            "fat" -> fat()
            "production" -> release()
            else -> fat()
        }
    }


}