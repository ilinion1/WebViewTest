package com.example.webviewtest.data.network.api

import com.example.webviewtest.data.network.model.LinkContainerDto
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET(KEY)
    fun loadLink(): Single<LinkContainerDto>

    companion object{
        const val KEY = "prod"
    }

}