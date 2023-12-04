package foi.air.coachcom

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import foi.air.coachcom.models.UserData
import foi.air.coachcom.models.UserDataResponse
import foi.air.coachcom.network.ApiInterface
import foi.air.coachcom.network.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClientProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientProfileFragment : Fragment() {
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

        val rootView = inflater.inflate(R.layout.fragment_client_profile, container, false)

        val sharedPrefs = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)

        val username = sharedPrefs.getString("username", "") ?: ""

        val apiInterface: ApiInterface = Retrofit.apiInterface

        val call: Call<UserDataResponse> = apiInterface.getUserData(username)

        call.enqueue(object : Callback<UserDataResponse> {
            override fun onResponse(call: Call<UserDataResponse>, response: Response<UserDataResponse>) {
                // Obrada uspešnog odgovora
                if (response.isSuccessful) {
                    val responseData = response.body()
                    val data: UserDataResponse? = responseData
                    val user: UserData? = responseData?.data
                    val firstName: String? = user?.first_name
                    val lastName: String? = user?.last_name

                    val nameTextView: TextView = rootView.findViewById(R.id.client_profile_name)
                    nameTextView.text = "$firstName $lastName"

                    val name1TextView: TextView = rootView.findViewById(R.id.cilient_profile_name_view)
                    name1TextView.text = "$firstName $lastName"

                } else {
                    // Obrada neuspešnog odgovora
                    // response.errorBody() može pružiti dodatne informacije o grešci
                }
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                // Obrada greške
            }
        })

        return rootView
        return inflater.inflate(R.layout.fragment_client_profile, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}