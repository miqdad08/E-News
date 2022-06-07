package com.miqdad.e_news.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.miqdad.e_news.ui.search.category.ListFragment
import com.miqdad.e_news.data.network.ArticlesItem
import com.miqdad.e_news.databinding.FragmentSearchBinding
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
        setUpTabBarAndViewPager()

        activity?.actionBar?.hide()

        return binding.root
    }

    private fun setUpTabBarAndViewPager(){
        val tabs = binding.tabLayout
        val viewPager = binding.viewpager
        tabs.setupWithViewPager(viewPager)
        setUpTabBar(viewPager)
    }

    private fun setUpTabBar(viewPager: ViewPager) {
        val adapter = Adapter(childFragmentManager)
        adapter.addFragment("Business")
        adapter.addFragment("Entertainment")
        adapter.addFragment("General")
        adapter.addFragment("Health")
        adapter.addFragment("Science")
        adapter.addFragment("Sports")
        adapter.addFragment("Technology")
        viewPager.adapter = adapter
    }

    class Adapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(title: String) {
            var bundle = Bundle()
            val fragment = ListFragment()
            bundle.putString("key", title)
            fragment.arguments = bundle
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

    //mengset recycler view
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
        binding.searchView.setOnQueryTextFocusChangeListener { _, b ->
            if (b) {
                binding.rvSearch.visibility = View.VISIBLE
            } else {
                binding.rvSearch.visibility = View.GONE
            }
        }
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