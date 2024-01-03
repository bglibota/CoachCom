package foi.air.coachcom.ws.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private const val BASE_URL = "http://10.0.2.2:3000/"

    private var instance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val loginService: LoginService = instance.create(LoginService::class.java)
    val profileService: ProfileService = instance.create(ProfileService::class.java)
    val measurementService: MeasurementService = instance.create(MeasurementService::class.java)
    val physicalMeasurementService: PhysicalMeasurementService = instance.create(PhysicalMeasurementService::class.java)
    val targetMeasurementService: TargetMeasurementService = instance.create(TargetMeasurementService::class.java)
    val changePersonalInformationService: ChangePersonalInformationService = instance.create(ChangePersonalInformationService::class.java)
    val changePasswordService: ChangePasswordService = instance.create(ChangePasswordService::class.java)
}