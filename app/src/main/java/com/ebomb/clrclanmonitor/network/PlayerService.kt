package com.ebomb.clrclanmonitor.network

import com.ebomb.clrclanmonitor.BuildConfig
import com.ebomb.clrclanmonitor.CLRConstants
import com.ebomb.clrclanmonitor.model.PlayerBattle
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface PlayerService {

    @Headers("Accept: application/json", "Authorization: Bearer " + BuildConfig.CLRTOKEN)
    @GET("players/{playerTag}/battlelog")
    fun battlelog(@Path(value = "playerTag") playerTag: String): Observable<PlayerBattle>

    companion object {
        fun create(): PlayerService {
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            }

            val httpClient = OkHttpClient.Builder().addInterceptor(logging)

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(CLRConstants.BASE_URL)
                    .client(httpClient.build())
                    .build()

            return retrofit.create(PlayerService::class.java)
        }
    }
}