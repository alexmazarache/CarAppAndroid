package ro.ubbcluj.cs.ilazar.mycarapplication196.todo.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ro.ubbcluj.cs.ilazar.mycarapplication196.core.Api
import ro.ubbcluj.cs.ilazar.mycarapplication196.todo.data.Car

object CarApi {
    interface Service {
        @GET("/api/item")
        suspend fun find(): List<Car>

        @GET("/api/item/{id}")
        suspend fun read(@Path("id") itemId: String): Car;

        @Headers("Content-Type: application/json")
        @POST("/api/item")
        suspend fun create(@Body item: Car): Car

        @Headers("Content-Type: application/json")
        @PUT("/api/item/{id}")
        suspend fun update(@Path("id") itemId: String, @Body item: Car): Car
    }

    val service: Service = Api.retrofit.create(Service::class.java)
}