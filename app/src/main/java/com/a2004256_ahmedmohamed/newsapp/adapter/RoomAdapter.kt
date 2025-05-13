package com.a2004256_ahmedmohamed.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.a2004256_ahmedmohamed.newsapp.R
import com.a2004256_ahmedmohamed.newsapp.model.RoomModel
import com.a2004256_ahmedmohamed.newsapp.ui.StarFragment
import com.a2004256_ahmedmohamed.newsapp.viewmodel.HomeViewModel
import com.bumptech.glide.Glide

class RoomAdapter(private val viewModel: HomeViewModel, val context: StarFragment) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    private var newsList: List<RoomModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
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

        Glide.with(context).load(article.urlToImage).placeholder(R.drawable.background).into(holder.newsImage)
        holder.titleTextView.text = article.title
        holder.descriptionTextView.text = article.description
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun submitList(news: List<RoomModel>) {
        newsList = news
        notifyDataSetChanged()
    }

    class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        val newsImage: ImageView = view.findViewById(R.id.newsImage)
        val starIcon: ImageView = view.findViewById(R.id.starIcon)
    }
}