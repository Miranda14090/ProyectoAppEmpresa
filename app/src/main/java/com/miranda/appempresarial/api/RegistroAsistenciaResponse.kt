package com.miranda.appempresarial.api

import com.google.gson.annotations.SerializedName

data class RegistroAsistenciaResponse(
    @SerializedName("codigoOperacion") var codigoOperacion:Int,
    @SerializedName("descripcion") var descripcion:String,
    @SerializedName("folio") var folio:String
) {
}
// 0 Corrrecto
// -1 Error
// 2 Formato invalido