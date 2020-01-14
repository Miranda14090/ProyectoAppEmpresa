package com.miranda.appempresarial.presentet

import com.miranda.appempresarial.api.DatabaseEntidades
import com.miranda.appempresarial.api.DatabaseEntidadesImp
import com.miranda.appempresarial.api.Servicios.ServiciosEntidades
import com.miranda.appempresarial.view.DatabaseView

class EntidadesImp(var view: DatabaseView):Entidades {

    val datos:DatabaseEntidades = DatabaseEntidadesImp()

    override fun createDB(serviciosEntidades: ServiciosEntidades) {
        datos.createDB(serviciosEntidades)
    }
}