package dev.thedukerchip.scandy.permissions

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import dev.thedukerchip.scandy.extensions.isPermissionGranted

sealed class PermissionHost(val context: Context) {
    abstract fun isPermissionGranted(permission: String): Boolean
    abstract fun isRationaleRequired(permission: String): Boolean
    abstract fun registerPermissionRequest(callback: PermissionResult): ActivityResultLauncher<String>

    class Activity(private val activity: FragmentActivity) : PermissionHost(activity) {
        override fun isPermissionGranted(permission: String): Boolean =
            activity.isPermissionGranted(permission)

        override fun isRationaleRequired(permission: String) =
            activity.shouldShowRequestPermissionRationale(permission)

        override fun registerPermissionRequest(callback: PermissionResult): ActivityResultLauncher<String> {
            return activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission(),
                callback
            )
        }
    }

    class Fragment(private val fragment: androidx.fragment.app.Fragment) :
        PermissionHost(fragment.requireContext()) {
        override fun isPermissionGranted(permission: String): Boolean =
            fragment.context?.isPermissionGranted(permission) ?: false

        override fun isRationaleRequired(permission: String): Boolean =
            fragment.shouldShowRequestPermissionRationale(permission)

        override fun registerPermissionRequest(callback: PermissionResult): ActivityResultLauncher<String> {
            return fragment.registerForActivityResult(
                ActivityResultContracts.RequestPermission(),
                callback
            )
        }
    }
}
