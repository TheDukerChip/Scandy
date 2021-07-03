package dev.thedukerchip.scandy.permissions

import android.content.Context
import dev.thedukerchip.scandy.extensions.isPermissionGranted


class PermissionWrapper(private val context: Context, private val permission: String) {
    val isGranted: Boolean
        get() = context.isPermissionGranted(permission)

    init {
        TODO(" Make the permission wrapper class lifecycle aware component")
        TODO ("Add the activity result for permission request in here")
    }

    val shouldShowRationale: Boolean
        get() {
            TODO("Find the show rationale technique to get the state")
        }
}