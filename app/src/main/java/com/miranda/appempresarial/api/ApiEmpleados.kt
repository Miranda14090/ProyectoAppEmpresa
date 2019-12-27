package com.miranda.appempresarial.api

import com.miranda.appempresarial.Model.Empleado
import com.miranda.appempresarial.Model.LoginUser
import com.miranda.appempresarial.Model.ReportesSend
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiEmpleados {
    //Registro
    @POST("api/bdm/app-empresarial/registrarEmpleado")
    fun registrar_empleado(@Header("Content-Type") contentType: String, @Body request: Empleado):Call<RegistroEmpleadoResponse>

    // Inicio sesion

    // Reportes
    @POST("api/bdm/app-empresarial/registrarReporte")
    fun registrar_reporte(@Header("Content-Type") contentType: String, @Body request:ReportesSend):Call<RegistroReporteResponse>

    @POST("api/bdm/app-empresarial/login")
    fun login_user(@Header("Content-Type") contentType: String, @Body request: LoginUser):Call<LoginUserResponse>
}