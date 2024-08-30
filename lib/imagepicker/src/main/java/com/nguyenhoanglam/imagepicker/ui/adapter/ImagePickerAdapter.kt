/*
 * Copyright (C) 2021 Image Picker
 * Author: Nguyen Hoang Lam <hoanglamvn90@gmail.com>
 */

package com.nguyenhoanglam.imagepicker.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.nguyenhoanglam.imagepicker.R
import com.nguyenhoanglam.imagepicker.helper.ImageHelper
import com.nguyenhoanglam.imagepicker.helper.ImageLoader
import com.nguyenhoanglam.imagepicker.listener.OnImageSelectListener
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig

class ImagePickerAdapter(
    context: Context,
    private val config: ImagePickerConfig,
    private val imageSelectListener: OnImageSelectListener
) : BaseRecyclerViewAdapter<ImagePickerAdapter.ImageViewHolder?>(context) {

    private val selectedImages = arrayListOf<Image>()
    private val images: ArrayList<Image> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = inflater.inflate(R.layout.imagepicker_item_image, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ImageViewHolder, position: Int) {
        val image = images[position]
        val selectedIndex = ImageHelper.findImageIndex(image, selectedImages)
        val isSelected = config.isMultipleMode && selectedIndex != -1

        ImageLoader.loadImage(viewHolder.image, image.uri)

        viewHolder.gifIndicator.visibility =
            if (ImageHelper.isGifFormat(image)) View.VISIBLE else View.GONE
        viewHolder.selectedIcon.visibility = if(!config.isShowNumberIndicator || (config.isShowNumberIndicator && !isSelected)) View.VISIBLE else View.GONE
        viewHolder.selectedIcon.setImageResource(if(isSelected && !config.isShowNumberIndicator) R.mipmap.imagepicker_checked else R.mipmap.imagepicker_unchecked)
        viewHolder.selectedNumber.visibility =
            if (isSelected && config.isShowNumberIndicator) View.VISIBLE else View.GONE
        if (viewHolder.selectedNumber.visibility == View.VISIBLE) {
            viewHolder.selectedNumber.text = (selectedIndex + 1).toString()
        }
        viewHolder.overMask.visibility = if(selectedImages.size == config.maxSize && !isSelected)
            View.VISIBLE else View.GONE
        viewHolder.itemView.setOnClickListener {
            selectOrRemoveImage(image, position)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    private fun selectOrRemoveImage(image: Image, position: Int) {
        if (config.isMultipleMode) {
            val selectedIndex = ImageHelper.findImageIndex(image, selectedImages)
            if (selectedIndex != -1) {
                val notifyDataSetChanged = selectedImages.size == config.maxSize
                selectedImages.removeAt(selectedIndex)
                notifyItemChanged(position)
                val indexes = ImageHelper.findImageIndexes(selectedImages, images)
                for (index in indexes) {
                    notifyItemChanged(index)
                }
                if(notifyDataSetChanged) {
                    notifyDataSetChanged()
                }
            } else {
                if (selectedImages.size >= config.maxSize) {
                    val message =
                        if (config.limitMessage != null) config.limitMessage!! else String.format(
                            context.resources.getString(R.string.imagepicker_msg_limit_images),
                            config.maxSize
                        )
                    Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT).show()
                    return
                } else {
                    val maxFileSizeInMB = 10 * 1024 * 1024
                    if(image.size > maxFileSizeInMB) {
                        val limitMessage = context.resources.getString(R.string.imagepicker_msg_limit_file_size)
                        Toast.makeText(context.applicationContext, limitMessage, Toast.LENGTH_SHORT).show()
                    } else {
                        selectedImages.add(image)
                        notifyItemChanged(position)
                        if (selectedImages.size == config.maxSize) {
                            notifyDataSetChanged()
                        }
                    }
                }
            }
            imageSelectListener.onSelectedImagesChanged(selectedImages)
        } else {
            imageSelectListener.onSingleModeImageSelected(image)
        }
    }

    fun setData(images: List<Image>) {
        this.images.clear()
        this.images.addAll(images)
        notifyDataSetChanged()
    }

    fun setSelectedImages(selectedImages: ArrayList<Image>) {
        this.selectedImages.clear()
        this.selectedImages.addAll(selectedImages)
        notifyDataSetChanged()

    }

    class ImageViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_thumbnail)
        val selectedIcon: ImageView = itemView.findViewById(R.id.image_selected_icon)
        val selectedNumber: TextView = itemView.findViewById(R.id.text_selected_number)
        val gifIndicator: View = itemView.findViewById(R.id.gif_indicator)
        val overMask: View = itemView.findViewById(R.id.over_mask)
    }

}