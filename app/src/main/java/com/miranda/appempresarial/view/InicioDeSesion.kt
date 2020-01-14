package com.miranda.appempresarial.view




import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.miranda.appempresarial.R
import com.miranda.appempresarial.presentet.Permissions
import com.miranda.appempresarial.presentet.PermissionsImp
import com.miranda.appempresarial.view.fragments.FingerprintFragment
import com.miranda.appempresarial.view.fragments.Formulario
import com.miranda.appempresarial.view.fragments.Sesion


class InicioDeSesion : AppCompatActivity(),
    Sesion.FormulariosListener, Formulario.FormulariosListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_de_sesion)

        title="Talento Azteca"

        if(savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.contenedorSesion,
                    Sesion(),"")
                .commit()

        //PedirHuella()
    }
    override fun registroFinishCallback() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.contenedorSesion,
                Formulario(),"")
            .addToBackStack(null)
            .commit()
    }

    override fun loginFinishCallback(){
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.contenedorSesion,
                Sesion()  ,"")
            .commit()
    }

    fun PedirHuella(){
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.contenedorHuella,
                FingerprintFragment(),"")
            .addToBackStack(null)
            .commit()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun cambiarColor(primaryDark:String, primary:String){
        window.statusBarColor = Color.parseColor(primaryDark)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(primary)))
       // window.navigationBarColor = ColorDrawable //ColorDrawable.Color.parseColor(primary)

    }

}
