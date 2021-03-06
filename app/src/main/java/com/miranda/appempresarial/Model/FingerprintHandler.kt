package com.miranda.appempresarial.Model

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.miranda.appempresarial.R
import com.miranda.appempresarial.presentet.DatabasePresenter
import com.miranda.appempresarial.presentet.DatabasePresenterImp
import com.miranda.appempresarial.presentet.Internet
import com.miranda.appempresarial.view.DatabaseView
import com.miranda.appempresarial.view.fragments.FingerprintFragment
import com.miranda.appempresarial.view.fragments.Sesion

@TargetApi(Build.VERSION_CODES.M)
class FingerprintHandler(private val context: Context) :
    FingerprintManager.AuthenticationCallback(), DatabaseView {

    var preseterDbUser: DatabasePresenter = DatabasePresenterImp(this)

    fun startAuth(
        fingerprintManager: FingerprintManager,
        cryptoObject: FingerprintManager.CryptoObject?
    ) {
        val cancellationSignal = CancellationSignal()
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
    }

    override fun onAuthenticationError(
        errorCode: Int,
        errString: CharSequence
    ) {
        update("Hubo un error de autenticación. $errString", false)
    }

    override fun onAuthenticationFailed() {
        update("Error de autenticación. ", false)
    }

    override fun onAuthenticationHelp(
        helpCode: Int,
        helpString: CharSequence
    ) {
        update("Error: $helpString", false)
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult) {
        update("Autorizado, enviando datos", true)
    }


    private fun update(s: String, b: Boolean) {
        val txtMensajes =
            (context as Activity).findViewById<View>(R.id.txtMensajesFingerprint) as TextView
        val imageView =
            context.findViewById<View>(R.id.fingerprintImage) as ImageView
        txtMensajes.text = s
        if (b == false) {
            txtMensajes.setTextColor(ContextCompat.getColor(context, R.color.error))
        } else {
            //aqui poner inicio
            if(Internet.coprobarInternet(context)) {
                val datosUser = preseterDbUser.obtenerUsuario(Consumo.serviciosDataOnjet)
                val usuario = LoginUser(datosUser!!.password, datosUser.numeroEmpledo)
                Consumo.pedir_loginHuella(usuario, context, datosUser.numeroEmpledo)
            }

            txtMensajes.setTextColor(ContextCompat.getColor(context, R.color.verdeFuerte))
            imageView.setImageResource(R.mipmap.action_done)
        }
    }

}