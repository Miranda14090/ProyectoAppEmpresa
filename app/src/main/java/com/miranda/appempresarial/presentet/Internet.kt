@file:Suppress("DEPRECATION")

package com.miranda.appempresarial.presentet


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import com.miranda.appempresarial.R


object Internet {

    fun coprobarInternet(c:Context):Boolean{

        val connectivityManager =
            c.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        var info_wifi = connectivityManager?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        var info_datos = connectivityManager?.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        val wifi_connected = info_wifi?.isConnected
        val datos_connected = info_datos?.isConnected

        var isConected:Boolean

        if(wifi_connected!! || datos_connected!!)
        {
            isConected = true
        }
        else
        {
            isConected = false
            var dialogo_internet = AlertDialog.Builder(c)

            dialogo_internet.setTitle(R.string.internet)
                .setMessage(R.string.mensaje_internet)
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, which ->  }) //despues del lambda -> se pone la accion
            dialogo_internet.create()
            dialogo_internet.show()
        }
        return isConected
    }
}