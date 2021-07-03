package dev.thedukerchip.scandy.permissions

import androidx.activity.result.ActivityResultCallback
import dev.thedukerchip.scandy.extensions.openApplicationSettings
import dev.thedukerchip.scandy.ui.BaseActivity

typealias PermissionResult = ActivityResultCallback<Boolean>

class PermissionWrapper(
    private val host: PermissionHost,
    private val permission: String,
    permissionResult: PermissionResult
) {
    val isGranted: Boolean
        get() = host.isPermissionGranted(permission)

    val shouldShowRationale: Boolean
        get() = host.isRationaleRequired(permission)

    private val activityResult = host.registerPermissionRequest(permissionResult)

    fun check(action: () -> Unit) {
        if (isGranted) {
            action()
        } else {
            request()
        }
    }

    fun request() {
        if (shouldShowRationale) {
            activityResult.launch(permission)
        } else {
            host.context.openApplicationSettings()
        }
    }
}


fun BaseActivity.permissionWrapperFor(
    permission: String,
    callback: PermissionResult
) = PermissionWrapper(
    PermissionHost.Activity(this),
    permission,
    callback
)