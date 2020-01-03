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
                            Formulario.newInstance().mensaje(context,"Empleado registrado correctamente,tú número de empleado es: $numeroDeEmpleado",0, view)
                        }
                        1 -> {
                            Formulario.newInstance().mensaje(context,"El empleado ${empleado.nombres} ya se encuentra registrado",1, view)
                        }

                        2 -> {
                            Formulario.newInstance().mensaje(context,"Formato de datos incorrecto.Intente nuevamente",2,view)
                        }
                        else -> {
                            Formulario.newInstance().mensaje(context,"Error inesperado, marcar al soporte tecnico para más ayuda", -1,view)
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
                                "${response.body()?.descripcion}. $numeroFolio")
                        }
                        -1 -> {
                            Reportes.newInstance().mensajeReporte(context,"${response.body()?.descripcion}")
                        }
                        2 -> {
                            Reportes.newInstance().mensajeReporte(context,"${response.body()?.descripcion},Verifeque  su reporte,Intentelo denuevo")
                        }
                    }
                } else {
                    Reportes.newInstance().mensajeReporte(context,R.string.noneServise.toString())
                }
            }
        })

    }

    fun pedir_login(usuario:LoginUser, context:Context, titulo:String, empleado:String){

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
                           TuNumeroDeEmpleado=empleado
                           val intento1 = Intent(context, MainActivity::class.java)
                           context.startActivity(intento1)
                       }
                       -1 -> {
                           mensajes(context,titulo,"Error inesperado")
                       }
                       3 ->{
                           mensajes(context,titulo,"Numero de empleado y/o contraseña incorrecto")
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
                            mensajes(context,titulo,R.string.ErrorFormatos.toString())
                        }
                    }
                }else{
                    mensajes(context,titulo,R.string.noneServise.toString())
                }
            }
        })
    }

    fun registrarAsistencia(asistencia:RegistroAsistencia,context: Context,titulo: String){
        val callRespuesta = apiEnvios.registrar_asistencia("text/plain",asistencia)
        callRespuesta.enqueue(object :Callback<RegistroAsistenciaResponse>{
            override fun onFailure(call: Call<RegistroAsistenciaResponse>, t: Throwable) {
                mensajes(context,titulo,R.string.noneServise.toString())
            }

            override fun onResponse(
                call: Call<RegistroAsistenciaResponse>,
                response: Response<RegistroAsistenciaResponse>
            ) {
                if(response.isSuccessful){
                    when(response.body()?.codigoOperacion){
                        0 -> {
                            mensajes(context,titulo,"Registro de asistencia exitoso, su folio es: ${response.body()?.folio}")
                        }
                        -1 ->{
                            //Error
                        }
                        2 ->{
                            //Formato Invalido
                            mensajes(context,titulo,"${response.body()?.descripcion}")
                        }
                        4 -> {
                            mensajes(context,titulo,"${response.body()?.descripcion},Capture otra imagen e intentelo nuevamente")
                        }
                        5 -> {
                            //Asistencia del dia resgistrada
                        }

                    }

                }
                else{
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