package com.miranda.appempresarial.view




import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.miranda.appempresarial.R
import com.miranda.appempresarial.presentet.Permissions
import com.miranda.appempresarial.presentet.PermissionsImp
import com.miranda.appempresarial.view.fragments.FingerprintFragment
import com.miranda.appempresarial.view.fragments.Formulario
import com.miranda.appempresarial.view.fragments.Sesion


class InicioDeSesion : AppCompatActivity(),
    Sesion.FormulariosListener, Formulario.FormulariosListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_de_sesion)

        if(savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.contenedorSesion,
                    Sesion(),"")
                .commit()

        //PedirHuella()
    }
    override fun registroFinishCallback() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.contenedorSesion,
                Formulario(),"")
            .commit()
    }

    override fun loginFinishCallback(){
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.contenedorSesion,
                Sesion()  ,"")
            .commit()
    }

    fun PedirHuella(){
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.contenedorHuella,
                FingerprintFragment(),"")
            .commit()
    }

}
