package com.miranda.appempresarial.api.Servicios

import com.miranda.appempresarial.Model.EntidadesFederativa
import io.realm.Realm

class ServiciosEntidades(val realm:Realm) {

    fun obtenerEntidades():List<EntidadesFederativa>{
        var entidad = realm.where(EntidadesFederativa::class.java).findAll()
        return entidad
    }
    fun obtenerUltimoId():Int{
        val id = realm.where(EntidadesFederativa::class.java).max("id")
        val ultimoId = if(id == null)1 else (id.toInt()+1)
        return ultimoId
    }
    fun crearEntidad(id:Int,entidad:String){
        realm.beginTransaction()
        val estado:EntidadesFederativa = realm.createObject(EntidadesFederativa::class.java,id)
        estado.entidad = entidad
        realm.commitTransaction()
    }
    fun obtenerEntidadPorId(id:Int):EntidadesFederativa?{
        var entidad = realm.where(EntidadesFederativa::class.java).equalTo("id",id).findFirst()
        return entidad
    }
    fun eliminarEntidad(id:Int){
        val entidad = obtenerEntidadPorId(id)
        if(entidad!=null){
            realm.beginTransaction()
            entidad.deleteFromRealm()
            realm.commitTransaction()
        }
    }
}