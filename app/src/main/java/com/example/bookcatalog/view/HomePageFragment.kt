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
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookcatalog.BuildConfig
import com.example.bookcatalog.R
import com.example.bookcatalog.adapter.AllBooksAdapter
import com.example.bookcatalog.databinding.FragmentHomePageBinding
import com.example.bookcatalog.viewmodel.HomePageViewModel

class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomePageViewModel
    private lateinit var bookRecyclerAdapter: AllBooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomePageViewModel::class.java]

        bookRecyclerAdapter = AllBooksAdapter(arrayListOf(), viewModel)
        binding.allBooksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.allBooksRecyclerView.adapter = bookRecyclerAdapter

        binding.errorTextView.visibility = View.GONE
        binding.allBooksRecyclerView.visibility = View.GONE
        binding.emptyHomePageViewLayout.visibility = View.VISIBLE

        binding.searchBarView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val apiKey = BuildConfig.API_KEY
                    viewModel.getDataFromInternet(it, apiKey)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optional: Perform search as user types
                return true
            }
        })

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.books.observe(viewLifecycleOwner) { bookList ->
            if (bookList.isNotEmpty()) {
                bookRecyclerAdapter.updateBookList(bookList)
                binding.allBooksRecyclerView.visibility = View.VISIBLE
                binding.emptyHomePageViewLayout.visibility = View.GONE
                binding.errorTextView.visibility = View.GONE
            } else {
                binding.allBooksRecyclerView.visibility = View.GONE
                binding.emptyHomePageViewLayout.visibility = View.VISIBLE
                binding.errorTextView.visibility = View.GONE
            }
        }

        viewModel.bookErrorMessage.observe(viewLifecycleOwner) { errorOccurred ->
            if (errorOccurred) {
                binding.allBooksRecyclerView.visibility = View.GONE
                binding.emptyHomePageViewLayout.visibility = View.GONE
                binding.errorTextView.visibility = View.VISIBLE
            } else {
                binding.errorTextView.visibility = View.GONE
            }
        }

        viewModel.emptyPageView.observe(viewLifecycleOwner) { isEmpty ->
            if (isEmpty) {
                binding.allBooksRecyclerView.visibility = View.GONE
                binding.emptyHomePageViewLayout.visibility = View.VISIBLE
                binding.errorTextView.visibility = View.GONE
            } else {
                binding.emptyHomePageViewLayout.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.favouriteBooksItem){

            val action = HomePageFragmentDirections.actionHomePageFragmentToFavouriteBooksFragment()
            Navigation.findNavController(requireView()).navigate(action)

        } else if (item.itemId == R.id.homePageItem) {

            Toast.makeText(requireContext(),"You are already in home page.",Toast.LENGTH_LONG).show()

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
