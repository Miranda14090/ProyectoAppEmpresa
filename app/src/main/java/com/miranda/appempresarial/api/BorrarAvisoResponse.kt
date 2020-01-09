package com.miranda.appempresarial.api

import com.google.gson.annotations.SerializedName

data class BorrarAvisoResponse(
    @SerializedName("codigoOperacion") var codigoOperacion:Int,
    @SerializedName("descripcion") var descripcion:String
)

//0 exito
//-1 error no controlado