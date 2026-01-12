package com.travel.home.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.base.glide.GlideApp
import com.example.base.utils.AppConfig
import com.travel.home.R
import com.travel.home.widget.ZoomImageView

/**
 * 图片预览DialogFragment
 * 支持缩放和点击关闭，参考微信体验
 */
class ImagePreviewDialogFragment : DialogFragment() {

    private var imageUrl: String? = null

    companion object {
        private const val ARG_IMAGE_URL = "image_url"

        fun newInstance(imageUrl: String): ImagePreviewDialogFragment {
            val fragment = ImagePreviewDialogFragment()
            val args = Bundle()
            args.putString(ARG_IMAGE_URL, imageUrl)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageUrl = arguments?.getString(ARG_IMAGE_URL)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_image_preview, container, false)
        val zoomImageView = view.findViewById<ZoomImageView>(R.id.zoom_image_view)
        
        // 加载图片
        imageUrl?.let { url ->
            GlideApp.with(requireContext())
                .load(AppConfig.appBaseImg(url))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(zoomImageView)
        }
        
        // 点击图片空白处关闭（当图片未放大时）
        zoomImageView.setOnSingleTapListener {
            // 如果图片处于最小缩放状态，点击关闭
            if (zoomImageView.getCurrentScale() <= zoomImageView.getMinScale() + 0.1f) {
                dismiss()
            }
        }
        
        // 点击背景关闭
        view.setOnClickListener {
            dismiss()
        }
        
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.let { window ->
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            window.setBackgroundDrawableResource(android.R.color.black)
            window.setDimAmount(1.0f)
        }
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}





