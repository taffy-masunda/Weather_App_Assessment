package za.co.taffy.weatherappassessment.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Clouds (
    val all: Long
)

data class Coord (
    val lon: Double,
    val lat: Double
)

data class Main (
    val temp: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("temp_min")
    val tempMin: Double,

    @SerializedName("temp_max")
    val tempMax: Double,

    val pressure: Long,
    val humidity: Long
)

data class Sys (
    val type: Long,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class Weather (
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
): Serializable

data class Wind (
    val speed: Double,
    val deg: Long
)


data class CurrentWeatherResponse(
    val coordinates: Coord,
    @SerializedName("weather")
    val weatherEntries: List<Weather>,
    val base: String,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    @SerializedName("dt")
    val dateTime: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
)
