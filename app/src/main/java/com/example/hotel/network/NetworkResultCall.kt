package com.example.hotel.network

import android.util.Log
import com.example.hotel.network.NetworkResult.Companion.NULL_BODY_ERROR
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResultCall<T : Any>(
    private val proxy: Call<T>
) : Call<NetworkResult<T>> {

    override fun enqueue(callback: Callback<NetworkResult<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResultCall,
                            Response.success(NetworkResult.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResultCall,
                            Response.success(NetworkResult.Error(NULL_BODY_ERROR, "null body"))
                        )
                    }
                } else {
                    callback.onResponse(
                        this@NetworkResultCall,
                        Response.success(
                            NetworkResult.Error(
                                response.code(),
                                response.errorBody().toString()
                            )
                        )
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult: NetworkResult<T> = NetworkResult.Exception(t)
                Log.d("lastIndex", t.toString())
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }
        })
    }

    override fun execute(): Response<NetworkResult<T>> =
        throw NotImplementedError()

    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() {
        proxy.cancel()
    }

    override fun clone(): Call<NetworkResult<T>> =
        NetworkResultCall(proxy.clone())
}