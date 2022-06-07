package com.miqdad.e_news.ui.search.category

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
import com.miqdad.e_news.databinding.FragmentListBinding
import com.miqdad.e_news.ui.OnItemClickCallback
import com.miqdad.e_news.ui.detail.DetailActivity
import com.miqdad.e_news.ui.search.SearchViewModel

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding as FragmentListBinding

    private var _viewModel: SearchViewModel? = null
    private val viewModel get() = _viewModel as SearchViewModel

    private lateinit var getCategory: String
    private lateinit var mAdapter: NewsCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(layoutInflater)
        _viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        mAdapter = NewsCategoryAdapter()
        // mengset recycler view

        getCategory = arguments?.getString("key").toString()

        viewModel.getNewsCategory(getCategory)

        viewModel.searchResponse.observe(viewLifecycleOwner) {
            setUpRecyclerView(it.articles as List<ArticlesItem>?)
        }

        return binding.root
    }

    private fun setUpRecyclerView(data: List<ArticlesItem>?) {
        Log.i("setuprec", "setUpRecyclerView: $data")
        binding.rvCategory.apply {
            mAdapter.setData(data)
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}