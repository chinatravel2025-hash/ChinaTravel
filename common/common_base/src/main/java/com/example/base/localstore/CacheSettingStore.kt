package com.example.base.localstore


object CacheSettingStore {


    private const val AUTO_DOWNLOAD_SETTINGS_KEY  = "AUTO_DOWNLOAD_SETTINGS_KEY"

    private const val AUTO_PALY_VIDEO_SETTINGS_KEY = "AUTO_PALY_VIDEO_SETTINGS_KEY"


    fun saveAutoDownloadSettingsType(userId: String,networkType: Int) {

            MMKVSpUtils.putInt(
                "${AUTO_DOWNLOAD_SETTINGS_KEY}_${userId}",
                networkType,
            )
    }


    //没有默认选中
    fun getAutoDownloadSettingsType(userId: String): Int {
        return MMKVSpUtils.getInt(
            "${AUTO_DOWNLOAD_SETTINGS_KEY}_${userId}",
            -1);
    }


    fun saveAutoPlayVideoType(userId: String,networkType: Int) {

        MMKVSpUtils.putInt(
            "${AUTO_PALY_VIDEO_SETTINGS_KEY}_${userId}",
            networkType,
        )
    }


    fun getAutoPlayVideoType(userId: String): Int {
        return MMKVSpUtils.getInt(  "${AUTO_PALY_VIDEO_SETTINGS_KEY}_${userId}" ,
            0);
    }


}