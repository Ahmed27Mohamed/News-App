package com.a2004256_ahmedmohamed.newsapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a2004256_ahmedmohamed.newsapp.R
import com.a2004256_ahmedmohamed.newsapp.adapter.NewsAdapter
import com.a2004256_ahmedmohamed.newsapp.database.NewsDatabase
import com.a2004256_ahmedmohamed.newsapp.interfaces.RetrofitInstance
import com.a2004256_ahmedmohamed.newsapp.viewmodel.HomeViewModel
import com.a2004256_ahmedmohamed.newsapp.viewmodel.HomeViewModelFactory
import com.a2004256_ahmedmohamed.newsapp.viewmodel.NewsRepository
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : Fragment() {

    private lateinit var searchView: TextInputEditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var viewModel: HomeViewModel

    private lateinit var noInternetLayout: View
    private lateinit var retryButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_search, container, false)
        searchView = v.findViewById(R.id.searchView)
        recyclerView = v.findViewById(R.id.recyclerView)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsDao = NewsDatabase.getNewsRoomDatabase(requireContext()).newsDao()
        val repository = NewsRepository(newsDao)
        val factory = HomeViewModelFactory(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        noInternetLayout = view.findViewById(R.id.noInternetLayout)
        retryButton = view.findViewById(R.id.btnRetry)

        adapter = NewsAdapter(viewModel)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.mutableLiveData.observe(viewLifecycleOwner) { newsList ->
            newsList?.let {
                adapter.submitList(it)
            }
        }

        searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                viewModel.fetchNewsOnRoom(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        retryButton.setOnClickListener {
            loadNews()
        }

        loadNews()

    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun loadNews() {
        if (isInternetAvailable(requireContext())) {
            recyclerView.visibility = View.VISIBLE
            noInternetLayout.visibility = View.GONE
            viewModel.fetchNews()
        } else {
            recyclerView.visibility = View.GONE
            noInternetLayout.visibility = View.VISIBLE
        }
    }

}