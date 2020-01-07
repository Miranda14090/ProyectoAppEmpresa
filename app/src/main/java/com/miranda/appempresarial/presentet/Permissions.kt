package com.miranda.appempresarial.presentet

import android.content.Context

interface Permissions {
    fun cameraPermission(c:Context):Boolean
    fun permisosFingerprint(c:Context):Boolean
}