package za.co.taffy.weatherappassessment.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    @SerializedName("list")
    val list: List<DayForecast>,
    val message: Double
)

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
)

data class DayForecast(
    @SerializedName("dt")
    val dateTime: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike,
    val pressure: Long,
    val humidity: Long,
    @SerializedName("weather")
    val weatherList: List<Weather>,
    val speed: Double,
    val deg: Long,
    val clouds: Int): Serializable

data class FeelsLike(
    val day: Double,
    val eve: Double,
    val morn: Double,
    val night: Double
)

data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)
