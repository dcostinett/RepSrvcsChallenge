package com.costinett.network

import android.util.Log
import com.costinett.model.DriversAndRoutes
import retrofit2.Response
import retrofit2.http.GET

interface DriverInterface {
    @GET("data")
    suspend fun getDriverData(): Response<DriversAndRoutes>
}

sealed class NetworkStateResponse<out T : Any> {
    data class Success<out T : Any>(val data: T?) : NetworkStateResponse<T>()
    data class Error(val exception: Exception) : NetworkStateResponse<Nothing>()
}

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkStateResponse<T> {
    return try {
        val response = call.invoke()

        if (response.isSuccessful) {
            response.body()?.let {
                NetworkStateResponse.Success(it)
            } ?: NetworkStateResponse.Success(null)

        } else {
            val error = if (response.errorBody() != null) {
                String(response.errorBody()?.bytes()!!)
            } else {
                "Error occurred during safe Api Call"
            }
            NetworkStateResponse.Error(RemoteRequestException(error))
        }
    } catch (error: Exception) {
        Log.e("safeApiCall", error.toString())
        NetworkStateResponse.Error(error)
    }
}

data class RemoteRequestException(val errorMessage: String) : Exception(errorMessage)

data class BadRequestException(val errorMessage: String) : Exception(errorMessage)
