package com.example.coachcom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationClientFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationClientFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegistrationClientFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrationClientFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}