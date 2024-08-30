/*
 * Copyright (C) 2021 Image Picker
 * Author: Nguyen Hoang Lam <hoanglamvn90@gmail.com>
 */

package com.nguyenhoanglam.imagepicker.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    var uri: Uri,
    var name: String,
    val mimeType: String,
    val width: Int,
    val height: Int,
    val size: Long,
    val orientation: Int,
    val path: String,
    var bucketId: Long = 0,
    var bucketName: String = ""
) : Parcelable