package com.example.besinlerkitabi.servis

import com.example.besinlerkitabi.model.Besin
import io.reactivex.Single
import retrofit2.http.GET

interface BesinAPI {
    //GET,POST

    //BASE_URL-> https://raw.githubusercontent.com/
    //atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesin():Single<List<Besin>>



}