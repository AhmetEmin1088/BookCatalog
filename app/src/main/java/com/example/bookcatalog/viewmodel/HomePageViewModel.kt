package com.example.bookcatalog.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bookcatalog.model.Book
import com.example.bookcatalog.roomdb.BookDatabase
import com.example.bookcatalog.service.BookAPIService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageViewModel(application: Application) : AndroidViewModel(application) {

    val books = MutableLiveData<List<Book>>()
    val bookErrorMessage = MutableLiveData<Boolean>()
    val emptyPageView = MutableLiveData<Boolean>()

    private val bookApiService = BookAPIService()

    fun getDataFromInternet(query: String, apiKey: String) {
        emptyPageView.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val bookList = bookApiService.getData(query, apiKey)
                withContext(Dispatchers.Main) {
                    if (!bookList.isNullOrEmpty()) {
                        showBooks(bookList)
                    } else {
                        handleEmptyBooks()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    handleError()
                }
            }
        }
    }

    fun saveToRoom(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dao = BookDatabase.invoke(getApplication()).bookDao()
                dao.insert(book)
                withContext(Dispatchers.Main) {
                    Toast.makeText(getApplication(), "${book.bookVolumeInfo?.bookTitle} is added to favourites.", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(getApplication(), e.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun showBooks(bookList: List<Book>) {
        books.value = bookList
        bookErrorMessage.value = false
        emptyPageView.value = false
    }

    private fun handleEmptyBooks() {
        books.value = emptyList()
        bookErrorMessage.value = false
        emptyPageView.value = true
    }

    private fun handleError() {
        bookErrorMessage.value = true
        emptyPageView.value = false
    }
}
