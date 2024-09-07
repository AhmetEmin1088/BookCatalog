package com.example.bookcatalog.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bookcatalog.model.Book
import com.example.bookcatalog.roomdb.BookDatabase
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteBooksViewModel(application: Application) : AndroidViewModel(application) {

    val favouriteBookErrorMessage = MutableLiveData<Boolean>()
    val books = MutableLiveData<List<Book>>()

    fun getDataFromRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val bookList = BookDatabase(getApplication()).bookDao().getAllBook()
                withContext(Dispatchers.Main) {
                    if (bookList.isNotEmpty()) {
                        showBooks(bookList)
                    }else {
                        handleEmptyBookList()
                    }

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(getApplication(),e.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun deleteFromRoom(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                BookDatabase(getApplication()).bookDao().deleteBook(book)
                withContext(Dispatchers.Main) {
                    Toast.makeText(getApplication(),"${book.bookVolumeInfo?.bookTitle} is removed from favourites.",Toast.LENGTH_LONG).show()

                    getDataFromRoom()
                }
            } catch (e: Exception) {
                Toast.makeText(getApplication(),e.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showBooks(bookList: List<Book>) {
        books.value = bookList
        favouriteBookErrorMessage.value = false
    }

    private fun handleEmptyBookList() {
        books.value = emptyList()
        favouriteBookErrorMessage.value = true
    }

}
