package foi.air.coachcom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment

class RegistrationFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val rootView = inflater.inflate(R.layout.fragment_registration, container, false)

        val clientSignUp: Button = rootView.findViewById(R.id.client_sign_up)

        clientSignUp.setOnClickListener {

            val clientRegisterDialog = RegistrationClientFragment()
            clientRegisterDialog.show(childFragmentManager, "clientRegisterDialog")
        }


        return rootView

        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

}