package za.co.taffy.weatherappassessment.data.retrofit_api

class WeatherAppRepo(private val serviceApiHelper: ServiceApiHelper) {

    suspend fun getCurrentWeather() = serviceApiHelper.getCurrentWeather()
    suspend fun getFutureForecast() = serviceApiHelper.getFiveDaysForecast()
}