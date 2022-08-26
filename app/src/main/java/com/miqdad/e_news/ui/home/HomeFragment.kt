package com.miqdad.e_news.ui.home

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
=======
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.miqdad.e_news.R
>>>>>>> 6764822c3d10b229a7836bca43a61c780a676744
import com.miqdad.e_news.data.network.ArticlesItem
import com.miqdad.e_news.databinding.FragmentHomeBinding
import com.miqdad.e_news.ui.OnItemClickCallback
import com.miqdad.e_news.ui.detail.DetailActivity

class HomeFragment : Fragment() {
    private val channelId = "channel_01"
    private val notificationId = 101

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var _viewModel: HomeViewModel? = null
    private val viewModel get() = _viewModel as HomeViewModel

<<<<<<< HEAD
    private var listNews = ArrayList<ArticlesItem>()

=======
>>>>>>> 6764822c3d10b229a7836bca43a61c780a676744
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
//        val data = listNews[1]

        viewModel.getTopHeadlineNews("ID")
<<<<<<< HEAD
        viewModel.topHeadlineResponse.observe(viewLifecycleOwner){
=======
        viewModel.topHeadlineResponse.observe(viewLifecycleOwner) { showData(it.articles as List<ArticlesItem>) }
>>>>>>> 6764822c3d10b229a7836bca43a61c780a676744

            showData(it.articles as List<ArticlesItem>)

<<<<<<< HEAD
            /*binding.topHeadline.apply {
                Glide.with(context).load(it.articles).into(this)
            }*/
        }

//        viewModel.topHeadlineResponse.observe(viewLifecycleOwner){showImageSlider(it.articles as List<ArticlesItem>)}

        activity?.actionBar?.hide()

        return binding.root


    }

//    private fun showImage(data: List<ArticlesItem>){
//        binding.topHeadline.apply {
//            Glide.with(context).load(data.urlToima).into(this)
//        }
//    }

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


=======
        createNotificationChannel()
        binding.btnExit.setOnClickListener {
            sendNotification()
            alertDialogExit()
        }
        return binding.root
    }

    private fun showData(data: List<ArticlesItem>) {
        binding.rvNews.apply {
            val mAdapter = NewsAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            Log.i("apiData", "showData: $data")
            adapter = mAdapter
            mAdapter.setData(data)
            mAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                override fun onItemClicked(data: ArticlesItem) {
                    startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra("EXTRA_DATA", data)
                    )
                }
            })
        }
    }

>>>>>>> 6764822c3d10b229a7836bca43a61c780a676744
    private fun showError(isError: Throwable?) {
        Log.e("MainActivity", "Error get data ${isError.toString()}")
    }

    private fun showLoading(isLoading: Boolean?) {
        if (isLoading == true) {
            binding.progressMain.visibility = View.VISIBLE
        } else {
            binding.progressMain.visibility = View.INVISIBLE
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Text"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val builder = context?.let {
            NotificationCompat.Builder(it, channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("You exit from E-News")
                .setContentText("Succeed!")
                .setPriority(NotificationCompat.PRIORITY_MAX)
        }

        with(context?.let { NotificationManagerCompat.from(it) }) {
            builder?.let { this?.notify(notificationId, it.build()) }
        }
    }

    private fun alertDialogExit() {
        val builder = AlertDialog.Builder(context, R.style.AlertDialog)
        builder.setTitle("Exit")
        builder.setMessage("Are you sure to exit?")
        builder.setIcon(R.drawable.ic_warning_foreground)
        builder.setPositiveButton("Yes") { dialog, which ->
            finish()
        }
        builder.setNegativeButton("No") { dialog, which -> }
        builder.setNeutralButton("Cancel") { dialog, which -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun finish() {
        TODO("Not yet implemented")
    }
}