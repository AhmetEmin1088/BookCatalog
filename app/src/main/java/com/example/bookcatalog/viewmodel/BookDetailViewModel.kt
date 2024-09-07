package com.example.bookcatalog.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bookcatalog.model.Book
import com.example.bookcatalog.service.BookAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookDetailViewModel(application: Application) : AndroidViewModel(application) {

    val theBook = MutableLiveData<Book?>()

    private val bookApiService = BookAPIService()

    fun getMoreDataFromInternet(volumeId: String, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val book = bookApiService.getOneData(volumeId, apiKey)
                withContext(Dispatchers.Main) {
                    if (book != null) {
                        theBook.value = book
                    } else {
                        Toast.makeText(getApplication(),"Details of the book were not found.",Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(getApplication(),"Error occurred while fetching the details of the book.",Toast.LENGTH_LONG).show()
            }
        }
    }
}
