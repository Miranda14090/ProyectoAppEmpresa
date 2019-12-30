package com.miranda.appempresarial.view.fragments


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.ReportesSend
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.ApiEmpleados
import com.miranda.appempresarial.api.Api_Envio
import com.miranda.appempresarial.api.RegistroEmpleadoResponse
import com.miranda.appempresarial.api.RegistroReporteResponse
import kotlinx.android.synthetic.main.fragment_reportes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.miranda.appempresarial.view.fragments.Reportes as Reportes

/**
 * A simple [Fragment] subclass.
 */
class Reportes : Fragment() {
    
    var clasificacion:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reportes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        clasificacion = 0

        cardViewTecnico.setOnClickListener {
            clasificacion = 1
            MostrarDescripcion()
        }

        cardViewMantenimiento.setOnClickListener {
            clasificacion = 2
            MostrarDescripcion()
        }

        cardViewAdministrativo.setOnClickListener {
            clasificacion = 3
            MostrarDescripcion()
        }
        cardViewServicio.setOnClickListener {
            clasificacion = 4
            MostrarDescripcion()
        }

        descipcion.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(descipcion.length()>=1){
                    boton_EnviarReporte.visibility=View.VISIBLE
                }else
                    boton_EnviarReporte.visibility=View.INVISIBLE
            }
        })

        boton_EnviarReporte.setOnClickListener{
            if(descipcion.text.toString().replace(" ","")=="" || descipcion.text.toString()[0]==' '){
                descipcion.error="Campo no valido"

            }else{
                descipcion.error=null
                val numeroDeEmpleado = "000028"

                val reporte = ReportesSend(descipcion.text.toString(),clasificacion,numeroDeEmpleado)
                Consumo.registrar_reporte(activity!!,reporte)

            }
                
        }


    }

    private fun MostrarDescripcion() {
        descipcion.visibility=View.VISIBLE
    }


    companion object {
        fun newInstance(): Reportes =
            Reportes()
    }

    fun mensajeReporte(c:Context, txtmensaje:String)
    {
        val dialogoRespuesta = AlertDialog.Builder(c)

        dialogoRespuesta.setTitle(R.string.reportes)
            .setMessage(txtmensaje)
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which ->    }) //despues del lambda -> se pone la accion
        dialogoRespuesta.create()
        dialogoRespuesta.show()
    }


}

