package com.example.bookcatalog.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bookcatalog.model.Book

@Dao
interface BookDAO {

    @Insert
    suspend fun insert(book: Book)

    @Query("SELECT * FROM book")
    suspend fun getAllBook(): List<Book>

    @Delete
    suspend fun deleteBook(book: Book)
}
