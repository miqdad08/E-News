package com.miqdad.e_news.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.miqdad.e_news.R
import com.miqdad.e_news.data.network.ArticlesItem
import com.miqdad.e_news.data.network.TopHeadlineResponse
import com.miqdad.e_news.databinding.ActivityDetailBinding
import com.miqdad.e_news.helper.HelperFunction

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()
    }

    private fun initView() {
        val data = intent.getParcelableExtra<ArticlesItem>("EXTRA_DATA")
        binding.apply {
            var date = HelperFunction().dateFormatter(data?.publishedAt!!)
            Glide.with(this@DetailActivity)
                .load(data?.urlToImage)
                .into(imgDetail)
            if (data != null) {
                tvDetailTitle.text = data.title
                tvDescDetail.text = data.description
                tvAuthorDetail.text = data.author
                tvDateDetail.text = date
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}