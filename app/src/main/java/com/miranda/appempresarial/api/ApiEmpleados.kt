package com.miranda.appempresarial.api

import com.miranda.appempresarial.Model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiEmpleados {
    //Registro
    @POST("api/bdm/app-empresarial/registrarEmpleado")
    fun registrar_empleado(@Header("Content-Type") contentType: String, @Body request: Empleado):Call<RegistroEmpleadoResponse>

    // Reportes
    @POST("api/bdm/app-empresarial/registrarReporte")
    fun registrar_reporte(@Header("Content-Type") contentType: String, @Body request:ReportesSend):Call<RegistroReporteResponse>

    // Inicio sesion
    @POST("api/bdm/app-empresarial/login")
    fun login_user(@Header("Content-Type") contentType: String, @Body request: LoginUser):Call<LoginUserResponse>

    @POST("api/bdm/app-empresarial/consultarReportes")
    fun consultar_reportes(@Header("Content-Type") contentType: String, @Body request:InboxReport):Call<ConsultarReportesResponse>

    @POST("api/bdm/app-empresarial/registrarAsistencia")
    fun registrar_asistencia(@Header("Content-Type") contentType: String,@Body request: RegistroAsistencia):Call<RegistroAsistenciaResponse>

    @POST("api/bdm/app-empresarial/consultarAvisos")
    fun registrar_avisos(@Header("Content-Type") contentType: String,@Body request: RegistroAviso):Call<RegistroAvisoResponse>
}