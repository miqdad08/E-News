package com.miqdad.e_news.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miqdad.e_news.data.ArticlesItem
import com.miqdad.e_news.databinding.RowImageSliderBinding
import com.miqdad.e_news.ui.OnItemClickCallback

class SliderAdapter: RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    fun setData(data : List<ArticlesItem>){
        this.listItem.clear()
        this.listItem.addAll(data)
    }

    private val listItem = arrayListOf<ArticlesItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val slideItem: RowImageSliderBinding) : RecyclerView.ViewHolder(slideItem.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        RowImageSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SliderAdapter.ViewHolder, position: Int) {
       holder.slideItem.apply {
           Glide.with(imgArtikel.context).load(listItem[position].urlToImage).into(imgArtikel)
       }
    }

    override fun getItemCount() = listItem.size
}