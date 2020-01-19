package com.miranda.appempresarial.presentet

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.security.KeyStore
import javax.crypto.Cipher
import android.annotation.TargetApi
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.FingerprintHandler
import com.miranda.appempresarial.R
import com.miranda.appempresarial.view.PermissionsView
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

class PermissionsImp(var view: PermissionsView):Permissions {

    private var fingerprintManager: FingerprintManager? = null
    private var keyguardManager: KeyguardManager? = null
    private var keyStore: KeyStore? = null
    private var cipher: Cipher? = null
    private val KEY_NAME = "AndroidKey"

    override fun cameraPermission(c: Context): Boolean {
        var version = Build.VERSION.SDK_INT

        if (version >= Build.VERSION_CODES.M) {
            return if (ContextCompat.checkSelfPermission(
                    c,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(c as Activity, arrayOf(Manifest.permission.CAMERA), 1000)
                false
            } else {
                true
            }
        } else {
            Toast.makeText(c, "No es necesario pedir permisos", Toast.LENGTH_SHORT)
                .show()
            return true
        }
    }

    override fun permisosFingerprint(c: Context): Boolean {

        val txtMensajes =
            (c as Activity).findViewById<View>(R.id.txtMensajesFingerprint) as TextView

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager = c.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
            keyguardManager = c.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

            if (!fingerprintManager!!.isHardwareDetected) {
                txtMensajes.text = "Escáner de huellas digitales no detectado en el dispositivo"
            }else if (ContextCompat.checkSelfPermission(c, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED
            ) {
                txtMensajes.text = "No hay permiso de usar el escáner de huellas digitales"
            } else if (!keyguardManager!!.isKeyguardSecure) {
                txtMensajes.text = "Agregar bloqueo a su teléfono en la configuración"
            } else if (!fingerprintManager!!.hasEnrolledFingerprints()) {
                txtMensajes.text = "Debe agregar al menos 1 huella digital para usar esta función"
            } else {
                txtMensajes.text = "Coloque su dedo en el escáner."
                generateKey()
                if (cipherInit()) {
                    val cryptoObject =
                        FingerprintManager.CryptoObject(cipher!!)
                    val fingerprintHandler = FingerprintHandler(c)
                    fingerprintHandler.startAuth(fingerprintManager!!, cryptoObject)
                }
            }
        }

        return false
    }


    @TargetApi(Build.VERSION_CODES.M)
    private fun generateKey() {
        val any = try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            val keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                "AndroidKeyStore"
            )
            keyStore.run {
                this!!.load(null)
            }
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or
                            KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                        KeyProperties.ENCRYPTION_PADDING_PKCS7
                    )
                    .build()
            )
            keyGenerator.generateKey()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: InvalidAlgorithmParameterException) {
            e.printStackTrace()
        } catch (e: NoSuchProviderException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @TargetApi(Build.VERSION_CODES.M)
    fun cipherInit(): Boolean {
        cipher = try {
            Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                keyStore!!.load(null)
                val key = keyStore!!.getKey(
                    KEY_NAME,
                    null
                ) as SecretKey
                cipher.run {
                    this!!.init(Cipher.ENCRYPT_MODE, key)
                }
                true
            } catch (@SuppressLint("NewApi") e: KeyPermanentlyInvalidatedException) {
                false
            } catch (e: KeyStoreException) {
                throw RuntimeException("Failed to init Cipher", e)
            } catch (e: CertificateException) {
                throw RuntimeException("Failed to init Cipher", e)
            } catch (e: UnrecoverableKeyException) {
                throw RuntimeException("Failed to init Cipher", e)
            } catch (e: IOException) {
                throw RuntimeException("Failed to init Cipher", e)
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException("Failed to init Cipher", e)
            } catch (e: InvalidKeyException) {
                throw RuntimeException("Failed to init Cipher", e)
            }
        } else {
            TODO("VERSION.SDK_INT < M")
        }
    }
}