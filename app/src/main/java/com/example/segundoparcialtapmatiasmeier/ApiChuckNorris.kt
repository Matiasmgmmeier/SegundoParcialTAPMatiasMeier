package com.example.segundoparcialtapmatiasmeier

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiChuckNorris
{
    @GET suspend fun getJokes(@Url url : String ) : Response<ChuckNorrisJoke>

    @GET suspend fun getCategories(@Url url : String ) : Response<List<String>>

}