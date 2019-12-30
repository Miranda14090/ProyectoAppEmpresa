package com.miranda.appempresarial.view.fragments


import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import android.widget.Toast
import com.miranda.appempresarial.Model.ReportesSend
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.ApiEmpleados
import com.miranda.appempresarial.api.Api_Envio
import com.miranda.appempresarial.api.RegistroEmpleadoResponse
import com.miranda.appempresarial.api.RegistroReporteResponse
import com.miranda.appempresarial.view.ListaReportes
import com.miranda.appempresarial.view.MainActivity
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
    lateinit var apiReporte: ApiEmpleados

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
            MostrarColor()
        }

        cardViewMantenimiento.setOnClickListener {
            clasificacion = 2
            MostrarDescripcion()
            MostrarColor()
        }

        cardViewAdministrativo.setOnClickListener {
            clasificacion = 3
            MostrarDescripcion()
            MostrarColor()
        }
        cardViewServicio.setOnClickListener {
            clasificacion = 4
            MostrarDescripcion()
            MostrarColor()
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
                val numeroDeEmpleado = "000001"

                val reporte = ReportesSend(descipcion.text.toString(),clasificacion,numeroDeEmpleado)

                apiReporte = Api_Envio.getApiEnvio().create(ApiEmpleados::class.java)
                val callRespuesta = apiReporte.registrar_reporte("text/plain", reporte)

                callRespuesta.enqueue(object: Callback<RegistroReporteResponse>{

                    override fun onFailure(call: Call<RegistroReporteResponse>, t: Throwable) {
                        activity?.let { it1 -> mensaje(it1, R.string.noneServise.toString(),0) }

                    }
                    override fun onResponse(
                        call: Call<RegistroReporteResponse>,
                        response: Response<RegistroReporteResponse>
                    ){
                        if (response.isSuccessful) {

                            when (val codigoOperacion = response.body()?.codigoOperacion) {
                                0 -> {
                                    val numeroFolio= response.body()?.folio
                                    activity?.let { it1 -> mensaje(it1, "Tu Registro fue correcto, tu numerp de reporte es $numeroFolio",0) }
                                }
                                -1 -> {
                                    activity?.let { it1 -> mensaje(it1, "Tu Registro fallo intentalo de nuevo mas tarde",0) }
                                }
                                2 -> {
                                    activity?.let { it1 -> mensaje(it1, "Error inesperado, marcar al soporte para más ayuda",0) }
                                }
                            }
                        } else {
                            activity?.let { it1 -> mensaje(it1, "Error inesperado, marcar al soporte para más ayuda",0) }
                        }

                    }
                })

                descipcion.error=null
            }

        }


        btnListaReportes.setOnClickListener {
            val intento2 = Intent(activity, ListaReportes::class.java)
            activity?.startActivity(intento2)
        }

    }


    private fun MostrarColor(){
       when(clasificacion){
           1 -> {

              // tvTecnico.setTextColor(R.color.primaryColor.toInt())
               //tvTecnico.setTextColor(Color.parseColor("#558b2f"))
               tvTecnico.setTextColor(Color.GREEN)
               tvMantenimiento.setTextColor(Color.GRAY)
               tvAdministrativo.setTextColor(Color.GRAY)
               tvServicio.setTextColor(Color.GRAY)
           }
           2 -> {
               tvTecnico.setTextColor(Color.GRAY)
               tvMantenimiento.setTextColor(Color.GREEN)
               tvAdministrativo.setTextColor(Color.GRAY)
               tvServicio.setTextColor(Color.GRAY)
           }
           3 -> {
               tvTecnico.setTextColor(Color.GRAY)
               tvMantenimiento.setTextColor(Color.GRAY)
               tvAdministrativo.setTextColor(Color.GREEN)
               tvServicio.setTextColor(Color.GRAY)
           }
           4 -> {
               tvTecnico.setTextColor(Color.GRAY)
               tvMantenimiento.setTextColor(Color.GRAY)
               tvAdministrativo.setTextColor(Color.GRAY)
               tvServicio.setTextColor(Color.GREEN)
           }
           else ->{
               tvTecnico.setTextColor(Color.GRAY)
               tvMantenimiento.setTextColor(Color.GRAY)
               tvAdministrativo.setTextColor(Color.GRAY)
               tvServicio.setTextColor(Color.GRAY)
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

    fun mensaje(c:Context, txtmensaje:String, codigo:Int)
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

