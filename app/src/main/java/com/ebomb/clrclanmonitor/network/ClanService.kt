package com.ebomb.clrclanmonitor.network

import com.ebomb.clrclanmonitor.BuildConfig
import com.ebomb.clrclanmonitor.model.Clan
import com.ebomb.clrclanmonitor.model.Warlog
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface ClanService {

    @Headers("Accept: application/json", "Authorization: Bearer " + BuildConfig.CLRTOKEN)
    @GET("clans/{clanTag}")
    fun clanInfo(@Path(value = "clanTag") clanTag: String): Observable<Clan>

    @Headers("Accept: application/json", "Authorization: Bearer " + BuildConfig.CLRTOKEN)
    @GET("clans/{clanTag}/warlog")
    fun warlog(@Path(value = "clanTag") clanTag: String): Observable<Warlog>

    companion object {
        fun create(): ClanService {
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            }

            val httpClient = OkHttpClient.Builder()
                    .addInterceptor(logging)

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.clashroyale.com/v1/")
                    .client(httpClient.build())
                    .build()

            return retrofit.create(ClanService::class.java)
        }
    }
}