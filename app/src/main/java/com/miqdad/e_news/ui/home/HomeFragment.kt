package com.miqdad.e_news.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.miqdad.e_news.data.network.ArticlesItem
import com.miqdad.e_news.databinding.FragmentHomeBinding
import com.miqdad.e_news.ui.NewsAdapter
import com.miqdad.e_news.ui.OnItemClickCallback
import com.miqdad.e_news.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var _viewModel: HomeViewModel? = null
    private val viewModel get() = _viewModel as HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.getTopHeadlineNews("ID")
        viewModel.topHeadlineResponse.observe(viewLifecycleOwner){showData(it.articles as List<ArticlesItem>)}

        activity?.actionBar?.hide()



        return binding.root
    }


    private fun showData(data: List<ArticlesItem>) {
        binding.rvNews.apply {
            val mAdapter = NewsAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            Log.i("apiData", "showData: $data")
            adapter = mAdapter
            mAdapter.setData(data)
            mAdapter.setOnItemClickCallback(object : OnItemClickCallback{
                override fun onItemClicked(item: ArticlesItem) {
                    startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra("EXTRA_DATA", item)
                    )
                }
            })
        }
    }



    private fun showError(isError: Throwable?) {
        Log.e("MainActivity", "Error get data ${isError.toString()}")
    }

    private fun showLoading(isLoading: Boolean?) {
        if(isLoading == true){
            binding.progressMain.visibility = View.VISIBLE
        } else {
            binding.progressMain.visibility = View.INVISIBLE
        }

    }


}