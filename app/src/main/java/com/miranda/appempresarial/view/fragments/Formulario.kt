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
import com.miranda.appempresarial.presentet.Sifrado
import kotlinx.android.synthetic.main.fragment_formulario.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Formulario : Fragment() {

    var mCallback : Formulario.FormulariosListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            mCallback = activity as Formulario.FormulariosListener?

        }catch (e :Exception ){

        }
    }

    interface FormulariosListener {
        fun loginFinishCallback()
    }

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
            if(activity?.let { Internet.coprobarInternet(it) }!!) {

                    if (validacion()) {
                        val nombres = txtNombre.text.toString()
                        val apellido_p = txtApellidoP.text.toString()
                        val apellido_m = txtApellidoM.text.toString()
                        val entidad_f = txtEntidad.text.toString()
                        val pass = txtPass1.text.toString()
                        val fecha_de_nacimiento = txtFecha.text.toString()

                        val empleado = Sifrado.convertirSHA256(pass)?.let { it1 ->
                            Empleados(nombres, apellido_p, apellido_m, edad, fecha_de_nacimiento, entidad_f,
                                it1
                            )
                        }

                        apiEmpleados = Api_Envio.getApiEnvio().create(ApiEmpleados::class.java)
                        val callRespuesta =
                            empleado?.let { it1 ->
                                apiEmpleados.registrar_empleado("text/plain",
                                    it1
                                )
                            }

                        callRespuesta?.enqueue(object: Callback<RegistroEmpleadoResponse> {
                            override fun onFailure(call: Call<RegistroEmpleadoResponse>, t: Throwable) {
                                activity?.let { it1 -> mensaje(it1,
                                    R.string.noneServise.toString(), -1) }
                            }

                            override fun onResponse(
                                call: Call<RegistroEmpleadoResponse>,
                                response: Response<RegistroEmpleadoResponse>
                            ){
                                if (response.isSuccessful) {
                                    Log.w("Empleado", "Respuesta correcta")
                                    Log.i("Empleado", response.body().toString())
                                    val numeroDeEmpleado = response.body()?.numeroDeEmpleado;
                                    when (val codigoOperacion = response.body()?.codigoOperacion) {
                                        0 -> {
                                            activity?.let { it1 -> mensaje(it1,"Tú número de empleado es: $numeroDeEmpleado", codigoOperacion) }
                                        }
                                        1 -> {
                                            activity?.let { it1 -> mensaje(it1,"El empleado $nombres ya se encuentra registrado", codigoOperacion) }
                                        }
                                        else -> {
                                            activity?.let { it1 -> mensaje(it1,"Error inesperado, marcar al soporte para más ayuda", -1) }
                                        }
                                    }
                                } else { mensaje(activity!!, R.string.noneServise.toString(),-1) }

                            }
                        })
                    }
                }
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
    fun calendari():Int{

        var edad = 0
        val calendario = Calendar.getInstance()
        val day = calendario.get(Calendar.DAY_OF_MONTH)
        val month = calendario.get(Calendar.MONTH)
        val year = calendario.get(Calendar.YEAR)
        var mes: String
        var dia: String

        val date_p_d = DatePickerDialog(activity!!, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->

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
        }, year, month, day)
        date_p_d.show()
        return edad
    }

    fun mensaje(c:Context, txtmensaje:String, codigo:Int)
    {
        val dialogoRespuesta = AlertDialog.Builder(c)

        dialogoRespuesta.setTitle(R.string.registro)
            .setMessage(txtmensaje)
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which ->
                    if(mCallback!=null)
                    {mCallback!!.loginFinishCallback()}
                }) //despues del lambda -> se pone la accion
        dialogoRespuesta.create()
        dialogoRespuesta.show()
    }

}

