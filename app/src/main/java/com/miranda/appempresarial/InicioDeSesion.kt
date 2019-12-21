package com.miranda.appempresarial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class InicioDeSesion : AppCompatActivity() ,Sesion.FormulariosListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_de_sesion)

        if(savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.contenedorSesion, Sesion(),"")
                .commit()
    }


    override fun registroFinishCallback() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.contenedorSesion, Formulario(),"")
            .commit()
    }


    /*fun MostrarMenu(view: View) {
        val intento1 = Intent(this, MainActivity::class.java)
        startActivity(intento1)
    }*/
}
