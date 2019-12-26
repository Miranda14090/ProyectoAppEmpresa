package com.miranda.appempresarial.presentet

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Sifrado {

    fun convertirSHA256(password: String): String? {
        var md: MessageDigest? = null
        md = try {
            MessageDigest.getInstance("SHA-256")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        }
        val hash = md.digest(password.toByteArray())
        val sb = StringBuffer()
        for (b in hash) {
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }
}