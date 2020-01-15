package com.miranda.appempresarial.view.fragments


import android.annotation.SuppressLint
import android.content.Context
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
import kotlinx.android.synthetic.main.fragment_sesion.*
import com.miranda.appempresarial.R.color.gris
import com.miranda.appempresarial.view.InicioDeSesion


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

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        txtLogin_usuario.setText(Consumo.TuNumeroDeEmpleado)

        boton_formulario.setOnClickListener {

            if(mCallback!=null)
                mCallback!!.registroFinishCallback()
        }

        boton_InicioSesion.setOnClickListener{
            if((activity?.let { Internet.coprobarInternet(it) }!!)) {
                if ((txtLogin_usuario.text.toString().replace(" ","") == "")) {
                    usuario.error="Campo requerido"

                }else if(txtLogin_usuario.text.toString().length != 6){
                    usuario.error="Tienen que ser 6 caracteres"
                }
                else{
                    usuario.error = null
                    if(txtLogin_pass.text.toString().replace(" ","") == ""){
                        contrasena.error="Campo requerido"
                    }
                    else{
                        usuario.error = null
                        contrasena.error = null
                        loginApp()
                        boton_InicioSesion.isEnabled = false
                        boton_InicioSesion.setBackgroundColor(gris)
                    }
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

    private fun loginApp() {
        val numeroEmpleado =txtLogin_usuario.text.toString()
        val pass_send = Sifrado.convertirSHA256(txtLogin_pass.text.toString())
        val usuario = pass_send?.let { LoginUser(it,numeroEmpleado) }
        Consumo.pedir_login(usuario!!, activity!!,"Sesion",numeroEmpleado, boton_InicioSesion)
        txtLogin_pass.setText("")
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
