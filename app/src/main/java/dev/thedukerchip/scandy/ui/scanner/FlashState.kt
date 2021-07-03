package dev.thedukerchip.scandy.ui.scanner

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.camera.core.TorchState
import scandy.R

sealed class FlashState {
    abstract fun other(): FlashState

    abstract fun enabled(): Boolean

    abstract fun value(): Int

    @ColorRes
    abstract fun color(): Int

    @DrawableRes
    abstract fun icon(): Int

    object ON : FlashState() {
        override fun other() = OFF
        override fun enabled() = true
        override fun value() = TorchState.ON
        override fun color() = R.color.flash_active
        override fun icon() = R.drawable.ic_flash_active
    }

    object OFF : FlashState() {
        override fun other() = ON
        override fun enabled() = false
        override fun value() = TorchState.OFF
        override fun color() = R.color.flash_inactive
        override fun icon() = R.drawable.ic_flash_inactive
    }

    companion object {
        fun fromTorchState(state: Int) = if (state == TorchState.ON) ON else OFF
    }
}
