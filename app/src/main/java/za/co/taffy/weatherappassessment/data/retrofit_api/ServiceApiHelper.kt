package za.co.taffy.weatherappassessment.data.retrofit_api

class ServiceApiHelper(private val apiService: ServiceApi, private val latitude: String, private val longitude: String) {
    suspend fun getFiveDaysForecast() = apiService.getFiveDaysForecast(latitude, longitude)
    suspend fun getCurrentWeather() = apiService.getCurrentWeather(latitude, longitude)
}