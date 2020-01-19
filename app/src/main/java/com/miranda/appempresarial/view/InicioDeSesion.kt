package com.miranda.appempresarial.view




import android.app.KeyguardManager
import android.content.Context
import android.content.SharedPreferences
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.Servicios.ServiciosDatabase
import com.miranda.appempresarial.presentet.DatabasePresenter
import com.miranda.appempresarial.presentet.DatabasePresenterImp
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
    lateinit var serviciosDatabase: ServiciosDatabase
    var firstT: SharedPreferences? = null
    var preseterDb:DatabasePresenter=DatabasePresenterImp(this)


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

    override fun pedirHuella() {
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.contenedorHuella,
                FingerprintFragment(),"")
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

    private fun  getFirstTimeRun(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val fingerprintManager = this.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
            Consumo.hasFingerprintScaner = fingerprintManager!!.isHardwareDetected }

        firstT = applicationContext.getSharedPreferences(DB_KEY, Context.MODE_PRIVATE)

        Realm.init(this)
        val config: RealmConfiguration = RealmConfiguration.Builder()
            .name(getString(R.string.entidades))
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)
        serviciosDatabase = ServiciosDatabase(Realm.getDefaultInstance())
        Consumo.serviciosDataOnjet = serviciosDatabase

        if (leerPreferencias()) {
            preseterDb.spinnerEstados(serviciosDatabase)
        } else {
            val editor = firstT!!.edit()
            editor.putString(DB_CREATE, "base de datos")
            editor.commit()
            preseterDb.createDB(serviciosDatabase)
        }
    }

    private fun leerPreferencias(): Boolean {
        val correo = firstT!!.getString(DB_CREATE, null)
        return correo != null
    }

    companion object{
        fun newInstance(): InicioDeSesion = InicioDeSesion()
    }

}
