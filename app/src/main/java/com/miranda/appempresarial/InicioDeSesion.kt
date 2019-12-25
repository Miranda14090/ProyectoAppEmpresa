package com.miranda.appempresarial




import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity



class InicioDeSesion : AppCompatActivity() ,Sesion.FormulariosListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_de_sesion)

        if(savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.contenedorSesion, Sesion(),"")
                .commit()
        //Internet.coprobarInternet(this)

    }
    override fun registroFinishCallback() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.contenedorSesion, Formulario(),"")
            .commit()
    }


}
