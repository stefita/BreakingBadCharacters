package com.stefita.presentation.common.extensions

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load

@BindingAdapter("loadUrl")
fun ImageView.loadUrl(url: String?) {
    val isPlaceholderSet = ((tag as? Int) ?: 0) > 0
    if (url == null && isPlaceholderSet) {
        load(tag as Int) { crossfade(true) }
    } else {
        load(url) { crossfade(true) }
    }
}

@BindingAdapter("loadBitmap")
fun ImageView.loadBitmap(bitmap: Bitmap?) {
    load(bitmap) { crossfade(true) }
}