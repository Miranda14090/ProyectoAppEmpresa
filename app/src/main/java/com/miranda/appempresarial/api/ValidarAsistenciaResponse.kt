package com.miranda.appempresarial.api

import com.google.gson.annotations.SerializedName

data class ValidarAsistenciaResponse(
    @SerializedName("codigoOperacion") var codigoOperacion:Int,
    @SerializedName("descripcion") var descripcion:String,
    @SerializedName("validacion") var validacion:Boolean
)

// 0 exitoso
// -1 error no controlado
// 2 error en los datos
// 3 datos incorrectos