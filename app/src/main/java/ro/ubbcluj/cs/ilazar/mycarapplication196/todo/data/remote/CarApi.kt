package ro.ubbcluj.cs.ilazar.mycarapplication196.todo.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ro.ubbcluj.cs.ilazar.mycarapplication196.todo.data.Car

object CarApi {
    private const val URL = "http://192.168.68.125:3000/"

    interface Service {
        @GET("/item")
        suspend fun find(): List<Car>

        @GET("/item/{id}")
        suspend fun read(@Path("id") itemId: String): Car;

        @Headers("Content-Type: application/json")
        @POST("/item")
        suspend fun create(@Body item: Car): Car

        @Headers("Content-Type: application/json")
        @PUT("/item/{id}")
        suspend fun update(@Path("id") itemId: String, @Body item: Car): Car
    }

    private val client: OkHttpClient = OkHttpClient.Builder().build()

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val service: Service = retrofit.create(
        Service::class.java)
}