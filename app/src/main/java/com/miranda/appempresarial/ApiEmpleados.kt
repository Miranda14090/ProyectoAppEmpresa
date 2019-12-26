package com.miranda.appempresarial

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiEmpleados {
    //Registro
    @POST("api/bdm/app-empresarial/registrarEmpleado")
    fun registrar_empleado(@Header("Content-Type") contentType: String, @Body request: Empleados):Call<RegistroEmpleadoResponse>

    // Inicio secion
}