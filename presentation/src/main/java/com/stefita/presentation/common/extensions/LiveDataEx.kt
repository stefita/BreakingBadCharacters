package com.stefita.presentation.common.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(owner: LifecycleOwner, onChange: (T) -> Unit) {
    observe(owner, Observer<T> { it?.let(onChange) })
}