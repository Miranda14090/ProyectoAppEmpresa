package com.miranda.appempresarial.api


import android.util.Log
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.EntidadesFederativa
import com.miranda.appempresarial.api.Servicios.ServiciosEntidades


class DatabaseEntidadesImp:DatabaseEntidades {

    val entidases = arrayOf("Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "CDMX",
        "Chiapas", "Chihuahua", "Coahuila", "Colima", "Durango", "Guanajuato", "Guerrero", "Hidalgo", "Jalisco", "México",
        "Michoacán", "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí",
        "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas")

    lateinit var entidadesList: EntidadesFederativa

    override fun createDB(serviciosEntidades: ServiciosEntidades)
    {
        val serviEnt = serviciosEntidades
        for (i in entidases.indices)
        {
            var ultimoId = serviEnt.obtenerUltimoId()
            serviEnt.crearEntidad(ultimoId,this.entidases[i])
        }
       /* val entidadesList = serviEnt.obtenerEntidades()
        entidadesList.forEach {
            Log.d("DB", "id de entidad: ${it.id}  Entidad: ${it.entidad}\n")
        }*/
    }

    override fun spinnerEstados(serviciosEntidades: ServiciosEntidades) {
        val serviEnt = serviciosEntidades
        for (i in 1..32){
            entidadesList = serviEnt.obtenerEntidadPorId(i)!!
            Consumo.entidades[i-1] = entidadesList.entidad
        }
    }

}