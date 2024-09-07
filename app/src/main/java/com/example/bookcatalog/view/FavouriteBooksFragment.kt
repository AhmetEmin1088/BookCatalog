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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookcatalog.R
import com.example.bookcatalog.adapter.FavouriteBooksAdapter
import com.example.bookcatalog.databinding.FragmentFavouriteBooksBinding
import com.example.bookcatalog.viewmodel.FavouriteBooksViewModel

class FavouriteBooksFragment : Fragment() {

    private var _binding: FragmentFavouriteBooksBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavouriteBooksViewModel
    private lateinit var favouriteBookRecyclerAdapter: FavouriteBooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FavouriteBooksViewModel::class.java]
        favouriteBookRecyclerAdapter = FavouriteBooksAdapter(arrayListOf(), viewModel)
        binding.favouriteBooksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favouriteBooksRecyclerView.adapter = favouriteBookRecyclerAdapter

        observeLiveData()

        viewModel.getDataFromRoom()

        binding.homePageButton.setOnClickListener {
            val action = FavouriteBooksFragmentDirections.actionFavouriteBooksFragmentToHomePageFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }

    }

    private fun observeLiveData() {
        viewModel.books.observe(viewLifecycleOwner) { bookList ->
            bookList?.let {
                if (it.isNotEmpty()) {
                    binding.favouriteBooksRecyclerView.visibility = View.VISIBLE
                    binding.noFavouriteBookErrorMessage.visibility = View.GONE
                    favouriteBookRecyclerAdapter.updateFavouriteBookList(bookList)
                } else {
                    binding.favouriteBooksRecyclerView.visibility = View.GONE
                    binding.noFavouriteBookErrorMessage.visibility = View.VISIBLE
                }
            }
        }
        viewModel.favouriteBookErrorMessage.observe(viewLifecycleOwner) {
            if (it) {
                binding.noFavouriteBookErrorMessage.visibility = View.VISIBLE
                binding.favouriteBooksRecyclerView.visibility = View.GONE
            } else {
                binding.noFavouriteBookErrorMessage.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.favouriteBooksItem){

            Toast.makeText(requireContext(),"You are already in favourite books page.", Toast.LENGTH_LONG).show()

        } else if (item.itemId == R.id.homePageItem) {

            val action = FavouriteBooksFragmentDirections.actionFavouriteBooksFragmentToHomePageFragment()
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
