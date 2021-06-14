package dev.thedukerchip.scandy.permissions

import androidx.fragment.app.Fragment
import dev.thedukerchip.scandy.ui.extensions.isPermissionGranted

// TODO Make the permission wrapper class lifecycle aware component
// TODO Add the activity result for permission request in here
class PermissionWrapper(private val context: Fragment, private val permission: String) {
    val isGranted: Boolean
        get() = context.isPermissionGranted(permission)

    val shouldShowRationale: Boolean
        get() = context.shouldShowRequestPermissionRationale(permission)
}