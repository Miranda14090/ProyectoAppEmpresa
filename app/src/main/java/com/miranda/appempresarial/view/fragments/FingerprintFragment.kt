package com.miranda.appempresarial.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.miranda.appempresarial.R
import com.miranda.appempresarial.presentet.Permissions
import com.miranda.appempresarial.presentet.PermissionsImp
import com.miranda.appempresarial.view.InicioDeSesion
import com.miranda.appempresarial.view.PermissionsView

/**
 * A simple [Fragment] subclass.
 */
class FingerprintFragment : Fragment(), PermissionsView {

    var fingerprintPermission:Permissions= PermissionsImp(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fingerprint, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fingerprintPermission.permisosFingerprint(activity!!)
    }


}
