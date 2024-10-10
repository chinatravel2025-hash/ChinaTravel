package com.travel.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.base.base.bean.BlocksBean
import com.travel.home.R
import com.travel.home.databinding.ItemPlaceBlockHeadBinding
import com.travel.home.databinding.ItemPlaceBlockImageBinding
import com.travel.home.databinding.ItemPlaceBlockParagraphBinding

class PlaceBlockAdapter() :
    BaseMultiItemQuickAdapter<BlocksBean, BaseViewHolder>() {
    init {
        addItemType(ITEM_TYPE_HEAD, R.layout.item_place_block_head)
        addItemType(ITEM_TYPE_PARAGRAPH, R.layout.item_place_block_paragraph)
        addItemType(ITEM_TYPE_IMAGE, R.layout.item_place_block_image)
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
        }
    }
    companion object {
        const val ITEM_TYPE_HEAD = 0
        const val ITEM_TYPE_PARAGRAPH = 1
        const val ITEM_TYPE_IMAGE = 2
    }
}
class BlockHeadHolder(view: View) :
    BaseDataBindingHolder<ItemPlaceBlockHeadBinding>(view)
class BlockParagraphHolder(view: View) :
    BaseDataBindingHolder<ItemPlaceBlockParagraphBinding>(view)
class BlockImageHolder(view: View) : BaseDataBindingHolder<ItemPlaceBlockImageBinding>(view)
