package com.miranda.appempresarial.Model


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miranda.appempresarial.R

/**
 * A simple [Fragment] subclass.
 */
class CuerpoAviso : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cuerpo_aviso, container, false)
    }
    companion object {
        fun newInstance(): CuerpoAviso = CuerpoAviso()
    }

}
