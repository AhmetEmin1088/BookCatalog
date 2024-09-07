package com.example.bookcatalog.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity
@TypeConverters(Converters::class)
data class Book(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val bookId: String,

    @Embedded
    @SerializedName("volumeInfo")
    val bookVolumeInfo: VolumeInfo? = null,
)

data class VolumeInfo(
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val bookTitle: String? = null,

    @TypeConverters(Converters::class)
    @ColumnInfo(name = "authors")
    @SerializedName("authors")
    val bookAuthors: List<String>? = null,

    @ColumnInfo(name = "publisher")
    @SerializedName("publisher")
    val bookPublisher: String? = null,

    @ColumnInfo(name = "publishedDate")
    @SerializedName("publishedDate")
    val bookPublishedDate: String? = null,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    val bookDescription: String? = null,

    @ColumnInfo(name = "pageCount")
    @SerializedName("pageCount")
    val bookPageCount: Long? = null,

    @Embedded
    @SerializedName("imageLinks")
    val bookImageLinks: ImageLinks? = null,
)

data class ImageLinks(
    @ColumnInfo(name = "smallThumbnail")
    @SerializedName("smallThumbnail")
    val bookSmallImageLink: String? = null
)

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.joinToString(", ")
    }
}
