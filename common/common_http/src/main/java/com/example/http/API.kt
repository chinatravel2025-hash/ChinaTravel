package com.example.http



open class API {

    companion object {




       var env = "dev"
      // var env = "fat"
      //  var env = "production"

    }


    private fun dev(): String {
        return "http://117.50.180.237:8810"
    }


    private fun fat(): String {
        return "http://117.50.180.237:8810"

    }

    private fun release(): String {
        return "http://117.50.180.237:8810"
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