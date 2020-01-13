package com.miranda.appempresarial.view.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.LoginUser
import com.miranda.appempresarial.R
import com.miranda.appempresarial.presentet.Internet
import com.miranda.appempresarial.presentet.Sifrado
import com.miranda.appempresarial.view.MainActivity
import kotlinx.android.synthetic.main.fragment_sesion.*
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences





/**
 * A simple [Fragment] subclass.
 */
class Sesion : Fragment() {

    var mCallback : FormulariosListener?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.miranda.appempresarial.R.layout.fragment_sesion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        txtLogin_usuario.setText(Consumo.TuNumeroDeEmpleado)

        boton_formulario.setOnClickListener {

            if(mCallback!=null)
                mCallback!!.registroFinishCallback()
        }

        boton_InicioSesion.setOnClickListener{
            if((activity?.let { Internet.coprobarInternet(it) }!!)) {
                if ((txtLogin_usuario.text.toString().replace(" ","") == "")) {
                    txtLogin_usuario.error="El esta vacío"

                }else
                {
                    loginApp()
                }
            }
        }

        btnswitch.setOnClickListener {
            if(btnswitch.isChecked){
                /*guardarValor("user",<nombre usuario>)
                val usuario = leerValor("user")*/

            }else{

            }
        }
    }

   /* private val PREFS_KEY = "mispreferencias"

    fun guardarValor(context: Context, keyPref: String, valor: String) {
        val settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE)
        val editor: SharedPreferences.Editor
        editor = settings.edit()
        editor.putString(keyPref, mostrar)
        editor.commit()
    }

    fun leerValor(context: Context, keyPref: String): String {
        val preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE)
        return preferences.getString(keyPref, "")
    }*/

    private fun loginApp() {

        val numeroEmpleado =txtLogin_usuario.text.toString()
        val pass_send = Sifrado.convertirSHA256(txtLogin_pass.text.toString())
        val usuario = pass_send?.let { LoginUser(it,numeroEmpleado) }

        Consumo.pedir_login(usuario!!, activity!!,"Sesion",numeroEmpleado)

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            mCallback = activity as FormulariosListener?

        }catch (e :Exception ){

        }
    }

    interface FormulariosListener{
        fun registroFinishCallback()
    }
    companion object {
        fun newInstance(): Sesion =
            Sesion()
    }

}
