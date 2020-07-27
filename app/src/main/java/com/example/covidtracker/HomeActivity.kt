package com.example.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {

    lateinit var retrofit : Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        CountryData.countryList.clear()
//
//        retrofit = Retrofit.Builder()
//            .baseUrl(Utilities.AllCountryUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val allCountry = retrofit.create(CovidTrackerApi::class.java)
//        val call : Call<CountryItem> = allCountry.getAllCountries()
//
//        call.enqueue(object : Callback<CountryItem>{
//            override fun onFailure(call: Call<CountryItem>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<CountryItem>, response: Response<CountryItem>) {
//                if(response.isSuccessful){
//                    val output = response.body()
//                    if(output != null){
//                      CountryData.countryList.add(output)
//                    }
//                }
//            }
//        })

        viewpager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tabLayout,viewpager
        , TabLayoutMediator.TabConfigurationStrategy{tab, position ->

                when(position){
                    0 -> tab.text = "Global"
                    1 -> tab.text = "Country"
                }

            }).attach()

    }
}