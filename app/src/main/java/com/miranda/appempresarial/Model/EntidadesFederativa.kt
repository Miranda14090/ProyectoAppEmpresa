package com.miranda.appempresarial.Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class EntidadesFederativa:RealmObject() {

    @PrimaryKey
    var id:Int = 0
    lateinit var entidad:String
}