package dev.thedukerchip.scandy.extensions

import android.view.View

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.setOnClickListener(listener: () -> Unit) {
    setOnClickListener {
        listener()
    }
}
