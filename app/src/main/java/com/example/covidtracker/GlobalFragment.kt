package com.example.covidtracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_global.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GlobalFragment : Fragment() {

    lateinit var retrofit : Retrofit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_global, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrofit = Retrofit.Builder()
            .baseUrl(Utilities.globalUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val global = retrofit.create(CovidTrackerApi::class.java)
        val call : Call<GlobalData> = global.getGlobalData()

        call.enqueue(object : Callback<GlobalData>{
            override fun onFailure(call: Call<GlobalData>, t: Throwable) {

            }

            override fun onResponse(call: Call<GlobalData>, response: Response<GlobalData>) {
                if(response.isSuccessful){
                    val output = response.body()
                    if(output != null){
                        cases.text = "${output.cases}"
                        casualties.text = "${output.deaths}"
                        recovered.text = "${output.recovered}"

                    }
                }
            }
        })

    }

}