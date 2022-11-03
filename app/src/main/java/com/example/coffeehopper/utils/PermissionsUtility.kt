package com.example.coffeehopper.utils

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.coffeehopper.BuildConfig
import com.example.coffeehopper.R
import com.google.android.material.snackbar.Snackbar

// contains functions and constants for gathering permissions.

fun Fragment.foregroundAndBackgroundLocationPermissionApproved(): Boolean {
    return (
            PackageManager.PERMISSION_GRANTED ==
                    ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) &&
                    PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) && (PackageManager.PERMISSION_GRANTED ==
                    ContextCompat.checkSelfPermission(
                        requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ))
            )
}

fun Fragment.requestForegroundAndBackgroundLocationPermissions(
    requestLauncher: ActivityResultLauncher<Array<String>>
) {
    // if the permissions are NOT approved then request permission from user.
    if (!foregroundAndBackgroundLocationPermissionApproved()) {
        var permissionsArray = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION)

        requestLauncher.launch(permissionsArray)
        return
    }
}

fun Fragment.getPermissionRequestLauncher(view: View, permissionsGrantedAction :()->Unit): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        if (it.isEmpty() || it[Manifest.permission.ACCESS_FINE_LOCATION] == false || ( it[Manifest.permission.ACCESS_BACKGROUND_LOCATION] == false)) {
            Snackbar.make(
                view,
                R.string.permission_denied_explanation,
                Snackbar.LENGTH_INDEFINITE
            ).setAction(R.string.settings) {
                startActivity(Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }.show()
        }
        else {
            permissionsGrantedAction()
        }
    }
}