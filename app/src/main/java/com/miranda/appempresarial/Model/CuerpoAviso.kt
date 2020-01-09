package com.miranda.appempresarial.Model


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miranda.appempresarial.R
import com.miranda.appempresarial.api.FragmentoListener

/**
 * A simple [Fragment] subclass.
 */
open class CuerpoAviso : Fragment() {

    var mCallBack:FragmentoListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            mCallBack=activity as FragmentoListener?
        }catch (e :Exception){}
    }


    interface FragmentoListener {
        fun cambiarFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cuerpo_aviso, container, false)
    }

    companion object {
        //init{
        fun newInstance(): CuerpoAviso = CuerpoAviso()
       // }

    }


}
