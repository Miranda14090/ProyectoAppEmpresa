package com.miranda.appempresarial.api

import com.google.gson.annotations.SerializedName

data class CambiarEstadoAvisoResponse(
    @SerializedName("codigoOperacion") var codigoOperacion:Int,
    @SerializedName("descripcion") var descripcion:String
)

//0 Exitosa
//-1 Error no controlado
//6 Estatus de aviso actualizado