package za.co.taffy.weatherappassessment.data.retrofit_api

import retrofit2.http.GET
import retrofit2.http.Query
import za.co.taffy.weatherappassessment.data.models.CurrentWeatherResponse
import za.co.taffy.weatherappassessment.data.models.ForecastResponse

interface ServiceApi {

    @GET("forecast/daily?units=metric&cnt=10&cont=16")
    suspend fun getFiveDaysForecast(@Query("lat") latitude : String, @Query("lon")longitude : String): ForecastResponse

    @GET("weather?units=metric")
    suspend fun getCurrentWeather(@Query("lat") latitude : String, @Query("lon")longitude : String): CurrentWeatherResponse
}