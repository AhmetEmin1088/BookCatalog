package com.example.bookcatalog.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.bookcatalog.BuildConfig
import com.example.bookcatalog.R
import com.example.bookcatalog.databinding.FragmentBookDetailBinding
import com.example.bookcatalog.util.createPlaceHolder
import com.example.bookcatalog.util.downloadImage
import com.example.bookcatalog.viewmodel.BookDetailViewModel

class BookDetailFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookDetailViewModel
    private var bookId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[BookDetailViewModel::class.java]
        arguments?.let {
            bookId = BookDetailFragmentArgs.fromBundle(it).bookId
            val apiKey = BuildConfig.API_KEY
            viewModel.getMoreDataFromInternet(bookId, apiKey)
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.theBook.observe(viewLifecycleOwner) {
            binding.detailsBookNameText.text = it?.bookVolumeInfo?.bookTitle
            binding.detailsBookAuthorText.text = it?.bookVolumeInfo?.bookAuthors.toString()
            binding.detailsBookPageNumberText.text = it?.bookVolumeInfo?.bookPageCount.toString() + " pages"
            binding.detailsBookPublisherText.text = it?.bookVolumeInfo?.bookPublisher
            binding.detailsBookPublishedDateText.text = it?.bookVolumeInfo?.bookPublishedDate
            binding.detailsBookDescriptionText.text = it?.bookVolumeInfo?.bookDescription
            binding.detailsBookImageView.downloadImage(it?.bookVolumeInfo?.bookImageLinks?.bookSmallImageLink!!,
                createPlaceHolder(requireContext())
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.favouriteBooksItem){

            val action = BookDetailFragmentDirections.actionBookDetailFragmentToFavouriteBooksFragment()
            Navigation.findNavController(requireView()).navigate(action)

        } else if (item.itemId == R.id.homePageItem) {

            val action = BookDetailFragmentDirections.actionBookDetailFragmentToHomePageFragment()
            Navigation.findNavController(requireView()).navigate(action)

        }else if (item.itemId == R.id.closeAppItem) {

            val alertDialog = AlertDialog.Builder(requireActivity())
            alertDialog
                .setTitle("Close Application")
                .setMessage("Do you want to close the app?")
                .setPositiveButton("Yes") { dialogInterface, i ->
                    requireActivity().finish()
                }
                .setNegativeButton("Hayır") { dialogInterface, i ->

                }
                .create()
                .show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

