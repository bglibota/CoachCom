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


class ClientSettingsFragment : Fragment() {

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

}