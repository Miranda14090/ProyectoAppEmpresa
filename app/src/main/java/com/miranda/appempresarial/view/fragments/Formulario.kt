package com.miranda.appempresarial.view.fragments


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miranda.appempresarial.Model.Consumo
import com.miranda.appempresarial.Model.Empleado
import com.miranda.appempresarial.R
import com.miranda.appempresarial.presentet.Internet
import com.miranda.appempresarial.presentet.Sifrado
import com.miranda.appempresarial.view.InicioDeSesion
import kotlinx.android.synthetic.main.fragment_formulario.*
import kotlinx.android.synthetic.main.fragment_formulario.view.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Formulario : Fragment() {

    var mCallback : FormulariosListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            mCallback = activity as FormulariosListener?

        }catch (e :Exception ){

        }
    }

    interface FormulariosListener {
        fun loginFinishCallback()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_formulario, container, false)
    }

    companion object {
        fun newInstance(): Formulario =
            Formulario()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var edad = 0

        txtFecha.isFocusable = false
        txtFecha.setOnClickListener {

            val calendario = Calendar.getInstance()
            val day = calendario.get(Calendar.DAY_OF_MONTH)
            val month = calendario.get(Calendar.MONTH)
            val year = calendario.get(Calendar.YEAR)
            var mes: String
            var dia: String


            val datePD = DatePickerDialog(activity!!, DatePickerDialog.OnDateSetListener{ _, mYear, mMonth, mDay ->

                edad = if((mMonth-month)<0) {
                    year - mYear
                } else if ((mMonth-month)==0) {
                    if((mDay-day)<=0) {
                        year - mYear
                    } else (year - mYear) -1
                } else (year - mYear) -1

                dia = if(mDay < 10) "0${mDay}"
                else "$mDay"
                mes = if((mMonth+1)<10) "0${mMonth+1}"
                else "${mMonth+1}"

                if(edad in 18..70)
                {
                    txtFecha.setText("${dia}/${mes}/${mYear}")
                    txtFecha.error=null
                }
                else {
                    txtFecha.error="Fecha no valida"
                    txtFecha.setText("")
                    txtFecha.requestFocus()
                }
            }, (year-18), month, day)
            datePD.show()
        }

        btnFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val day = calendario.get(Calendar.DAY_OF_MONTH)
            val month = calendario.get(Calendar.MONTH)
            val year = calendario.get(Calendar.YEAR)
            var mes: String
            var dia: String


            val datePD = DatePickerDialog(activity!!, DatePickerDialog.OnDateSetListener{ _, mYear, mMonth, mDay ->

                edad = if((mMonth-month)<0) {
                    year - mYear
                } else if ((mMonth-month)==0) {
                    if((mDay-day)<=0) {
                        year - mYear
                    } else (year - mYear) -1
                } else (year - mYear) -1

                dia = if(mDay < 10) "0${mDay}"
                else "$mDay"
                mes = if((mMonth+1)<10) "0${mMonth+1}"
                else "${mMonth+1}"

                if(edad in 18..70)
                {
                    txtFecha.setText("${dia}/${mes}/${mYear}")
                    txtFecha.error=null
                }
                else {
                    txtFecha.error="Fecha no valida"
                    txtFecha.setText("")
                    txtFecha.requestFocus()
                }
            }, (year-18), month, day)
            datePD.show()
        }

        boton_cancelar.setOnClickListener {
            backFragment()
        }
        btnContinuar.setOnClickListener {
            backFragment()
        }



        boton_registrar.setOnClickListener {
            if(activity?.let { Internet.coprobarInternet(it) }!!) {

                    if (validacion()) {
                        val nombres = txtNombre.text.toString()
                        val apellidoP = txtApellidoP.text.toString()
                        val apellidoM = txtApellidoM.text.toString()
                        val entidadF = txtEntidad.text.toString()
                        val pass = txtPass1.text.toString()
                        val fechaDeNacimiento: String = txtFecha.text.toString()

                        val empleado = Sifrado.convertirSHA256(pass)?.let { it1 ->
                            Empleado(nombres, apellidoP, apellidoM, edad, fechaDeNacimiento, entidadF,
                                it1
                            )}
                         Consumo.registrar_usuario(empleado!!,activity!!, view)

                    }
                }
        }

    }

     fun backFragment() {
        if(mCallback!=null)
        {
            mCallback!!.loginFinishCallback()
     }
    }

    @SuppressLint("SimpleDateFormat")
    fun validacion(): Boolean{

        if((txtNombre.text.toString().replace(" ","") == "")|| (txtNombre.text.toString())[0] == ' '){
            txtNombre.error="No valido"
            txtNombre.requestFocus()
            return false
        }else txtNombre.error=null

        if(txtApellidoP.text.toString().replace(" ","") == "" || (txtNombre.text.toString())[0] == ' '){
            txtApellidoP.error="No valido"
            txtApellidoP.requestFocus()
            return  false
        }else txtApellidoP.error=null

        if(txtEntidad.text.toString().replace(" ","") == "" || (txtNombre.text.toString())[0] == ' '){
            txtEntidad.error="No valido"
            txtEntidad.requestFocus()
            return  false
        }else txtEntidad.error=null

        if(txtFecha.text.toString().equals("")){
            txtFecha.error="campo vacio"
            txtFecha.requestFocus()
            return  false
        }else txtFecha.error=null

        if(txtPass1.length()<8){
            contrasena.error="Minimo 8 caracteres"
            contrasena.requestFocus()
            return  false
        }else contrasena.error=null

        if(!(txtPass1.text.toString().equals(txtPass2.text.toString()))){
            Confirmarcontrasena.error="No coinciden"
            Confirmarcontrasena.requestFocus()
            return false
        }else Confirmarcontrasena.error=null
        return true
    }

    @SuppressLint("SetTextI18n")

    fun mensaje(c:Context, txtmensaje:String, codigo:Int, v:View)
    {
        val formulario:FormulariosListener = InicioDeSesion()
        if(codigo==0) {
            v.btnContinuar.visibility=View.VISIBLE
            v.boton_cancelar.visibility= View.INVISIBLE
            v.boton_registrar.visibility= View.INVISIBLE
        }
        val dialogoRespuesta = AlertDialog.Builder(c)

        dialogoRespuesta.setTitle(R.string.registro)
            .setMessage(txtmensaje)
            .setPositiveButton(R.string.msnOk,
                DialogInterface.OnClickListener { dialog, which ->
                  //  formulario.loginFinishCallback()
                }) //despues del lambda -> se pone la accion
        dialogoRespuesta.create()
        dialogoRespuesta.show()
    }

}

