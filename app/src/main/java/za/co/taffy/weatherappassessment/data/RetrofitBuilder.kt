package za.co.taffy.weatherappassessment.data

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import za.co.taffy.weatherappassessment.data.retrofit_api.ServiceApi

object RetrofitBuilder {

    private const val STATIC_BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private fun getRetrofit(): Retrofit {

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("appid", WEATHER_API_KEY)
                .build()

            val requestBuilder = original.newBuilder()
                .url(url)
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        return Retrofit.Builder()
            .baseUrl(STATIC_BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ServiceApi = getRetrofit().create(ServiceApi::class.java)

    const val WEATHER_API_KEY = "53f9d8e4213222cf517d86dc406d67fc"
}