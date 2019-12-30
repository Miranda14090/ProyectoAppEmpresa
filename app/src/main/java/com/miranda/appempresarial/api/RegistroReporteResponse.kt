package com.miranda.appempresarial.api

import com.google.gson.annotations.SerializedName

data class RegistroReporteResponse (
    @SerializedName("codigoOperacion") var codigoOperacion:Int,
    @SerializedName("descripcion") var descripcion:String,
    @SerializedName("folio") var folio:String,
    @SerializedName("Estatus") var estatus:String
){
}