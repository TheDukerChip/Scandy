package dev.thedukerchip.scandy.extensions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.isPermissionGranted(permission: String): Boolean {
    val permissionState = ContextCompat.checkSelfPermission(this, permission)
    return permissionState == PackageManager.PERMISSION_GRANTED
}
