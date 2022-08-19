package com.miqdad.e_news.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.miqdad.e_news.data.network.ArticlesItem
import com.miqdad.e_news.databinding.RowItemCategoriesBinding
import com.miqdad.e_news.databinding.RowItemNewsBinding
import com.miqdad.e_news.ui.OnItemClickCallback

class NewsCategoryAdapter : RecyclerView.Adapter<NewsCategoryAdapter.MyViewHolder>() {

    private var listNewsCategory = ArrayList<ArticlesItem>()
    private var onItemClickCallBack: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    fun setData(data: List<ArticlesItem>?) {
        if (data == null) return
        listNewsCategory.clear()
        listNewsCategory.addAll(data)
    }

    class MyViewHolder(val binding: RowItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        RowItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NewsCategoryAdapter.MyViewHolder, position: Int) {
        val data = listNewsCategory[position]
        holder.binding.apply {
            tvTitle.text = data.title
            tvAuthor.text = data.author
            tvDate.text = data.publishedAt
            Glide.with(imgNews.context)
                .load(data.urlToImage)
                .apply(RequestOptions())
                .override(500, 500)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(imgNews)
        }
        holder.itemView.setOnClickListener {
            onItemClickCallBack?.onItemClicked(data)
        }
    }

    override fun getItemCount() = listNewsCategory.size
}