package com.miqdad.e_news.ui.search

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
import com.miqdad.e_news.databinding.FragmentSearchBinding
import com.miqdad.e_news.ui.NewsAdapter
import com.miqdad.e_news.ui.OnItemClickCallback
import com.miqdad.e_news.ui.detail.DetailActivity

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private var _viewModel: SearchViewModel? = null
    private val viewModel get() = _viewModel as SearchViewModel
    private var isLoading: Boolean? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

//        viewModel.ewLifecycleOwner){setUpRecyclerView
//        (it.articles as List<ArticlesItem>)}

        setUpSortByMenu()

        activity?.actionBar?.hide()

        return binding.root
    }


    //mengset reclyer view
    private fun setUpRecyclerView(data: List<ArticlesItem>?) {
        binding.rvSearch.apply {
            val mAdapter = SearchAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            Log.i("apiData", "showData: $data")
            adapter = mAdapter
            mAdapter.setDataSearch(data)
            mAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                override fun onItemClicked(item: ArticlesItem) {
                    startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra("EXTRA_DATA", item)
                    )
                }
            })
        }
    }


    //untuk search
    fun setUpSortByMenu() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getNewsBySearch(query)

                viewModel.searchResponse.observe (viewLifecycleOwner) {
                    setUpRecyclerView(it.articles as List<ArticlesItem>?)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getNewsBySearch(newText)

                viewModel.searchResponse.observe (viewLifecycleOwner) {
                    setUpRecyclerView(it.articles as List<ArticlesItem>?)
                }
                return false
            }

        })
    }

    //loading
    private fun loadingStateView() {
        binding.apply {
            when (isLoading) {
                true -> {
                    layoutSearch.visibility = View.INVISIBLE
                    progressBar.visibility = View.VISIBLE
                }
                false -> {
                    layoutSearch.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                }
                true -> {
                    layoutSearch.visibility = View.INVISIBLE
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

}