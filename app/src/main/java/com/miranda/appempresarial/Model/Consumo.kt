package com.miranda.appempresarial.Model

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.ApiEmpleados
import com.miranda.appempresarial.api.Api_Envio
import com.miranda.appempresarial.api.LoginUserResponse
import com.miranda.appempresarial.api.RegistroEmpleadoResponse
import com.miranda.appempresarial.view.fragments.Formulario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Consumo {

    var mCallback : Formulario.FormulariosListener?=null

     var apiEnvios: ApiEmpleados = Api_Envio.getApiEnvio().create(ApiEmpleados::class.java)

    fun registrar_usuario(empleado:Empleado, context:Context, titulo:String):Int{
        var codigo:Int =404
        val CallRespuesta = apiEnvios.registrar_empleado("text/plain", empleado)
        CallRespuesta.enqueue(object: Callback<RegistroEmpleadoResponse> {
            override fun onFailure(call: Call<RegistroEmpleadoResponse>, t: Throwable) {
                codigo = 2
                mensajes(context, titulo, R.string.noneServise.toString())
            }

            override fun onResponse(
                call: Call<RegistroEmpleadoResponse>,
                response: Response<RegistroEmpleadoResponse>
            ){
                if (response.isSuccessful) {
                    Log.w("Empleado", "Respuesta correcta")
                    Log.i("Empleado", response.body().toString())
                    val numeroDeEmpleado = response.body()?.numeroDeEmpleado
                    when (val codigoOperacion = response.body()?.codigoOperacion) {
                        0 -> {
                            codigo = 0
                            if(mCallback!=null)
                                mCallback!!.loginFinishCallback()
                            mensajes(context,titulo,"Tú número de empleado es: $numeroDeEmpleado")
                        }
                        1 -> {
                            codigo = 1
                            mensajes(context,titulo,"El empleado ${empleado.nombres} ya se encuentra registrado")
                        }
                        else -> {
                            codigo = 2
                            mensajes(context,titulo,"Error inesperado, marcar al soporte para más ayuda")
                        }
                    }
                } else {
                    codigo = 2
                    mensajes(context, titulo, R.string.noneServise.toString())
                }

            }
        })
        return codigo
    }

    fun registrar_reporte(){

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
                   when (val codigo = response.body()?.codigoOperacion){
                       0 -> {
                           mensajes(context, titulo, "hola")
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
                    mensajes(context,titulo,"Error inesperado")
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