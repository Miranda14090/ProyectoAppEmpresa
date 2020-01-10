package com.miranda.appempresarial.view.fragments


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.ListaAsistencia
import com.miranda.appempresarial.Model.RegistroAsistencia
import com.miranda.appempresarial.R
import com.miranda.appempresarial.presentet.Permissions
import com.miranda.appempresarial.presentet.PermissionsImp
import com.miranda.appempresarial.view.PermissionsView
import kotlinx.android.synthetic.main.fragment_asistencia.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream

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

        var isCameraPer = cameraPermission.cameraPermission(activity!!)
        val asistencia = ListaAsistencia(Consumo.TuNumeroDeEmpleado)
        Consumo.validarAsistencia(asistencia,activity!!,"Asistencia")

        if(Consumo.asistenciaDelDia){
            btnFoto.isEnabled = false
            Toast.makeText(activity!!, "Ya registraste la asistencia hoy",Toast.LENGTH_LONG).show()
        }

        btnFoto.setOnClickListener {
            isCameraPer = cameraPermission.cameraPermission(activity!!)
            if(isCameraPer) {
                val option =
                    arrayOf<CharSequence>("Tomar foto", "Elegir de galeria", "Cancelar")
                val builder =
                    AlertDialog.Builder(activity)
                builder.setTitle("Eleige una opción")
                builder.setItems(option) { dialog, which ->
                    when {
                        option[which] === "Tomar foto" -> {
                            val intento = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intento, 100)
                        }
                        option[which] === "Elegir de galeria" -> {
                            val intent = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )
                            intent.type = "image/*"
                            startActivityForResult(
                                Intent.createChooser(
                                    intent,
                                    "Selecciona app de imagen"
                                ), 200
                            )
                        }
                        else -> {
                            dialog.dismiss()
                        }
                    }
                }

                builder.show()
            }
            else{ Toast.makeText(activity,"Son necearios los permisos de camara, revise que estén activos", Toast.LENGTH_LONG).show()}
        }

        btnEnviarFoto.setOnClickListener{
            btnEnviarFoto.visibility = View.INVISIBLE
            val numeroDeEmpleado = Consumo.TuNumeroDeEmpleado
            val enviarFoto = fotoEnBase64.replace("\n","")
            val asistencia=RegistroAsistencia(enviarFoto,numeroDeEmpleado)
            Consumo.registrarAsistencia(asistencia,activity!!,"Asistencia")
            imgFoto.setImageBitmap(null)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val alto = 640
        val ancho = 480

        if(data!=null) {

            when (requestCode) {
                100 -> {
                    if (resultCode == Activity.RESULT_OK)
                        imgFoto.setImageBitmap(data!!.extras!!.get("data") as Bitmap)
                }
                200 -> {
                    var path = data!!.data
                    val selectedPath = path!!.path
                    if (selectedPath != null) {
                        var imageStream: InputStream? = null
                        try {
                            imageStream = activity!!.contentResolver.openInputStream(
                                path
                            )
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }
                        val bmp = BitmapFactory.decodeStream(imageStream)
                        imgFoto.setImageBitmap(bmp)
                    }
                }
            }
            var imagen = imgFoto.drawable.toBitmap()

            if (imgFoto.drawable != null) {
                btnEnviarFoto.visibility = View.VISIBLE
            }
            imagen = Bitmap.createScaledBitmap(imagen, ancho, alto, true)


            val byteArrayOutputStream = ByteArrayOutputStream()
            imagen.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
        }
    }
}
