package com.miranda.appempresarial.api

import com.miranda.appempresarial.Model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiEmpleados {
    //Registro
    @POST("app/api/bdm/app-empresarial/registrarEmpleado")
    fun registrar_empleado(@Header("Content-Type") contentType: String, @Body request: Empleado):Call<RegistroEmpleadoResponse>

    // Reportes
    @POST("app/api/bdm/app-empresarial/registrarReporte")
    fun registrar_reporte(@Header("Content-Type") contentType: String, @Body request:ReportesSend):Call<RegistroReporteResponse>

    // Inicio sesion
    @POST("app/api/bdm/app-empresarial/login")
    fun login_user(@Header("Content-Type") contentType: String, @Body request: LoginUser):Call<LoginUserResponse>

    @POST("app/api/bdm/app-empresarial/consultarReportes")
    fun consultar_reportes(@Header("Content-Type") contentType: String, @Body request:InboxReport):Call<ConsultarReportesResponse>

    @POST("app/api/bdm/app-empresarial/registrarAsistencia")
    fun registrar_asistencia(@Header("Content-Type") contentType: String,@Body request: RegistroAsistencia):Call<RegistroAsistenciaResponse>

    @POST("app/api/bdm/app-empresarial/consultarAvisos")
    fun registrar_avisos(@Header("Content-Type") contentType: String,@Body request: RegistroAviso):Call<RegistroAvisoResponse>

    @POST("app/api/bdm/app-empresarial/informacionEmpleado")
    fun pedirInformacionEmpleado(@Header("Content-Type") contentType: String,@Body request:InfoEmpleado):Call<InfoEmpleadoResponse>

    @POST("app/api/bdm/app-empresarial/cambiarEstado")
    fun cambiarEstadoAviso(@Header("Content-Type") contentType: String,@Body request:CambiarEstadoAviso):Call<CambiarEstadoAvisoResponse>

    @POST("app/api/bdm/app-empresarial/borrarAviso")
    fun borrarAviso(@Header("Content-Type") contentType: String,@Body request:CambiarEstadoAviso):Call<BorrarAvisoResponse>

    @POST("app/api/bdm/app-empresarial/consultarAsistencias")
    fun listaAsistencia(@Header("Content-Type") contentType: String,@Body request:ListaAsistencia):Call<ListaAsistenciaResponse>

    @POST("app/api/bdm/app-empresarial/validarAsistencia")
    fun validarAsistencia(@Header("Content-Type") contentType: String,@Body request:ListaAsistencia):Call<ValidarAsistenciaResponse>
}