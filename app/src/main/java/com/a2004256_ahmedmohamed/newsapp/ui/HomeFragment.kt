package com.a2004256_ahmedmohamed.newsapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a2004256_ahmedmohamed.newsapp.R
import com.a2004256_ahmedmohamed.newsapp.adapter.NewsAdapter
import com.a2004256_ahmedmohamed.newsapp.interfaces.RetrofitInstance
import com.a2004256_ahmedmohamed.newsapp.viewmodel.HomeViewModel
import com.a2004256_ahmedmohamed.newsapp.viewmodel.HomeViewModelFactory
import com.a2004256_ahmedmohamed.newsapp.viewmodel.NewsRepository
import com.a2004256_ahmedmohamed.newsapp.database.NewsDatabase

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var viewModel: HomeViewModel

    private lateinit var noInternetLayout: View
    private lateinit var retryButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        return view
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

        viewModel.newsList.observe(viewLifecycleOwner) { newsList ->
            newsList?.let {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }
        }

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