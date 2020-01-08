package com.miranda.appempresarial.api

import com.google.gson.annotations.SerializedName

data class RegistroAvisoResponse (
    @SerializedName("codigoOperacion") var codigoDeOperacion:Int,
    @SerializedName("descripcion") var descripcion:String,
    @SerializedName("avisos") var avisos:List<ListaDeAvisos>
) {
}

class ListaDeAvisos(
    @SerializedName("identificador") var identificador:String,
    @SerializedName("titulo") var titulo:String,
    @SerializedName("cuerpo") var cuerpo:String,
    @SerializedName("estatus") var estatus:Boolean,
    @SerializedName("emision") var emision:String,
    @SerializedName("lectura") var lectura:String

){}