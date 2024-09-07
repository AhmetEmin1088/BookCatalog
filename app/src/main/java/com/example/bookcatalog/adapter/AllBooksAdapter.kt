package com.example.bookcatalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.bookcatalog.databinding.AllBooksRecyclerRowBinding
import com.example.bookcatalog.model.Book
import com.example.bookcatalog.util.createPlaceHolder
import com.example.bookcatalog.util.downloadImage
import com.example.bookcatalog.view.HomePageFragmentDirections
import com.example.bookcatalog.viewmodel.HomePageViewModel

class AllBooksAdapter(private val allBookList: ArrayList<Book>, private val viewModel: HomePageViewModel) :
    RecyclerView.Adapter<AllBooksAdapter.AllBooksHolder>() {

    class AllBooksHolder(val binding: AllBooksRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBooksHolder {
        val binding = AllBooksRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllBooksHolder(binding)
    }

    override fun getItemCount(): Int = allBookList.size

    override fun onBindViewHolder(holder: AllBooksHolder, position: Int) {

        val book = allBookList[position]
        holder.binding.bookNameText.text = book.bookVolumeInfo?.bookTitle
        holder.binding.bookAuthorText.text = book.bookVolumeInfo?.bookAuthors?.joinToString(", ")
        holder.binding.bookImageView.downloadImage(
            book.bookVolumeInfo?.bookImageLinks?.bookSmallImageLink ?: "",
            createPlaceHolder(holder.itemView.context)
        )

        holder.itemView.setOnClickListener {
            val action = HomePageFragmentDirections.actionHomePageFragmentToBookDetailFragment(book.bookId)
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.addToFavouritesButton.setOnClickListener {
            viewModel.saveToRoom(book)
            val action = HomePageFragmentDirections.actionHomePageFragmentToFavouriteBooksFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateBookList(newList: List<Book>) {
        allBookList.clear()
        allBookList.addAll(newList)
        notifyDataSetChanged()
    }
}
