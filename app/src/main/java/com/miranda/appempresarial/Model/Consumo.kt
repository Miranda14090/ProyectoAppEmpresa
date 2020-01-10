package com.miranda.appempresarial.Model

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.*
import com.miranda.appempresarial.view.MainActivity
import com.miranda.appempresarial.view.fragments.Avisos
import com.miranda.appempresarial.view.fragments.Formulario
import com.miranda.appempresarial.view.fragments.Reportes
import kotlinx.android.synthetic.main.fragment_avisos.view.*
import kotlinx.android.synthetic.main.fragment_perfil_usuario.view.*
import kotlinx.android.synthetic.main.fragment_status_report.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Consumo {

    var TuNumeroDeEmpleado: String = ""
    var asistenciaDelDia: Boolean = false

    var apiEnvios: ApiEmpleados = Api_Envio.getApiEnvio().create(ApiEmpleados::class.java)

    fun registrar_usuario(empleado: Empleado, context: Context, view: View) {
        val CallRespuesta = apiEnvios.registrar_empleado("text/plain", empleado)
        CallRespuesta.enqueue(object : Callback<RegistroEmpleadoResponse> {
            override fun onFailure(call: Call<RegistroEmpleadoResponse>, t: Throwable) {
                Formulario.newInstance()
                    .mensaje(context, context.resources.getString(R.string.noneServise), 404, view)
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
                            Formulario.newInstance().mensaje(
                                context,
                                "Empleado registrado correctamente, tu número de empleado es: $numeroDeEmpleado",
                                0,
                                view
                            )
                        }
                        1 -> {
                            Formulario.newInstance().mensaje(
                                context,
                                "El empleado ${empleado.nombres} ya se encuentra registrado",
                                1,
                                view
                            )
                        }

                        2 -> {
                            Formulario.newInstance().mensaje(
                                context,
                                "Formato de datos incorrecto.Intente nuevamente",
                                2,
                                view
                            )
                        }
                        else -> {
                            Formulario.newInstance().mensaje(
                                context,
                                "Error inesperado, marcar al soporte tecnico para más ayuda",
                                -1,
                                view
                            )
                        }
                    }
                } else {
                    Formulario.newInstance().mensaje(
                        context,
                        context.resources.getString(R.string.noneServise),
                        404,
                        view
                    )
                }

            }
        })
    }

    fun registrar_reporte(context: Context, report: ReportesSend) {

        val callRespuesta = apiEnvios.registrar_reporte("text/plain", report)

        callRespuesta.enqueue(object : Callback<RegistroReporteResponse> {
            override fun onFailure(call: Call<RegistroReporteResponse>, t: Throwable) {
                Reportes.newInstance()
                    .mensajeReporte(context, context.resources.getString(R.string.noneServise))
            }

            override fun onResponse(
                call: Call<RegistroReporteResponse>,
                response: Response<RegistroReporteResponse>
            ) {
                if (response.isSuccessful) {

                    when (val codigoOperacion = response.body()?.codigoOperacion) {
                        0 -> {
                            val numeroFolio = response.body()?.folio
                            Reportes.newInstance().mensajeReporte(
                                context,
                                "${response.body()?.descripcion}. $numeroFolio"
                            )
                        }
                        -1 -> {
                            Reportes.newInstance()
                                .mensajeReporte(context, "${response.body()?.descripcion}")
                        }
                        2 -> {
                            Reportes.newInstance().mensajeReporte(
                                context,
                                "${response.body()?.descripcion},Verifeque  su reporte,Intentelo denuevo"
                            )
                        }
                        else -> {
                            Reportes.newInstance().mensajeReporte(
                                context,
                                "Error inesperado, marcar al soporte tecnico para más ayuda"
                            )
                        }
                    }
                } else {
                    Reportes.newInstance()
                        .mensajeReporte(context, context.resources.getString(R.string.noneServise))
                }
            }
        })

    }

    fun pedir_login(usuario: LoginUser, context: Context, title: String, empleado: String) {

        val CallRespuesta = apiEnvios.login_user("text/plain", usuario)
        CallRespuesta.enqueue(object : Callback<LoginUserResponse> {

            override fun onFailure(call: Call<LoginUserResponse>, t: Throwable) {
                mensajes(context, title, context.resources.getString(R.string.noneServise))
            }

            override fun onResponse(
                call: Call<LoginUserResponse>,
                response: Response<LoginUserResponse>
            ) {
                if (response.isSuccessful) {
                    when (response.body()?.codigoOperacion) {
                        0 -> {
                            TuNumeroDeEmpleado = empleado
                            val intento1 = Intent(context, MainActivity::class.java)
                            context.startActivity(intento1)
                        }
                        -1 -> {
                            mensajes(context, title, "Error inesperado")
                        }
                        3 -> {
                            mensajes(
                                context,
                                title,
                                "Numero de empleado y/o contraseña incorrecto"
                            )
                        }
                        else -> {
                            mensajes(
                                context,
                                title,
                                "Error inesperado, marcar al soporte tecnico para más ayuda"
                            )
                        }
                    }
                } else {
                    mensajes(context, title, context.resources.getString(R.string.noneServise))
                }
            }
        })
    }

    fun mostrar_reportes(consulta: InboxReport, context: Context, title: String, view: View) {
        val callRespuesta = apiEnvios.consultar_reportes("text/plain", consulta)
        callRespuesta.enqueue(object : Callback<ConsultarReportesResponse> {
            override fun onFailure(call: Call<ConsultarReportesResponse>, t: Throwable) {
                mensajes(context, title, context.resources.getString(R.string.noneServise))
            }

            override fun onResponse(
                call: Call<ConsultarReportesResponse>,
                response: Response<ConsultarReportesResponse>
            ) {
                if (response.isSuccessful) {
                    when (response.body()?.codigoDeOperacion) {

                        0 -> {
                            //Consulta Exitosa
                            view.recyclerReportes.layoutManager = LinearLayoutManager(context)
                            val miAdaptador =
                                AdaptaterReports(response.body()?.reportes as ArrayList<ListaDeReporte>)
                            view.recyclerReportes.adapter = miAdaptador
                        }
                        -1 -> {
                            //Error
                            mensajes(context, title, context.resources.getString(R.string.ErrorConsultarReporte))
                        }
                        2 -> {
                            //Formato Invalido
                            mensajes(context, title, R.string.ErrorFormatos.toString())
                        }
                        else -> {
                            mensajes(
                                context,
                                title,
                                "Error inesperado, marcar al soporte tecnico para más ayuda"
                            )
                        }

                    }
                } else {
                    mensajes(context, title, context.resources.getString(R.string.noneServise))
                }
            }
        })
    }

    fun mostrar_avisos(consulta: RegistroAviso, context: Context, title: String, view: View) {
        val callRespuesta = apiEnvios.registrar_avisos("text/plain", consulta)
        callRespuesta.enqueue(object : Callback<RegistroAvisoResponse> {
            override fun onFailure(call: Call<RegistroAvisoResponse>, t: Throwable) {
                mensajes(context, title, context.resources.getString(R.string.noneServise))
            }

            override fun onResponse(
                call: Call<RegistroAvisoResponse>,
                response: Response<RegistroAvisoResponse>
            ) {
                if (response.isSuccessful) {
                    when (response.body()?.codigoDeOperacion) {
                        0 -> {
                            //consulta exitosa
                            /*view.recyclerNotificaciones.layoutManager=LinearLayoutManager(context)
                            val miAdaptador=AdapterAvisos(response.body()?.avisos as ArrayList<ListaDeAvisos>)
                            view.recyclerNotificaciones.adapter=miAdaptador*/

                            var miRecyclerView = view.recyclerNotificaciones
//                            MainActivity.newInstance().llenarRecycler(response.body()?.avisos as ArrayList<ListaDeAvisos>, miRecyclerView)
                            Avisos.newInstance().llenarRecycler(response.body()?.avisos as ArrayList<ListaDeAvisos>, miRecyclerView)

                        }
                        -1 -> {
                            // ERROR NO CONTROLADO
                            mensajes(context, title, "${response.body()?.descripcion}")
                        }

                    }
                }
            }


        })
    }

    fun registrarAsistencia(asistencia: RegistroAsistencia, context: Context, titulo: String) {
        val callRespuesta = apiEnvios.registrar_asistencia("text/plain", asistencia)
        callRespuesta.enqueue(object : Callback<RegistroAsistenciaResponse> {
            override fun onFailure(call: Call<RegistroAsistenciaResponse>, t: Throwable) {
                mensajes(context, titulo, context.resources.getString(R.string.noneServise))
            }

            override fun onResponse(
                call: Call<RegistroAsistenciaResponse>,
                response: Response<RegistroAsistenciaResponse>
            ) {
                if (response.isSuccessful) {
                    when (response.body()?.codigoOperacion) {
                        0 -> {
                            mensajes(
                                context,
                                titulo,
                                "Registro de asistencia exitoso, su folio es: ${response.body()?.folio}"
                            )
                        }
                        -1 -> {
                            //Error
                        }
                        2 -> {
                            //Formato Invalido
                            mensajes(context, titulo, "${response.body()?.descripcion}")
                        }
                        4 -> {
                            mensajes(
                                context,
                                titulo,
                                "${response.body()?.descripcion},Capture otra imagen e intentelo nuevamente"
                            )
                        }
                        5 -> {
                            //Asistencia del dia resgistrada
                            mensajes(context, titulo, "${response.body()?.descripcion}")
                        }
                        else -> {
                            mensajes(
                                context,
                                titulo,
                                "Error inesperado, marcar al soporte tecnico para más ayuda"
                            )
                        }
                    }

                } else {
                    mensajes(context, titulo, context.resources.getString(R.string.noneServise))
                }

            }
        })
    }

    fun datosEmpleado(datos: InfoEmpleado, context: Context, title: String, view: View) {
        val callRespuesta = apiEnvios.pedirInformacionEmpleado("text/plain", datos)
        callRespuesta.enqueue(object : Callback<InfoEmpleadoResponse> {
            override fun onFailure(call: Call<InfoEmpleadoResponse>, t: Throwable) {
                mensajes(context, title, context.resources.getString(R.string.noneServise))
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<InfoEmpleadoResponse>,
                response: Response<InfoEmpleadoResponse>
            ) {
                if (response.isSuccessful) {
                    when (response.body()?.codigoOperacion) {
                        0 -> {
                            view.txtDatosEmpleadoPerfil.text = "${response.body()?.nombres} ${response.body()?.apellidoPaterno}"
                            Toast.makeText(context, "Usted tiene: ${response.body()?.avisosPendientes} avisos pendientes" , Toast.LENGTH_LONG).show()
                        }
                        -1 -> {
                            Toast.makeText(context, "${response.body()?.descripcion}", Toast.LENGTH_SHORT).show()
                        }
                        2 -> {
                            //Formato Invalido
                            Toast.makeText(
                                context,
                                "${response.body()?.descripcion}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        3 -> {
                            Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            mensajes(context, title, "Error inesperado, marcar al soporte tecnico para más ayuda")
                        }
                    }

                } else {
                    mensajes(context, title, context.resources.getString(R.string.noneServise))
                }
            }
        })
    }

    fun cambiarEstadoDeAviso(aviso:CambiarEstadoAviso, context: Context, title: String){
        val callRespuesta = apiEnvios.cambiarEstadoAviso("text/plain",aviso)
        callRespuesta.enqueue(object :Callback<CambiarEstadoAvisoResponse>{
            override fun onFailure(call: Call<CambiarEstadoAvisoResponse>, t: Throwable) {
                mensajes(context, title, context.resources.getString(R.string.noneServise))
            }

            override fun onResponse(
                call: Call<CambiarEstadoAvisoResponse>,
                response: Response<CambiarEstadoAvisoResponse>
            ) {
                if (response.isSuccessful) {
                    when (response.body()?.codigoOperacion) {
                        0 -> {
                            Toast.makeText(context, "${response.body()?.descripcion}", Toast.LENGTH_SHORT).show()
                        }
                        -1 -> {
                            Toast.makeText(context, "${response.body()?.descripcion}", Toast.LENGTH_SHORT).show()
                        }
                        6 -> {
                            //Actualizado
                            Toast.makeText(context, "${response.body()?.descripcion}", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            mensajes(context, title, "Error inesperado, marcar al soporte tecnico para más ayuda")
                        }
                    }
                } else {
                    mensajes(context, title, context.resources.getString(R.string.noneServise))
                }
            }

        })
    }

    fun borrarAviso(aviso:CambiarEstadoAviso,context: Context, title: String){
        val callR = apiEnvios.borrarAviso("text/plain",aviso)
        callR.enqueue(object :Callback<BorrarAvisoResponse>{
            override fun onFailure(call: Call<BorrarAvisoResponse>, t: Throwable) {
                mensajes(context, title, context.resources.getString(R.string.noneServise))
            }

            override fun onResponse(
                call: Call<BorrarAvisoResponse>,
                response: Response<BorrarAvisoResponse>
            ) {
                if(response.isSuccessful){
                    when (response.body()?.codigoOperacion) {
                        0 -> {
                            Toast.makeText(context, "${response.body()?.descripcion}", Toast.LENGTH_SHORT).show()
                        }
                        -1 -> {
                            Toast.makeText(context, "${response.body()?.descripcion}", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            mensajes(context, title, "Error inesperado, marcar al soporte tecnico para más ayuda")
                        }
                    }
                }else{
                    mensajes(context, title, context.resources.getString(R.string.noneServise))
                }
            }

        })
    }

    fun listaAsistencia(asistencia:ListaAsistencia,context: Context, title: String, view: View){
        val callR = apiEnvios.listaAsistencia("text/plain",asistencia)
        callR.enqueue(object :Callback<ListaAsistenciaResponse>{
            override fun onFailure(call: Call<ListaAsistenciaResponse>, t: Throwable) {
                mensajes(context, title, context.resources.getString(R.string.noneServise))
            }

            override fun onResponse(
                call: Call<ListaAsistenciaResponse>,
                response: Response<ListaAsistenciaResponse>
            ) {
                if(response.isSuccessful){
                    when (response.body()?.codigoOperacion) {
                        0->{
                            //0 exito
                            view.recyclerAsistencia.layoutManager = LinearLayoutManager(context)
                            val miAdaptador =
                                AdapterAsistencia(response.body()?.asistencias as ArrayList<Asistencia>)
                            view.recyclerAsistencia.adapter = miAdaptador

                        }
                        -1->{
                            //-1 error no controlado
                        }
                        2->{
                            // 2 error en el formato de los datps
                        }
                        3->{
                            //3 datos incorrectos
                        }
                    }
                }else{
                    mensajes(context, title, context.resources.getString(R.string.noneServise))
                }
            }

        })
    }

    fun validarAsistencia (asistencia:ListaAsistencia,context: Context, title: String){
        val callR = apiEnvios.validarAsistencia("text/plain",asistencia)
        callR.enqueue(object :Callback<ValidarAsistenciaResponse>{
            override fun onFailure(call: Call<ValidarAsistenciaResponse>, t: Throwable) {
                mensajes(context, title, context.resources.getString(R.string.noneServise))
            }

            override fun onResponse(
                call: Call<ValidarAsistenciaResponse>,
                response: Response<ValidarAsistenciaResponse>
            ) {
                if(response.isSuccessful)
                {
                    when(response.body()?.codigoOperacion){
                        0->{
                            asistenciaDelDia = response.body()?.validacion!!
                        }
                        else->{
                            Toast.makeText(context, "${response.body()?.descripcion}",Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{
                    mensajes(context, title, context.resources.getString(R.string.noneServise))
                }

            }
        })
    }

    private fun mensajes(context: Context, titulo: String, s: String) {
        val dialogoRespuesta = AlertDialog.Builder(context)

        dialogoRespuesta.setTitle(titulo)
            .setMessage(s)
            .setPositiveButton(R.string.msnOk,
                DialogInterface.OnClickListener { dialog, which ->    }) //despues del lambda -> se pone la accion
        dialogoRespuesta.create()
        dialogoRespuesta.show()

    }

}