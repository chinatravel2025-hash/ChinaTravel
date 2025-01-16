package com.travel.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.maps2d.MapView

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.devs.readmoreoption.ReadMoreOption
import com.example.base.base.bean.BlocksBean
import com.example.base.utils.ResourceUtils
import com.travel.home.R
import com.travel.home.databinding.ItemPlaceBlockAboutBinding
import com.travel.home.databinding.ItemPlaceBlockHeadBinding
import com.travel.home.databinding.ItemPlaceBlockImageBinding
import com.travel.home.databinding.ItemPlaceBlockLocationBinding
import com.travel.home.databinding.ItemPlaceBlockParagraphBinding
import com.travel.home.databinding.ItemPlaceBlockTripContentBinding
import com.travel.home.databinding.ItemPlaceBlockTripHeadBinding

class PlaceBlockAdapter() :
    BaseMultiItemQuickAdapter<BlocksBean, BaseViewHolder>() {

        var mapView: MapView?=null

    init {
        addItemType(ITEM_TYPE_HEAD, R.layout.item_place_block_head)
        addItemType(ITEM_TYPE_PARAGRAPH, R.layout.item_place_block_paragraph)
        addItemType(ITEM_TYPE_IMAGE, R.layout.item_place_block_image)
        addItemType(ITEM_TYPE_TRIP_HEAD, R.layout.item_place_block_trip_head)
        addItemType(ITEM_TYPE_TRIP_CONTENT, R.layout.item_place_block_trip_content)
        addItemType(ITEM_TYPE_INTRO, R.layout.item_place_block_about)
        addItemType(ITEM_TYPE_LOCATION, R.layout.item_place_block_location)
    }
    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            ITEM_TYPE_HEAD -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_place_block_head, parent, false)
                return BlockHeadHolder(view)
            }
            ITEM_TYPE_PARAGRAPH -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_place_block_paragraph, parent, false)
                return BlockParagraphHolder(view)
            }
            ITEM_TYPE_IMAGE -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_place_block_image, parent, false)
                return BlockImageHolder(view)
            }
            ITEM_TYPE_TRIP_HEAD -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_place_block_trip_head, parent, false)
                return BlockTripHeadHolder(view)
            }
            ITEM_TYPE_TRIP_CONTENT -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_place_block_trip_content, parent, false)
                return BlockTripContentHolder(view)
            }
            ITEM_TYPE_INTRO -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_place_block_about, parent, false)
                return BlockAboutHolder(view)
            }
            ITEM_TYPE_LOCATION -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_place_block_location, parent, false)
                return BlockLocationHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_place_block_head, parent, false)
                return BlockHeadHolder(view)
            }

        }

    }

    override fun convert(
        holder: BaseViewHolder,
        item: BlocksBean
    ) {
        when (item.viewType()) {
            ITEM_TYPE_HEAD -> {
                val modelHolder = holder as BlockHeadHolder
                modelHolder.dataBinding?.apply {
                    vm = item
                    executePendingBindings()
                }
            }
            ITEM_TYPE_PARAGRAPH -> {
                val addHolder = holder as BlockParagraphHolder
                addHolder.dataBinding?.apply {
                    vm = item
                    val readMoreOption = ReadMoreOption.Builder(context)
                        .textLength(200, ReadMoreOption.TYPE_CHARACTER)
                        .moreLabel("Read more")
                        .lessLabel("  Read less")
                        .moreLabelColor(ResourceUtils.getColor(com.example.peanutmusic.base.R.color.txt_12C286))
                        .lessLabelColor(ResourceUtils.getColor(com.example.peanutmusic.base.R.color.txt_12C286))
                        .expandAnimation(true)
                        .build()
                    readMoreOption.addReadMoreTo(tvContent, item.data?.text)
                    executePendingBindings()
                }
            }
            ITEM_TYPE_IMAGE -> {
                val addHolder = holder as BlockImageHolder
                addHolder.dataBinding?.apply {
                    vm = item
                    executePendingBindings()
                }
            }
            ITEM_TYPE_TRIP_HEAD -> {
                val modelHolder = holder as BlockTripHeadHolder
                modelHolder.dataBinding?.apply {
                    vm = item
                    executePendingBindings()
                }
            }

            ITEM_TYPE_TRIP_CONTENT -> {
                val modelHolder = holder as BlockTripContentHolder
                modelHolder.dataBinding?.apply {
                    vm = item
                    val readMoreOption = ReadMoreOption.Builder(context)
                        .textLength(200, ReadMoreOption.TYPE_CHARACTER)
                        .moreLabel("Read more")
                        .lessLabel("  Read less")
                        .moreLabelColor(ResourceUtils.getColor(com.example.peanutmusic.base.R.color.txt_12C286))
                        .lessLabelColor(ResourceUtils.getColor(com.example.peanutmusic.base.R.color.txt_12C286))
                        .expandAnimation(true)
                        .build()
                    readMoreOption.addReadMoreTo(tvContent, item.data?.text)
                    executePendingBindings()
                }
            }
            ITEM_TYPE_INTRO -> {
                val modelHolder = holder as BlockAboutHolder
                modelHolder.dataBinding?.apply {
                    vm = item
                    val readMoreOption = ReadMoreOption.Builder(context)
                        .textLength(200, ReadMoreOption.TYPE_CHARACTER)
                        .moreLabel("Read more")
                        .lessLabel("  Read less")
                        .moreLabelColor(ResourceUtils.getColor(com.example.peanutmusic.base.R.color.txt_12C286))
                        .lessLabelColor(ResourceUtils.getColor(com.example.peanutmusic.base.R.color.txt_12C286))
                        .expandAnimation(true)
                        .build()
                    readMoreOption.addReadMoreTo(tvContent, item.data?.text)
                    executePendingBindings()
                }
            }
            ITEM_TYPE_LOCATION -> {
                val modelHolder = holder as BlockLocationHolder
                modelHolder.dataBinding?.apply {
                    vm = item
                    mapView = space
                    executePendingBindings()
                }
            }
        }
    }
    companion object {
        const val ITEM_TYPE_HEAD = 0
        const val ITEM_TYPE_PARAGRAPH = 1
        const val ITEM_TYPE_IMAGE = 2
        const val ITEM_TYPE_TRIP_HEAD = 3
        const val ITEM_TYPE_TRIP_CONTENT = 4
        const val ITEM_TYPE_INTRO = 5
        const val ITEM_TYPE_LOCATION = 6
    }
}

//带横线的标题
class BlockHeadHolder(view: View) :
    BaseDataBindingHolder<ItemPlaceBlockHeadBinding>(view)
//普通内容
class BlockParagraphHolder(view: View) :
    BaseDataBindingHolder<ItemPlaceBlockParagraphBinding>(view)
//about
class BlockAboutHolder(view: View) :
    BaseDataBindingHolder<ItemPlaceBlockAboutBinding>(view)
class BlockTripHeadHolder(view: View) :
    BaseDataBindingHolder<ItemPlaceBlockTripHeadBinding>(view)
class BlockTripContentHolder(view: View) :
    BaseDataBindingHolder<ItemPlaceBlockTripContentBinding>(view)

class BlockLocationHolder(view: View) :
    BaseDataBindingHolder<ItemPlaceBlockLocationBinding>(view)

class BlockImageHolder(view: View) : BaseDataBindingHolder<ItemPlaceBlockImageBinding>(view)
