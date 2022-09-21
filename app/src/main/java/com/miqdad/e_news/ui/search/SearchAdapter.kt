package com.miqdad.e_news.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.miqdad.e_news.data.ArticlesItem
import com.miqdad.e_news.databinding.RowItemCategoriesBinding
import com.miqdad.e_news.helper.HelperFunction
import com.miqdad.e_news.ui.OnItemClickCallback

class SearchAdapter(var customItemCount: Int? = null): RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private var listNews = ArrayList<ArticlesItem>()

    fun setDataSearch(data: List<ArticlesItem>?) {
        if (data == null) return
        listNews.clear()
        listNews.addAll(data)
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class MyViewHolder(val binding: RowItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        RowItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listNews[position]
        holder.binding.apply {
            val date = HelperFunction().dateFormatter(data.publishedAt!!)
            tvTitle.text = data.title
            tvDate.text = date
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

    override fun getItemCount(): Int = if(customItemCount == null) listNews.size else customItemCount!!
}