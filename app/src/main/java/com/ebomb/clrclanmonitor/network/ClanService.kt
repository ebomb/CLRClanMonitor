package com.ebomb.clrclanmonitor.network

import com.ebomb.clrclanmonitor.model.Clan
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ClanService {

    @GET("/clans/{clanTag}")
    fun clanInfo(@Path(value = "clanTag") clanTag: String): Observable<Clan>

    companion object {
        fun create(): ClanService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.clashroyale.com/v1/")
                    .build()

            return retrofit.create(ClanService::class.java)
        }
    }
}