package com.miqdad.e_news.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.miqdad.e_news.data.network.ApiClient
import com.miqdad.e_news.data.TopHeadlineResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchViewModel(application: Application): AndroidViewModel(application) {

    var isLoading = MutableLiveData<Boolean>()
    var isError = MutableLiveData<Throwable>()
    val searchResponse = MutableLiveData<TopHeadlineResponse>()

    //untuk mendapat data dari parameter query
    fun getDataByQuery(responseHandler : (TopHeadlineResponse) -> Unit, errorHandler : (Throwable) -> Unit, query: String){
        ApiClient.getApiService().getNewsBySearch(query)
            //membuat background thread / asnycronus
            .subscribeOn(Schedulers.io())
            //menentukan dimana thread akan dibuat
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseHandler(it)
            },{
                errorHandler(it)
            })
    }

    //untuk mendapatkan data dari parameter category
    fun getDataCategory(responseHandler : (TopHeadlineResponse) -> Unit, errorHandler : (Throwable) -> Unit, category: String){
        ApiClient.getApiService().getCategory(category )
            //membuat background thread / asnycronus
            .subscribeOn(Schedulers.io())
            //menentukan dimana thread akan dibuat
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseHandler(it)
            },{
                errorHandler(it)
            })
    }

    fun getNewsCategory(category: String?) {
        isLoading.value = true
        category?.let {
            getDataCategory({
                isLoading.value = false
                searchResponse.value = it
            }, {
                isLoading.value = false
                isError.value = it
            },it)
        }
    }

    fun getNewsBySearch(query: String?) {
        isLoading.value = true
        query?.let {
            getDataByQuery({
                isLoading.value = false
                searchResponse.value = it
            }, {
                isLoading.value = false
                isError.value = it
            },
                it
            )
        }
    }
}