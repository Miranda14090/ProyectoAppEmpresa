package com.miranda.appempresarial.view




import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.Servicios.ServiciosEntidades
import com.miranda.appempresarial.presentet.Entidades
import com.miranda.appempresarial.presentet.EntidadesImp
import com.miranda.appempresarial.view.fragments.FingerprintFragment
import com.miranda.appempresarial.view.fragments.Formulario
import com.miranda.appempresarial.view.fragments.Sesion
import io.realm.Realm
import io.realm.RealmConfiguration


class InicioDeSesion : AppCompatActivity(),
    Sesion.FormulariosListener,
    Formulario.FormulariosListener, DatabaseView
{

    var DB_KEY = "basedatos"
    var DB_CREATE = "baseCreada"
    lateinit var serviciosEntidades: ServiciosEntidades
    var firstT: SharedPreferences? = null
    var preseterDb:Entidades=EntidadesImp(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_de_sesion)

        getFirstTimeRun()

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

    private fun  getFirstTimeRun(){
        firstT = applicationContext.getSharedPreferences(DB_KEY, Context.MODE_PRIVATE)

        Realm.init(this)
        val config: RealmConfiguration = RealmConfiguration.Builder()
            .name(getString(R.string.entidades))
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)
        serviciosEntidades = ServiciosEntidades(Realm.getDefaultInstance())

        if (leerPreferencias()) {
            preseterDb.spinnerEstados(serviciosEntidades)
        } else {
            val editor = firstT!!.edit()
            editor.putString(DB_CREATE, "base de datos")
            editor.commit()
            preseterDb.createDB(serviciosEntidades)
        }
    }

    private fun leerPreferencias(): Boolean {
        val correo = firstT!!.getString(DB_CREATE, null)
        return correo != null
    }

    companion object{
        fun newInstance(): InicioDeSesion = InicioDeSesion()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun cambiarColor(primaryDark:String, primary:String){
        window.statusBarColor = Color.parseColor(primaryDark)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(primary)))
       // window.navigationBarColor = ColorDrawable //ColorDrawable.Color.parseColor(primary)

    }

}
