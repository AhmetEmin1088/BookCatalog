package com.example.bookcatalog.service

import com.example.bookcatalog.model.Book
import com.example.bookcatalog.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface BookAPI {

    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String
    ): BookResponse

    @GET("volumes/{volumeId}")
    suspend fun searchOneBook(
        @Path("volumeId") volumeId: String,
        @Query("key") apiKey: String
    ): Book
}
