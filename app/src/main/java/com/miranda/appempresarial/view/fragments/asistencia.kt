package com.miranda.appempresarial.view.fragments


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var isCameraPer = cameraPermission.cameraPermission(activity!!)
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        imgFoto.setImageBitmap(data!!.extras!!.get("data") as Bitmap)

        val imagen = imgFoto.drawable.toBitmap()

        val byteArrayOutputStream = ByteArrayOutputStream()
        imagen.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT)

    }

}
