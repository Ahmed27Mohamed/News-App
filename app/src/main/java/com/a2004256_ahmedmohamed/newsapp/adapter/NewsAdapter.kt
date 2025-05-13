package com.a2004256_ahmedmohamed.newsapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.a2004256_ahmedmohamed.newsapp.R
import com.a2004256_ahmedmohamed.newsapp.model.NewsModel
import com.a2004256_ahmedmohamed.newsapp.model.RoomModel
import com.a2004256_ahmedmohamed.newsapp.viewmodel.HomeViewModel
import com.bumptech.glide.Glide

class NewsAdapter(private val viewModel: HomeViewModel) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList: List<NewsModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = newsList[position]

        val url = article.url
        if (url != null) {
            viewModel.isFavorite(url).observeForever { isFavorite ->
                val starIconRes = if (isFavorite) {
                    R.drawable.star_fillid
                } else {
                    R.drawable.star
                }
                holder.starIcon.setImageResource(starIconRes)
            }
        }

        holder.starIcon.setOnClickListener {
            article.url?.let { url ->
                val description = article.description ?: "No description available"
                val title = article.title ?: "No title available"
                val image = article.urlToImage ?: "No image available"
                val favoriteNews = RoomModel(title, description, image, url)
                viewModel.toggleFavorite(favoriteNews)
            }
        }

        Glide.with(holder.itemView.context).load(article.urlToImage).placeholder(R.drawable.background).into(holder.newsImage)
        holder.titleTextView.text = article.title
        holder.descriptionTextView.text = article.description
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun submitList(news: List<NewsModel>) {
        newsList = news
        notifyDataSetChanged()
    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        val newsImage: ImageView = view.findViewById(R.id.newsImage)
        val starIcon: ImageView = view.findViewById(R.id.starIcon)
    }
}