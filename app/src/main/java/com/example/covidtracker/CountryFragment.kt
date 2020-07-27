package com.example.covidtracker

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.text.Html
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.fragment_country.*
import kotlinx.android.synthetic.main.item_country.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CountryFragment : Fragment() {

    lateinit var retrofit : Retrofit
    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CountryData.countryList.clear()

        retrofit = Retrofit.Builder()
            .baseUrl(Utilities.AllCountryUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val allCountry = retrofit.create(CovidTrackerApi::class.java)
        val call : Call<Country> = allCountry.getAllCountries()

        call.enqueue(object : Callback<Country>{
            override fun onFailure(call: Call<Country>, t: Throwable) {

            }

            override fun onResponse(call: Call<Country>, response: Response<Country>) {
                if(response.isSuccessful){
                    val got = response.body()
                    if(got != null){
                        //CountryData.stableThingy = output
                        val yup : Country? = got
                        CountryData.stableThingy = yup
                        for(output in yup!!){
                            adapter.add(CountryViewHolder(output.country,output.active,output.cases, output.totalTests,
                                output.testsPerOneMillion, output.critical, output.todayCases, output.todayDeaths,
                                output.recovered, output.deaths))
                        }
                    }
                }
            }
        })

        recyclerview.adapter = adapter

        searcher.queryHint = Html.fromHtml("<font color = #000000>" + resources.getString(R.string.hintSearchMess) + "</font>")

        searchCountry()
    }

    private fun searchCountry(){
        searcher.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
             searcher.clearFocus()
             return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterCountry(newText)
                return true
            }
        } )
    }

    private fun filterCountry(countryName : String?){

        if(countryName!!.isNotEmpty() && countryName.length > 0) {

            adapter.clear()
            for (country in CountryData.stableThingy!!) {

                if (country.country.toLowerCase().contains(countryName.toLowerCase())) {
                    adapter.add(
                        CountryViewHolder(
                            country.country,
                            country.active,
                            country.cases,
                            country.totalTests,
                            country.testsPerOneMillion,
                            country.critical,
                            country.todayCases,
                            country.todayDeaths,
                            country.recovered,
                            country.deaths
                        )
                    )
                    recyclerview.adapter = adapter
                }
            }

        }

    }

    class CountryViewHolder(val countryName : String, val active : Int, val cases : Int , val totalTests : Int,
                            val testsPerMil : Int, val critical : Int, val todayCases : Int, val todayDeaths : Int,
                            val recovered : Int, val deaths : Int ) : Item<GroupieViewHolder>(){
        override fun getLayout(): Int {
            return R.layout.item_country
        }

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.cases_country.text = "$cases"
            viewHolder.itemView.active_country.text = "$active"
            viewHolder.itemView.country.text = countryName
            viewHolder.itemView.totaltests_country.text = "$totalTests"
            viewHolder.itemView.testspermil_country.text = "$testsPerMil"
            viewHolder.itemView.critical_country.text = "$critical"
            viewHolder.itemView.todaycases_country.text = "$todayCases"
            viewHolder.itemView.todaydeaths_country.text = "$todayDeaths"
            viewHolder.itemView.recovered_country.text = "$recovered"
            viewHolder.itemView.deaths_country.text = "$deaths"
        }
    }

}