package com.miranda.appempresarial


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager



object Internet {

    fun coprobarInternet(c:Context):Boolean{

        val connectivityManager =
            c.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        var info_wifi = connectivityManager?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        var info_datos = connectivityManager?.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        var isConected = (info_wifi?.getState()!!.equals("CONNECTED")) ||
                (info_datos?.getState()!!.equals("CONNECTED"))
        if(!isConected)
        {
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