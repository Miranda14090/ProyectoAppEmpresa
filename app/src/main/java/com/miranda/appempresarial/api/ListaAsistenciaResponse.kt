package com.miranda.appempresarial.api

import com.google.gson.annotations.SerializedName

data class ListaAsistenciaResponse(
    @SerializedName("codigoOperacion") var codigoOperacion:Int,
    @SerializedName("descripcion") var descripcion:String,
    @SerializedName("asistencias") var asistencias:List<Asistencia>
)

class Asistencia (
    @SerializedName("folio") var folio:String,
    @SerializedName("fecha") var fecha:String
)




