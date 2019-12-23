package com.miranda.appempresarial


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import kotlinx.android.synthetic.main.fragment_formulario.*
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
        btnFecha.setOnClickListener {
            var calendario = Calendar.getInstance()
            var day = calendario.get(Calendar.DAY_OF_MONTH)
            var month = calendario.get(Calendar.MONTH)
            var year = calendario.get(Calendar.YEAR)

            var date_p_d = DatePickerDialog(getActivity()!!, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                txtFecha.setText(" ${mDay} / ${mMonth+1} / ${mYear} ")
            }, year, month, day)
            date_p_d.show()
        }

        boton_registrar.setOnClickListener {

          if(validacion())
          {
              Toast.makeText(getActivity(), "Todos los datos son correctos", Toast.LENGTH_LONG).show()

              //TODO hacer el consumo aqui
          }
        }

    }

    fun validacion(): Boolean{

        if(txtNombre.text.toString().equals("")){
            txtNombre.error="campo vacio"
            txtNombre.requestFocus()
            return false
        }else txtNombre.error=null

        if(txtApellidoP.text.toString().equals("")){
            txtApellidoP.error="campo vacio"
            txtApellidoP.requestFocus()
            return  false
        }else txtApellidoP.error=null

        if(txtEntidad.text.toString().equals("")){
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

