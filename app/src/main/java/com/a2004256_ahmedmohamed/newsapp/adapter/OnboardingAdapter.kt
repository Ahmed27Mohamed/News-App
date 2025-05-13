package com.a2004256_ahmedmohamed.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.a2004256_ahmedmohamed.newsapp.R
import com.a2004256_ahmedmohamed.newsapp.model.OnBoardingModel

class OnboardingAdapter(private val items: List<OnBoardingModel>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.titleTextView)
        private val description = view.findViewById<TextView>(R.id.descriptionTextView)
        private val image = view.findViewById<ImageView>(R.id.imageView)

        fun bind(item: OnBoardingModel) {
            title.text = item.title
            description.text = item.description
            image.setImageResource(item.imageResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
