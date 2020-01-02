package com.miranda.appempresarial.view.fragments


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.RegistroAsistencia
import com.miranda.appempresarial.R
import com.miranda.appempresarial.presentet.Permissions
import com.miranda.appempresarial.presentet.PermissionsImp
import com.miranda.appempresarial.view.PermissionsView
import kotlinx.android.synthetic.main.fragment_asistencia.*
import java.io.ByteArrayOutputStream

/**
 * A simple [Fragment] subclass.
 */
class asistencia : Fragment(), PermissionsView {

    var cameraPermission:Permissions= PermissionsImp(this)
    lateinit var  fotoEnBase64:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asistencia, container, false)
    }
    companion object {
        fun newInstance(): asistencia =
            asistencia()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnFoto.setOnClickListener {
            var isCameraPer = cameraPermission.cameraPermission(activity!!)
            val intento = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intento,0)
        }
        btnEnviarFoto.setOnClickListener{
            btnEnviarFoto.visibility = View.INVISIBLE
            val numeroDeEmpleado = Consumo.TuNumeroDeEmpleado
            val enviarFoto = fotoEnBase64.replace("\n","")
            val asistencia=RegistroAsistencia(enviarFoto,numeroDeEmpleado)
            Consumo.registrarAsistencia(asistencia,activity!!,"Asistencia")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        imgFoto.setImageBitmap(data!!.extras!!.get("data") as Bitmap)

        val alto = 32 //640
        val ancho = 32 //480

       var imagen = imgFoto.drawable.toBitmap()

        if(imgFoto.drawable != null)
        {
            btnEnviarFoto.visibility = View.VISIBLE
        }

        imagen = Bitmap.createScaledBitmap(imagen,ancho,alto,true)

        val byteArrayOutputStream = ByteArrayOutputStream()
        imagen.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT)

    }

}
