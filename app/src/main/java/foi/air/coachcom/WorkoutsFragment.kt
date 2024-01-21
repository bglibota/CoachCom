package foi.air.coachcom

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import foi.air.plan.Plan


class WorkoutsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_workouts, container, false)

        val planOpen: ImageView = rootView.findViewById(R.id.plan_adding)

        planOpen.setOnClickListener{

            val intent = Intent(requireContext(), Plan::class.java)
            startActivity(intent)

        }

        return rootView
        return inflater.inflate(R.layout.fragment_workouts, container, false)
    }

}