package com.example.bookcatalog.service

import com.example.bookcatalog.model.Book
import com.example.bookcatalog.model.BookResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookAPIService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BookAPI::class.java)

    suspend fun getData(query: String, apiKey: String): List<Book>? {
        return retrofit.searchBooks(query, apiKey).books
    }

    suspend fun getOneData(volumeId: String, apiKey: String): Book? {
        return try {
            val response = retrofit.searchOneBook(volumeId, apiKey)
            response
        } catch (e: Exception) {
            null
        }
    }
}
