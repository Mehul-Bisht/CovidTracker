package com.example.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val url = "https://coronavirus-19-api.herokuapp.com/countries/"
    lateinit var retrofit : Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setClicks()

    }

    private fun setClicks(){
        india.setOnClickListener(this)
        japan.setOnClickListener(this)
        argentina.setOnClickListener(this)
    }

    private fun displayFor(countryName : String){
        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val country = retrofit.create(CovidTrackerApi::class.java)
        val call : Call<CountryItem> = country.getCountryData(countryName)

        call.enqueue( object : Callback<CountryItem>{
            override fun onFailure(call: Call<CountryItem>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<CountryItem>,
                response: Response<CountryItem>
            ) {
                if(!response.isSuccessful){
                    wholespace.text = "Code : ${response.code()}"
                }
                val output : CountryItem? = response.body()
                if (output != null) {

                    wholespace.text = ""

                    var content = ""
                    content = "country : ${output.country} \n"
                    content += "cases : ${output.cases} \n"
                    content += "todaycases : ${output.todayCases} \n"
                    content += "active : ${output.active} \n"
                    content += "critical : ${output.critical} \n"
                    content += "totaltests : ${output.totalTests} \n"
                    content += "testspermil : ${output.testsPerOneMillion}"

                    wholespace.append(content)

                }

            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.india -> displayFor("India")
            R.id.argentina -> displayFor("Argentina")
            R.id.japan -> displayFor("Japan")
        }
    }
}