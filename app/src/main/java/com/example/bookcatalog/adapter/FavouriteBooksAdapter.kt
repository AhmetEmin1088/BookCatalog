package com.example.bookcatalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.bookcatalog.databinding.FavouriteBooksRecyclerRowBinding
import com.example.bookcatalog.model.Book
import com.example.bookcatalog.util.createPlaceHolder
import com.example.bookcatalog.util.downloadImage
import com.example.bookcatalog.view.FavouriteBooksFragmentDirections
import com.example.bookcatalog.viewmodel.FavouriteBooksViewModel

class FavouriteBooksAdapter(private val favouriteBookList: ArrayList<Book>, private val viewModel: FavouriteBooksViewModel) :RecyclerView.Adapter<FavouriteBooksAdapter.FavouriteBooksHolder>() {

    class FavouriteBooksHolder(val binding: FavouriteBooksRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteBooksHolder {
        val binding = FavouriteBooksRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavouriteBooksHolder(binding)
    }

    override fun getItemCount(): Int {
        return favouriteBookList.size
    }

    override fun onBindViewHolder(holder: FavouriteBooksHolder, position: Int) {
        holder.binding.favouritesBookNameText.text = favouriteBookList[position].bookVolumeInfo?.bookTitle
        holder.binding.favouritesBookImageView.downloadImage(favouriteBookList[position].bookVolumeInfo?.bookImageLinks?.bookSmallImageLink!!,
            createPlaceHolder(holder.itemView.context)
        )

        holder.itemView.setOnClickListener {
            val action = FavouriteBooksFragmentDirections.actionFavouriteBooksFragmentToBookDetailFragment(favouriteBookList[position].bookId)
            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.removeFromFavouritesButton.setOnClickListener {
            viewModel.deleteFromRoom(favouriteBookList[position])
            favouriteBookList.removeAt(position)
            notifyItemRemoved(position)
        }

    }

    fun updateFavouriteBookList(newList: List<Book>) {
        favouriteBookList.clear()
        favouriteBookList.addAll(newList)
        notifyDataSetChanged()
    }

}