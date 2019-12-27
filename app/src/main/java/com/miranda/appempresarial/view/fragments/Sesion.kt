package com.miranda.appempresarial.view.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.LoginUser
import com.miranda.appempresarial.R
import com.miranda.appempresarial.presentet.Internet
import com.miranda.appempresarial.presentet.Sifrado
import com.miranda.appempresarial.view.MainActivity
import kotlinx.android.synthetic.main.fragment_sesion.*

/**
 * A simple [Fragment] subclass.
 */
class Sesion : Fragment() {

    var mCallback : FormulariosListener?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_sesion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        boton_formulario.setOnClickListener {

            if(mCallback!=null)
                mCallback!!.registroFinishCallback()
        }

        boton_InicioSesion.setOnClickListener{
            if((activity?.let { Internet.coprobarInternet(it) }!!) && loginSuccessful()) {
                val intento1 = Intent(activity, MainActivity::class.java)
                activity?.startActivity(intento1)
            }
        }
    }

    private fun loginSuccessful(): Boolean {

        var numeroEmpleado =txtLogin_usuario.text.toString()
        var pass_send = Sifrado.convertirSHA256(txtLogin_pass.text.toString())
        var usuario = pass_send?.let { LoginUser(it,numeroEmpleado) }

        Consumo.pedir_login(usuario!!, activity!!,"Sesion")

        return false
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

}
