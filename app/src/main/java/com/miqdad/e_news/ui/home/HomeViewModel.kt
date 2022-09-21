package com.miqdad.e_news.ui.home

import androidx.lifecycle.MutableLiveData
import com.miqdad.e_news.data.network.ApiClient
import androidx.lifecycle.ViewModel
import com.miqdad.e_news.data.TopHeadlineResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    var isError = MutableLiveData<Throwable>()
    var topHeadlineResponse = MutableLiveData<TopHeadlineResponse>()

    private fun getData(
        responseHandler: (TopHeadlineResponse) -> Unit,
        errorHandler: (Throwable) -> Unit,
        news: String
    ) {
        ApiClient.getApiService().getTopHeadlineNews(news)
            //membuat background thread / asnycronus
            .subscribeOn(Schedulers.io())
            //menentukan dimana thread akan dibuat
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun getTopHeadlineNews(news: String) {
        isLoading.value = true
        getData({
            isLoading.value = false
            topHeadlineResponse.value = it
        }, {
            isLoading.value = true
            isError.value = it
        }, news)
    }
}