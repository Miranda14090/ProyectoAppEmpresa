package com.miranda.appempresarial.Model

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.*
import com.miranda.appempresarial.view.MainActivity
import com.miranda.appempresarial.view.fragments.Formulario
import com.miranda.appempresarial.view.fragments.Reportes
import com.miranda.appempresarial.view.fragments.Sesion
import kotlinx.android.synthetic.main.fragment_status_report.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Consumo {

   var TuNumeroDeEmpleado:String=""

    var apiEnvios: ApiEmpleados = Api_Envio.getApiEnvio().create(ApiEmpleados::class.java)

    fun registrar_usuario(empleado:Empleado, context:Context, view: View){
        val CallRespuesta = apiEnvios.registrar_empleado("text/plain", empleado)
        CallRespuesta.enqueue(object: Callback<RegistroEmpleadoResponse> {
            override fun onFailure(call: Call<RegistroEmpleadoResponse>, t: Throwable) {
                Formulario.newInstance().mensaje(context,R.string.noneServise.toString(),404, view)
            }

            override fun onResponse(
                call: Call<RegistroEmpleadoResponse>,
                response: Response<RegistroEmpleadoResponse>
            ) {
                if (response.isSuccessful) {
                    Log.w("Empleado", "Respuesta correcta")
                    Log.i("Empleado", response.body().toString())
                    val numeroDeEmpleado = response.body()?.numeroDeEmpleado
                    when (val codigoOperacion = response.body()?.codigoOperacion) {
                        0 -> {
                            TuNumeroDeEmpleado = numeroDeEmpleado!!
                            Formulario.newInstance().mensaje(context,"Tú número de empleado es: $numeroDeEmpleado",0, view)
                        }
                        1 -> {
                            Formulario.newInstance().mensaje(context,"El empleado ${empleado.nombres} ya se encuentra registrado",1, view)
                        }
                        else -> {
                            Formulario.newInstance().mensaje(context,"Error inesperado, marcar al soporte para más ayuda", 2,view)
                        }
                    }
                } else {
                    Formulario.newInstance().mensaje(context,R.string.noneServise.toString(),404, view)
                }

            }
        })
    }

    fun registrar_reporte(context:Context, reporte:ReportesSend){

        val callRespuesta = apiEnvios.registrar_reporte("text/plain", reporte)

        callRespuesta.enqueue(object: Callback<RegistroReporteResponse>{
            override fun onFailure(call: Call<RegistroReporteResponse>, t: Throwable) {
                Reportes.newInstance().mensajeReporte(context,R.string.noneServise.toString())
            }
            override fun onResponse(
                call: Call<RegistroReporteResponse>,
                response: Response<RegistroReporteResponse>
            ){
                if (response.isSuccessful) {

                    when (val codigoOperacion = response.body()?.codigoOperacion) {
                        0 -> {
                            val numeroFolio= response.body()?.folio
                            Reportes.newInstance().mensajeReporte(context,
                                "Tu Registro fue correcto, tu numerp de reporte es: $numeroFolio")
                        }
                        -1 -> {
                            Reportes.newInstance().mensajeReporte(context,"Tu Registro fallo intentalo de nuevo mas tarde")
                        }
                        2 -> {
                            Reportes.newInstance().mensajeReporte(context,"Error inesperado, marcar al soporte para más ayuda")
                        }
                    }
                } else {
                    Reportes.newInstance().mensajeReporte(context,R.string.noneServise.toString())
                }
            }
        })

    }

    fun pedir_login(usuario:LoginUser, context:Context, titulo:String){

        val CallRespuesta = apiEnvios.login_user("text/plain", usuario)
        CallRespuesta.enqueue(object : Callback<LoginUserResponse> {

                override fun onFailure(call: Call<LoginUserResponse>, t: Throwable){
                mensajes(context, titulo, R.string.noneServise.toString())
            }
            override fun onResponse(call: Call<LoginUserResponse>, response:Response<LoginUserResponse>){
                if(response.isSuccessful)
                {
                   when (response.body()?.codigoOperacion){
                       0 -> {
                           val intento1 = Intent(context, MainActivity::class.java)
                           context.startActivity(intento1)
                           //pasar los datos de login
                       }
                       -1 -> {
                           mensajes(context,titulo,"Error inesperado")
                       }
                       3 ->{
                           mensajes(context,titulo,"Usuario o contraseña incorrecto")
                       }
                   }

                }
                else {
                    mensajes(context,titulo,R.string.noneServise.toString())
                }
            }
        })
    }

    fun mostrar_reportes(consulta:InboxReport,context: Context,titulo: String,view:View){
        val callRespuesta = apiEnvios.consultar_reportes("text/plain",consulta)
        callRespuesta.enqueue(object : Callback<ConsultarReportesResponse>{
            override fun onFailure(call: Call<ConsultarReportesResponse>, t: Throwable) {
                mensajes(context,titulo,R.string.noneServise.toString())
            }

            override fun onResponse(
                call: Call<ConsultarReportesResponse>,
                response: Response<ConsultarReportesResponse>
            ) {
                if(response.isSuccessful){
                    when(response.body()?.codigoDeOperacion){

                        0 -> {
                            //Consulta Exitosa
                            view.recyclerReportes.layoutManager= LinearLayoutManager(context)
                            val miAdaptador=AdaptaterReports(response.body()?.reportes as ArrayList<ListaDeReporte>)
                            view.recyclerReportes.adapter=miAdaptador
                        }
                        -1 ->{
                            //Error
                            mensajes(context,titulo,R.string.ErrorConsultarReporte.toString())
                        }
                        2 ->{
                            //Formato Invalido
                            mensajes(context,titulo,R.string.ErrorFormatoReporte.toString())
                        }
                    }
                }else{
                    mensajes(context,titulo,R.string.noneServise.toString())
                }
            }
        })
    }

    private fun mensajes(context: Context, titulo: String, s: String) {
        val dialogoRespuesta = AlertDialog.Builder(context)

        dialogoRespuesta.setTitle(titulo)
            .setMessage(s)
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which ->    }) //despues del lambda -> se pone la accion
        dialogoRespuesta.create()
        dialogoRespuesta.show()

    }

}