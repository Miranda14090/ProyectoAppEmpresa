package com.miranda.appempresarial.presentet

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.miranda.appempresarial.view.PermissionsView

class PermissionsImp(var view:PermissionsView):Permissions {

    override fun cameraPermission(c: Context): Boolean {
        var version = Build.VERSION.SDK_INT

        if (version >= Build.VERSION_CODES.M) {
            return if (ContextCompat.checkSelfPermission(
                    c,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(c as Activity, arrayOf(Manifest.permission.CAMERA), 1000)
                false
            } else {
                true
            }
        } else {
            Toast.makeText(c, "No es necesario pedir permisos", Toast.LENGTH_SHORT)
                .show()
            return true
        }
    }
}