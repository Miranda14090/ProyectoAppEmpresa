package com.miranda.appempresarial.api

import com.google.gson.annotations.SerializedName

data class LoginUserResponse(
    @SerializedName("codigoOperacion") var codigoOperacion:Int,
    @SerializedName("descripcion") var descripcion:String
) {
}