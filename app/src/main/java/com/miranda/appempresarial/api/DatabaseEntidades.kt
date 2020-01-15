package com.miranda.appempresarial.api

import com.miranda.appempresarial.api.Servicios.ServiciosEntidades

interface DatabaseEntidades {
    fun createDB(serviciosEntidades: ServiciosEntidades)
    fun spinnerEstados(serviciosEntidades: ServiciosEntidades)
}