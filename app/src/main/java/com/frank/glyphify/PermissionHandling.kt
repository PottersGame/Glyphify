package com.frank.glyphify

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.frank.glyphify.ui.dialogs.Dialog

class PermissionHandling(private val activity: Activity) {

    private fun askPermissions(permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    /**shows alert dialog, if ok is pressed a set of permissions are requested otherwise the app is closed*/
    private fun popUp(permissions: Array<String>, requestCode: Int) {
        Dialog.showDialog(
            activity,
            R.layout.dialog_perm_notifications,
            mapOf(
                R.id.positiveButton to {
                    askPermissions(permissions, requestCode)
                },
                R.id.negativeButton to { }
            )
        )
    }

    fun checkRequiredPermission(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun askRequiredPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.POST_NOTIFICATIONS
        )

        if (!checkRequiredPermission(permissions.toTypedArray())) {
            popUp(permissions.toTypedArray(), 1)
        }
    }

}