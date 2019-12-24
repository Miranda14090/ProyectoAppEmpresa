package com.miranda.appempresarial


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import kotlinx.android.synthetic.main.fragment_formulario.*
import kotlinx.android.synthetic.main.fragment_formulario.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Formulario : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_formulario, container, false)
    }

    companion object {
        fun newInstance(): Formulario = Formulario()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var edad = 0

        btnFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val day = calendario.get(Calendar.DAY_OF_MONTH)
            val month = calendario.get(Calendar.MONTH)
            val year = calendario.get(Calendar.YEAR)
            var mes = ""
            var dia = ""
            var anio = ""


            val date_p_d = DatePickerDialog(getActivity()!!, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->

                edad = if((mMonth-month)<0) {
                    year - mYear
                } else if ((mMonth-month)==0) {
                    if((mDay-day)<=0) {
                        year - mYear
                    } else (year - mYear) -1
                } else (year - mYear) -1

                dia = if(mDay < 10) "0${mDay}"
                else "${mDay}"
                mes = if((mMonth+1)<10) "0${mMonth+1}"
                else "${mMonth+1}"
                //if(mYear < 2000) anio = "${mYear-1900}"
                //else if (mYear == 2000) anio = "00"
                //else if (mYear > 2000 && mYear < 2010) anio = "0${mYear-2000}"
                //else if (mYear > 2010) anio = "${mYear-2000}"

                if(edad>=18 && edad<=70)
                {
                    txtFecha.setText("${dia}/${mes}/${mYear}")
                    txtFecha.error=null
                }
                else {
                    txtFecha.error="Edad mínima de 18"
                    txtFecha.setText("")
                    txtFecha.requestFocus()
                }
            }, year, month, day)
            date_p_d.show()
        }

        boton_registrar.setOnClickListener {
            //Toast.makeText(getActivity(), "edad ${edad}", Toast.LENGTH_LONG).show()

          if(validacion())
          {
              var nombres = txtNombre.text.toString()
              var apellido_p = txtApellidoP.text.toString()
              var apellido_m = txtApellidoM.text.toString()
              var entidad_f = txtEntidad.text.toString()
              var pass = txtPass1.text.toString()
              var fecha_de_nacimiento = txtFecha.text.toString()

              Toast.makeText(getActivity(), "Todos los datos son correctos", Toast.LENGTH_LONG).show()
          }
        }

    }

    @SuppressLint("SimpleDateFormat")
    fun validacion(): Boolean{

        if(txtNombre.text.toString().replace(" ","").equals("")){
            txtNombre.error="campo vacio"
            txtNombre.requestFocus()
            return false
        }else txtNombre.error=null

        if(txtApellidoP.text.toString().replace(" ","").equals("")){
            txtApellidoP.error="campo vacio"
            txtApellidoP.requestFocus()
            return  false
        }else txtApellidoP.error=null

        if(txtEntidad.text.toString().replace(" ","").equals("")){
            txtEntidad.error="campo vacio"
            txtEntidad.requestFocus()
            return  false
        }else txtEntidad.error=null

        if(txtFecha.text.toString().equals("")){
            txtFecha.error="campo vacio"
            txtFecha.requestFocus()
            return  false
        }else txtFecha.error=null

        if(txtPass1.length()<8){
            txtPass1.error="Minimo 8 caracteres"
            txtPass1.requestFocus()
            return  false
        }else txtPass1.error=null

        if(!(txtPass1.text.toString().equals(txtPass2.text.toString()))){
            txtPass2.error="No coinciden"
            txtPass2.requestFocus()
            return false
        }else txtPass2.error=null
        return true
    }


}

