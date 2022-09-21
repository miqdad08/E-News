package com.miqdad.e_news.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.miqdad.e_news.data.ArticlesItem
import com.miqdad.e_news.databinding.RowItemNewsBinding
import com.miqdad.e_news.helper.HelperFunction
import com.miqdad.e_news.ui.OnItemClickCallback

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    private var listNews = ArrayList<ArticlesItem>()

    fun setData(data: List<ArticlesItem>?) {
        if (data == null) return
        listNews.clear()
        listNews.addAll(data)
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class MyViewHolder(val binding: RowItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        RowItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listNews[position]
        holder.binding.apply {
            var date = HelperFunction().dateFormatter(data.publishedAt!!)
            tvTitle.text = data.title
            tvTime.text = date
            tvAuthor.text = "By ${data.author}"
            Glide.with(imgNews.context)
                .load(data.urlToImage)
                .apply(RequestOptions())
                .override(500, 500)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(imgNews)

            holder.itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(data)
            }
        }
    }

    override fun getItemCount() = listNews.size
}