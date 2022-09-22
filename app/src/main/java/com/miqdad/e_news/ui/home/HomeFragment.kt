package com.miqdad.e_news.ui.home

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.miqdad.e_news.R
import com.miqdad.e_news.data.ArticlesItem
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.getTopHeadlineNews("ID")
        viewModel.topHeadlineResponse.observe(viewLifecycleOwner) { showData(it.articles as List<ArticlesItem>) }

        activity?.actionBar?.hide()

        createNotificationChannel()
        binding.btnExit.setOnClickListener {
            sendNotification()
            alertDialogExit()
        }
        return binding.root
    }

    private fun showData(data: List<ArticlesItem>) {
        binding.rvSlide.apply {
            val mAdapter = SliderAdapter()
            layoutManager = CenterItemLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = mAdapter
            PagerSnapHelper().attachToRecyclerView(this)
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
        builder.setPositiveButton("Yes") { _, _ ->
            finish()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setNeutralButton("Cancel") { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun finish() {
        TODO("Not yet implemented")
    }
}