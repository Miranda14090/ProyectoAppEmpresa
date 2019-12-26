package com.miranda.appempresarial.view.fragments


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miranda.appempresarial.Model.Empleados
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.RegistroEmpleadoResponse
import com.miranda.appempresarial.api.ApiEmpleados
import com.miranda.appempresarial.api.Api_Envio
import com.miranda.appempresarial.presentet.Internet
import kotlinx.android.synthetic.main.fragment_formulario.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Formulario : Fragment() {

    lateinit var apiEmpleados: ApiEmpleados

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

        btnFecha.setOnClickListener {
            edad = calendari()
        }
        fecha.setOnClickListener {
            edad = calendari()
        }

        boton_registrar.setOnClickListener {
            if(getActivity()?.let { Internet.coprobarInternet(it) }!!) {

                    if (validacion()) {
                        val nombres = txtNombre.text.toString()
                        val apellido_p = txtApellidoP.text.toString()
                        val apellido_m = txtApellidoM.text.toString()
                        val entidad_f = txtEntidad.text.toString()
                        val pass = txtPass1.text.toString()
                        val fecha_de_nacimiento = txtFecha.text.toString()

                        val empleado = Empleados(nombres, apellido_p, apellido_m, edad, fecha_de_nacimiento, entidad_f, pass)

                        apiEmpleados = Api_Envio.getApiEnvio().create(ApiEmpleados::class.java)
                        var callRespuesta = apiEmpleados.registrar_empleado("text/plain", empleado)

                        callRespuesta.enqueue(object: Callback<RegistroEmpleadoResponse> {
                            override fun onFailure(call: Call<RegistroEmpleadoResponse>, t: Throwable) {
                                Log.w("Empleado", "Fallo la llamada")
                            }

                            override fun onResponse(
                                call: Call<RegistroEmpleadoResponse>,
                                response: Response<RegistroEmpleadoResponse>
                             ){
                                if (response.isSuccessful) {
                                    Log.w("Empleado", "Respuesta correcta")
                                    Log.i("Empleado", response.body().toString())
                                    var numeroDeEmpleado = response.body()?.numeroDeEmpleado;
                                    var codigoOperacion = response.body()?.codigoOperacion;
                                    when (codigoOperacion) {
                                        0 -> {
                                            getActivity()?.let { it1 -> mensaje(it1,"Tú número de empleado es: ${numeroDeEmpleado}") }
                                        }
                                        1 -> {
                                            getActivity()?.let { it1 -> mensaje(it1,"El empleado ${nombres} ya se encuentra registrado") }
                                        }
                                        else -> {
                                            getActivity()?.let { it1 -> mensaje(it1,"Error inesperado, marcar al soporte para más ayuda") }
                                        }
                                    }
                                } else {Log.w("Empleado", "Respuesta erronea")}

                            }
                         })
                    }
                }
        }

    }


    @SuppressLint("SimpleDateFormat")
    fun validacion(): Boolean{

        if((txtNombre.text.toString().replace(" ","").equals(""))||(txtNombre.text.toString()).get(0).equals(' ') ){
            txtNombre.error="No valido"
            txtNombre.requestFocus()
            return false
        }else txtNombre.error=null

        if(txtApellidoP.text.toString().replace(" ","").equals("")||(txtNombre.text.toString()).get(0).equals(' ')){
            txtApellidoP.error="No valido"
            txtApellidoP.requestFocus()
            return  false
        }else txtApellidoP.error=null

        if(txtEntidad.text.toString().replace(" ","").equals("")||(txtNombre.text.toString()).get(0).equals(' ')){
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

    fun calendari():Int{

        var edad = 0
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
                txtFecha.error="Fecha no valida"
                txtFecha.setText("")
                txtFecha.requestFocus()
            }
        }, year, month, day)
        date_p_d.show()
        return edad
    }

    fun mensaje(c:Context, txtmensaje:String)
    {
        val dialogoRespuesta = AlertDialog.Builder(c)

        dialogoRespuesta.setTitle(R.string.registro)
            .setMessage(txtmensaje)
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which ->  }) //despues del lambda -> se pone la accion
        dialogoRespuesta.create()
        dialogoRespuesta.show()
    }
}

