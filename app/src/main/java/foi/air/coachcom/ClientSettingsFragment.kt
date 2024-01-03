package foi.air.coachcom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.chip.Chip

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClientSettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientSettingsFragment : Fragment() {
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
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_client_settings, container, false)

        val changePersonalInformation: Chip = rootView.findViewById(R.id.client_settings_personal)

        changePersonalInformation.setOnClickListener {
            val intent = Intent(requireContext(), ChangePersonalInformation::class.java)
            startActivity(intent)
        }

        val changePassword: Chip = rootView.findViewById(R.id.client_settings_password)

        changePassword.setOnClickListener {
            val intent = Intent(requireContext(), ChangePassword::class.java)
            startActivity(intent)
        }

        val enterPhysicalMeasurements: Chip = rootView.findViewById(R.id.client_settings_physical_measurements)

        enterPhysicalMeasurements.setOnClickListener {
            val intent = Intent(requireContext(), EnterPhysicalMeasurements::class.java)
            startActivity(intent)
        }

        val enterTargetMeasurements: Chip = rootView.findViewById(R.id.client_settings_target_measurements)

        enterTargetMeasurements.setOnClickListener {
            val intent = Intent(requireContext(), EnterTargetMeasurements::class.java)
            startActivity(intent)
        }

        val logoutButton: Button = rootView.findViewById(R.id.client_settings_logoutButton)

        logoutButton.setOnClickListener {

            val sharedPrefs = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()

            editor.clear()
            editor.apply()

            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)

        }

        return rootView

        return inflater.inflate(R.layout.fragment_client_settings, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientSettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}