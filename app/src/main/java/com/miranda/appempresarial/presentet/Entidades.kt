package com.miranda.appempresarial.presentet

import com.miranda.appempresarial.api.Servicios.ServiciosEntidades

interface Entidades {
    fun createDB(serviciosEntidades: ServiciosEntidades)
    fun spinnerEstados(serviciosEntidades: ServiciosEntidades)
}