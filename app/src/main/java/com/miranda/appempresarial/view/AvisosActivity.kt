package com.miranda.appempresarial.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import com.miranda.appempresarial.Model.AdapterAvisos
import com.miranda.appempresarial.Model.CambiarEstadoAviso
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.ListaDeAvisos
import kotlinx.android.synthetic.main.activity_avisos.*

class AvisosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title="Aviso"
        setContentView(R.layout.activity_avisos)
        var recibido=(intent.getSerializableExtra("Avisos")as? ListaDeAvisos)!!

        val identificador=recibido.identificador
        val numEp=Consumo.TuNumeroDeEmpleado
        val aviso= CambiarEstadoAviso(numEp,identificador)
        Consumo.cambiarEstadoDeAviso(aviso,applicationContext,"Avisos")

        txtCuerpo.setMovementMethod(ScrollingMovementMethod())

        txtTitulo.text=recibido.titulo
        txtCuerpo.text=recibido.cuerpo

        boton_regresar.setOnClickListener {
            finish()
        }

        boton_eliminar.setOnClickListener {
            Consumo.borrarAviso(aviso,applicationContext,"Avisos")
            finish()
        }
    }
}
