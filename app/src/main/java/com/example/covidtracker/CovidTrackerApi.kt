package com.example.covidtracker

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidTrackerApi {

    @GET("all")
    fun getGlobalData() : Call<GlobalData>

    @GET("countries")
    fun getAllCountries() : Call<Country>

    @GET("{countryName}")
    fun getCountryData(@Path("countryName") name : String ) : Call<CountryItem>
}