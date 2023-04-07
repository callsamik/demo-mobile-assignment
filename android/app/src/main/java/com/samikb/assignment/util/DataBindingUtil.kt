package com.samikb.assignment.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class DataBindingUtil {
    companion object {
        @JvmStatic
        @BindingAdapter(value = ["glideSrc", "glidePlaceholder", "corner_radius"], requireAll = false)
        fun setImageWithGlide(
            imageView: ImageView,
            imageUrl: String,
            drawable: Drawable?,
            cornerRadius: Int
        ) {
            var requestOptions = RequestOptions()
            if (drawable != null) {
                requestOptions = requestOptions
                    .placeholder(drawable)
            }
            if (cornerRadius > 0) {
                requestOptions = requestOptions
                    .transform(RoundedCorners(cornerRadius))
            }
            Glide.with(imageView.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .apply(requestOptions.centerInside())
                .into(imageView)
        }
    }

}