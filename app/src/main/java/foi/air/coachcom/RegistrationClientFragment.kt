package foi.air.coachcom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class RegistrationClientFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_registration_client, container, false)

        val clientNameEditText: EditText = rootView.findViewById(R.id.client_name)
        val clientSurnameEditText: EditText = rootView.findViewById(R.id.client_surname)
        val clientAddressEditText: EditText = rootView.findViewById(R.id.client_address)
        val clientContactEditText: EditText = rootView.findViewById(R.id.client_contact)
        val clientUsernameEditText: EditText = rootView.findViewById(R.id.client_username)
        val clientPasswordEditText: EditText = rootView.findViewById(R.id.client_password)
        val clientSignUp: Button = rootView.findViewById(R.id.client_sign_up_button)

        clientSignUp.setOnClickListener {
            val clientName = clientNameEditText.text.toString()
            val clientSurname = clientSurnameEditText.text.toString()
            val clientAddress = clientAddressEditText.text.toString()
            val clientContact = clientContactEditText.text.toString()
            val clientUsername = clientUsernameEditText.text.toString()
            val clientPassword = clientPasswordEditText.text.toString()

        }


        return rootView
    }

}