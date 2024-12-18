package com.example.mynews

import android.app.DownloadManager.Query
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsViewModel : ViewModel(){
    private val _articles=MutableLiveData<List<Article>>()
    val articles:LiveData<List<Article>> = _articles

    init{
        fetchNewsTopheadLines()
    }
    fun fetchNewsTopheadLines(category: String="GENERAL"){
        val newsApiClient = NewsApiClient(constant.apikey)
        val request= TopHeadlinesRequest.Builder().language("en").category(category).build()
        newsApiClient.getTopHeadlines(request,object : NewsApiClient.ArticlesResponseCallback{
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let{
                    _articles.postValue(it)
                }

            }

            override fun onFailure(throwable: Throwable?) {
                if (throwable != null) {
                    throwable.localizedMessage?.let { Log.i("NewsAPI Response Failed", it) }
                }

            }

        })
    }
    fun fetchEverythingwithQuery(query: String){
        val newsApiClient = NewsApiClient(constant.apikey)
        val request= EverythingRequest.Builder().language("en").q(query).build()
        newsApiClient.getEverything(request,object : NewsApiClient.ArticlesResponseCallback{
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let{
                    _articles.postValue(it)
                }

            }

            override fun onFailure(throwable: Throwable?) {
                if (throwable != null) {
                    throwable.localizedMessage?.let { Log.i("NewsAPI Response Failed", it) }
                }

            }

        })
    }
}
