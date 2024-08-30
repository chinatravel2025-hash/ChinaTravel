package com.nguyenhoanglam.imagepicker.helper

import com.nguyenhoanglam.imagepicker.model.Image

interface ImagePickerAction {

    fun takePhoto()

    fun pickImage(pickCount: Int)

}