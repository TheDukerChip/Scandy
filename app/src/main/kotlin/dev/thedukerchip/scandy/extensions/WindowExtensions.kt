package dev.thedukerchip.scandy.extensions

import android.view.Window
import android.view.WindowManager

fun Window.setNoLimitsToLayout() {
    setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}