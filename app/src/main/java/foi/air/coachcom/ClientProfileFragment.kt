package foi.air.coachcom

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import de.hdodenhof.circleimageview.CircleImageView
import foi.air.core.models.ImageData
import foi.air.core.models.PhysicalMeasurements
import foi.air.core.models.TargetMeasurement
import handlers.ClientProfileHandler
import handlers.DefaultClientProfileHandler




class ClientProfileFragment : Fragment() {

    private val clientProfileHandler: ClientProfileHandler = DefaultClientProfileHandler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_client_profile, container, false)

        val sharedPrefs = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)

        val userId = sharedPrefs.getInt("user_id", 0)

        clientProfileHandler.getUserData(userId) { success, user, error ->
            if (success) {
                val firstName: String? = user?.first_name
                val lastName: String? = user?.last_name
                val email: String? = user?.e_mail
                val phone: String? = user?. phone_number
                val residence: String? = user?.place_of_residence
                val sex: String? = user?.sex
                val profilePicture: ImageData? = user?.picture
                val formattedBirth: String? = user?.formatted_birthdate

                val nameTextView: TextView = rootView.findViewById(R.id.client_profile_name)
                nameTextView.text = "$firstName $lastName"

                val clientImageView : CircleImageView = rootView.findViewById(R.id.client_profile_image)
                val drawableUser = R.drawable.user

                if (profilePicture?.data != null) {
                    val byteArray = profilePicture.data.toUByteArray().toByteArray()

                    Glide.with(this@ClientProfileFragment)
                        .load(byteArray)
                        .into(clientImageView)
                } else {
                    clientImageView.setImageResource(drawableUser)
                }


                val name1TextView: TextView = rootView.findViewById(R.id.client_profile_name_view)
                name1TextView.text = "$firstName $lastName"

                val emailTextView: TextView = rootView.findViewById(R.id.client_profile_email)
                emailTextView.text = email

                val birthdayTextView : TextView = rootView.findViewById(R.id.client_profile_birthday)
                birthdayTextView.text = formattedBirth

                val phoneTextView : TextView = rootView.findViewById(R.id.client_profile_phone)
                phoneTextView.text = phone

                val residenceTextView : TextView = rootView.findViewById(R.id.client_profile_place)
                residenceTextView.text = residence

                val sexTextView : TextView = rootView.findViewById(R.id.client_profile_sex)
                sexTextView.text = sex
            } else {
                Log.e("ClientProfileFragment", "Error getting user data: $error")
            }
        }

        clientProfileHandler.getMeasurementData(userId) { success, measurements, error ->
            if (success) {
                val targetMeasurements: List<TargetMeasurement>? = measurements?.data?.target_measurements
                val physicalMeasurements: List<PhysicalMeasurements>? = measurements?.data?.physical_measurements

                val firstTargetMeasurement: TargetMeasurement? = targetMeasurements?.firstOrNull()
                val height: Float? = firstTargetMeasurement?.height ?: 0f
                val heightTextView : TextView = rootView.findViewById(R.id.client_profile_height)
                heightTextView.text = height.toString()

                val weightChart: BarChart = rootView.findViewById(R.id.chart_weight)
                setupChart(weightChart)
                setWeightChartData(targetMeasurements, physicalMeasurements, weightChart)

                val waistChart: BarChart = rootView.findViewById(R.id.chart_waist)
                setupChart(waistChart)
                setWaistChartData(targetMeasurements, physicalMeasurements, waistChart)

                val chestChart: BarChart = rootView.findViewById(R.id.chart_chest)
                setupChart(chestChart)
                setChestChartData(targetMeasurements, physicalMeasurements, chestChart)

                val armChart: BarChart = rootView.findViewById(R.id.chart_arm)
                setupChart(armChart)
                setArmChartData(targetMeasurements, physicalMeasurements, armChart)

                val legChart: BarChart = rootView.findViewById(R.id.chart_leg)
                setupChart(legChart)
                setLegChartData(targetMeasurements, physicalMeasurements, legChart)

                val hipChart: BarChart = rootView.findViewById(R.id.chart_hip)
                setupChart(hipChart)
                setHipChartData(targetMeasurements, physicalMeasurements, hipChart)
            } else {
                Log.e("ClientProfileFragment", "Error getting measurement data: $error")
            }
        }

        return rootView
        return inflater.inflate(R.layout.fragment_client_profile, container, false)
    }


    private fun setupChart(chart: BarChart) {

        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)

        val xAxis: XAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f

        val leftAxis: YAxis = chart.axisLeft
        leftAxis.setDrawGridLines(true)

        val rightAxis: YAxis = chart.axisRight
        rightAxis.isEnabled = false

        val legend: Legend = chart.legend
        legend.form = Legend.LegendForm.SQUARE
        legend.textSize = 14f
        legend.xEntrySpace = 20f
        legend.isWordWrapEnabled = true
    }

    private fun setWeightChartData(targetMeasurements: List<TargetMeasurement>?, physicalMeasurements: List<PhysicalMeasurements>?, chart: BarChart) {
        val weightEntries: List<BarEntry> = physicalMeasurements?.mapIndexed { index, measurement ->
            BarEntry((index + 1).toFloat(), measurement.weight.toFloat(), measurement)
        }?.takeIf { it.isNotEmpty() } ?: listOf(BarEntry(1f, 0f))

        val targetEntry: List<BarEntry> = targetMeasurements?.mapIndexed { index, targetMeasurement ->
            BarEntry((index + 1 + (physicalMeasurements?.size ?: 0)).toFloat(), targetMeasurement.target_weight.toFloat(), targetMeasurement)
        }?.takeIf { it.isNotEmpty() } ?: listOf(BarEntry(1f, 0f))

        val allEntries = weightEntries + targetEntry

        val dataSet1 = BarDataSet(allEntries.filter { it.data is PhysicalMeasurements }, "Actual weight data")
        dataSet1.color = Color.parseColor("#9AC0CD")

        val dataSet2 = BarDataSet(allEntries.filter { it.data is TargetMeasurement }, "Target weight")
        dataSet2.color = Color.parseColor("#8B4513")

        val barData = BarData(dataSet1, dataSet2)

        chart.data = barData

        val xAxis: XAxis = chart.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val date = allEntries.getOrNull(value.toInt() - 1)?.data?.let {
                    when (it) {
                        is PhysicalMeasurements -> it.formatted_date
                        is TargetMeasurement -> it.formatted_date
                        else -> null
                    }
                }
                return date ?: ""
            }
        }

        chart.invalidate()
    }

    private fun setWaistChartData(targetMeasurements: List<TargetMeasurement>?, physicalMeasurements: List<PhysicalMeasurements>?, chart: BarChart) {
        val waistEntries: List<BarEntry> = physicalMeasurements?.mapIndexed { index, measurement ->
            BarEntry((index + 1).toFloat(), measurement.waist_circumference.toFloat(), measurement)
        }?.takeIf { it.isNotEmpty() } ?: listOf(BarEntry(1f, 0f))

        val targetEntry: List<BarEntry> = targetMeasurements?.mapIndexed { index, targetMeasurement ->
            BarEntry((index + 1 + (physicalMeasurements?.size ?: 0)).toFloat(), targetMeasurement.target_waist_circumference.toFloat(), targetMeasurement)
        }?.takeIf { it.isNotEmpty() } ?: listOf(BarEntry(1f, 0f))

        val allEntries = waistEntries + targetEntry

        val dataSet1 = BarDataSet(allEntries.filter { it.data is PhysicalMeasurements }, "Actual waist circumference data")
        dataSet1.color = Color.parseColor("#9AC0CD")

        val dataSet2 = BarDataSet(allEntries.filter { it.data is TargetMeasurement }, "Target waist circumference")
        dataSet2.color = Color.parseColor("#8B4513")

        val barData = BarData(dataSet1, dataSet2)

        chart.data = barData

        val xAxis: XAxis = chart.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val date = allEntries.getOrNull(value.toInt() - 1)?.data?.let {
                    when (it) {
                        is PhysicalMeasurements -> it.formatted_date
                        is TargetMeasurement -> it.formatted_date
                        else -> null
                    }
                }
                return date ?: ""
            }
        }

        chart.invalidate()
    }


    private fun setChestChartData(targetMeasurements: List<TargetMeasurement>?, physicalMeasurements: List<PhysicalMeasurements>?, chart: BarChart) {
        val chestEntries: List<BarEntry> = physicalMeasurements?.mapIndexed { index, measurement ->
            BarEntry((index + 1).toFloat(), measurement.chest_circumference.toFloat(), measurement)
        }?.takeIf { it.isNotEmpty() } ?: listOf(BarEntry(1f, 0f))

        val targetEntry: List<BarEntry> = targetMeasurements?.mapIndexed { index, targetMeasurement ->
            BarEntry((index + 1 + (physicalMeasurements?.size ?: 0)).toFloat(), targetMeasurement.target_chest_circumference.toFloat(), targetMeasurement)
        }?.takeIf { it.isNotEmpty() } ?: listOf(BarEntry(1f, 0f))

        val allEntries = chestEntries + targetEntry

        val dataSet1 = BarDataSet(allEntries.filter { it.data is PhysicalMeasurements }, "Actual chest circumference data")
        dataSet1.color = Color.parseColor("#9AC0CD")

        val dataSet2 = BarDataSet(allEntries.filter { it.data is TargetMeasurement }, "Target chest circumference")
        dataSet2.color = Color.parseColor("#8B4513")

        val barData = BarData(dataSet1, dataSet2)

        chart.data = barData

        val xAxis: XAxis = chart.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val date = allEntries.getOrNull(value.toInt() - 1)?.data?.let {
                    when (it) {
                        is PhysicalMeasurements -> it.formatted_date
                        is TargetMeasurement -> it.formatted_date
                        else -> null
                    }
                }
                return date ?: ""
            }
        }

        chart.invalidate()
    }

    private fun setArmChartData(targetMeasurements: List<TargetMeasurement>?, physicalMeasurements: List<PhysicalMeasurements>?, chart: BarChart) {
        val armEntries: List<BarEntry> = physicalMeasurements?.mapIndexed { index, measurement ->
            BarEntry((index + 1).toFloat(), measurement.arm_circumference.toFloat(), measurement)
        }?.takeIf { it.isNotEmpty() } ?: listOf(BarEntry(1f, 0f))

        val targetEntry: List<BarEntry> = targetMeasurements?.mapIndexed { index, targetMeasurement ->
            BarEntry((index + 1 + (physicalMeasurements?.size ?: 0)).toFloat(), targetMeasurement.target_arm_circumference.toFloat(), targetMeasurement)
        }?.takeIf { it.isNotEmpty() } ?: listOf(BarEntry(1f, 0f))

        val allEntries = armEntries + targetEntry

        val dataSet1 = BarDataSet(allEntries.filter { it.data is PhysicalMeasurements }, "Actual arm circumference data")
        dataSet1.color = Color.parseColor("#9AC0CD")

        val dataSet2 = BarDataSet(allEntries.filter { it.data is TargetMeasurement }, "Target arm circumference")
        dataSet2.color = Color.parseColor("#8B4513")

        val barData = BarData(dataSet1, dataSet2)

        chart.data = barData

        val xAxis: XAxis = chart.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val date = allEntries.getOrNull(value.toInt() - 1)?.data?.let {
                    when (it) {
                        is PhysicalMeasurements -> it.formatted_date
                        is TargetMeasurement -> it.formatted_date
                        else -> null
                    }
                }
                return date ?: ""
            }
        }

        chart.invalidate()
    }

    private fun setLegChartData(targetMeasurements: List<TargetMeasurement>?, physicalMeasurements: List<PhysicalMeasurements>?, chart: BarChart) {
        val legEntries: List<BarEntry> = physicalMeasurements?.mapIndexed { index, measurement ->
            BarEntry((index + 1).toFloat(), measurement.leg_circumference.toFloat(), measurement)
        }?.takeIf { it.isNotEmpty() } ?: listOf(BarEntry(1f, 0f))

        val targetEntry: List<BarEntry> = targetMeasurements?.mapIndexed { index, targetMeasurement ->
            BarEntry((index + 1 + (physicalMeasurements?.size ?: 0)).toFloat(), targetMeasurement.target_leg_circumference.toFloat(), targetMeasurement)
        }?.takeIf { it.isNotEmpty() } ?: listOf(BarEntry(1f, 0f))

        val allEntries = legEntries + targetEntry

        val dataSet1 = BarDataSet(allEntries.filter { it.data is PhysicalMeasurements }, "Actual leg circumference data")
        dataSet1.color = Color.parseColor("#9AC0CD")

        val dataSet2 = BarDataSet(allEntries.filter { it.data is TargetMeasurement }, "Target leg circumference")
        dataSet2.color = Color.parseColor("#8B4513")

        val barData = BarData(dataSet1, dataSet2)

        chart.data = barData

        val xAxis: XAxis = chart.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val date = allEntries.getOrNull(value.toInt() - 1)?.data?.let {
                    when (it) {
                        is PhysicalMeasurements -> it.formatted_date
                        is TargetMeasurement -> it.formatted_date
                        else -> null
                    }
                }
                return date ?: ""
            }
        }

        chart.invalidate()
    }

    private fun setHipChartData(targetMeasurements: List<TargetMeasurement>?, physicalMeasurements: List<PhysicalMeasurements>?, chart: BarChart) {
        val hipEntries: List<BarEntry> = physicalMeasurements?.mapIndexed { index, measurement ->
            BarEntry((index + 1).toFloat(), measurement.hip_circumference.toFloat(), measurement)
        }?.takeIf { it.isNotEmpty() } ?: listOf(BarEntry(1f, 0f))

        val targetEntry: List<BarEntry> = targetMeasurements?.mapIndexed { index, targetMeasurement ->
            BarEntry((index + 1 + (physicalMeasurements?.size ?: 0)).toFloat(), targetMeasurement.target_hip_circumference.toFloat(), targetMeasurement)
        }?.takeIf { it.isNotEmpty() } ?: listOf(BarEntry(1f, 0f))

        val allEntries = hipEntries + targetEntry

        val dataSet1 = BarDataSet(allEntries.filter { it.data is PhysicalMeasurements }, "Actual hip circumference data")
        dataSet1.color = Color.parseColor("#9AC0CD")

        val dataSet2 = BarDataSet(allEntries.filter { it.data is TargetMeasurement }, "Target hip circumference")
        dataSet2.color = Color.parseColor("#8B4513")

        val barData = BarData(dataSet1, dataSet2)

        chart.data = barData

        val xAxis: XAxis = chart.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val date = allEntries.getOrNull(value.toInt() - 1)?.data?.let {
                    when (it) {
                        is PhysicalMeasurements -> it.formatted_date
                        is TargetMeasurement -> it.formatted_date
                        else -> null
                    }
                }
                return date ?: ""
            }
        }

        chart.invalidate()
    }

}