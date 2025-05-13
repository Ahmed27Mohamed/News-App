package com.a2004256_ahmedmohamed.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a2004256_ahmedmohamed.newsapp.R
import com.a2004256_ahmedmohamed.newsapp.adapter.RoomAdapter
import com.a2004256_ahmedmohamed.newsapp.database.NewsDatabase
import com.a2004256_ahmedmohamed.newsapp.viewmodel.HomeViewModel
import com.a2004256_ahmedmohamed.newsapp.viewmodel.HomeViewModelFactory
import com.a2004256_ahmedmohamed.newsapp.viewmodel.NewsRepository

class StarFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RoomAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_star, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsDao = NewsDatabase.getNewsRoomDatabase(requireContext()).newsDao()
        val repository = NewsRepository(newsDao)
        val factory = HomeViewModelFactory(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        adapter = RoomAdapter(viewModel, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.favoriteNews.observe(viewLifecycleOwner) { favoriteNewsList ->
            favoriteNewsList?.let {
                adapter.submitList(it)
            }
        }

        viewModel.loadFavoriteNews()
    }
}