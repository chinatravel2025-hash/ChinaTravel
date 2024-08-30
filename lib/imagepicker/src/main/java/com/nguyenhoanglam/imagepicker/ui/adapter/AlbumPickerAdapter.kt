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
import androidx.recyclerview.widget.RecyclerView
import com.nguyenhoanglam.imagepicker.R
import com.nguyenhoanglam.imagepicker.helper.ImageHelper
import com.nguyenhoanglam.imagepicker.helper.ImageLoader
import com.nguyenhoanglam.imagepicker.listener.OnFolderClickListener
import com.nguyenhoanglam.imagepicker.model.Folder

class AlbumPickerAdapter(context: Context, private val itemClickListener: OnFolderClickListener) :
    BaseRecyclerViewAdapter<AlbumPickerAdapter.AlbumViewHolder?>(context) {

    private val folders: MutableList<Folder> = mutableListOf()
    private var selectedId = ImageHelper.ALL_BUCKET_ID

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView = inflater.inflate(R.layout.imagepicker_item_album, parent, false)
        return AlbumViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val folder = folders[position]
        val count = folder.images.size
        val previewImage = folder.images[0]

        ImageLoader.loadImage(holder.image, previewImage.uri)
        holder.name.text = folder.name
        holder.count.text = count.toString()
        holder.checked.visibility = if(selectedId == folder.bucketId) View.VISIBLE else View.GONE
        holder.itemView.setOnClickListener {
            itemClickListener.onFolderClick(folder)
        }
    }

    fun setData(folders: List<Folder>) {
        this.folders.clear()
        this.folders.addAll(folders)
        notifyDataSetChanged()
    }

    fun selectedAlbumId(selectedId: Long) {
        this.selectedId = selectedId
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_folder_thumbnail)
        val name: TextView = itemView.findViewById(R.id.text_folder_name)
        val count: TextView = itemView.findViewById(R.id.text_photo_count)
        val checked: View = itemView.findViewById(R.id.image_folder_checked)
    }

}