package dev.thedukerchip.scandy.ui.extensions

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.isPermissionGranted(permission: String): Boolean {
    val permissionState = ContextCompat.checkSelfPermission(requireContext(), permission)
    return permissionState == PackageManager.PERMISSION_GRANTED
}
