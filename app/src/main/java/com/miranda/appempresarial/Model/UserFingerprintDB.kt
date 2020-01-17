package com.miranda.appempresarial.Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserFingerprintDB:RealmObject() {

    @PrimaryKey
    var id:Int = 0
    lateinit var numeroEmpledo:String
    lateinit var password:String
}