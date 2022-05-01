package com.example.youtubesearcher.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtubesearcher.api.RetrofitInstance
import com.example.youtubesearcher.models.YtubeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeViewModel : ViewModel() {
    val searchList = MutableLiveData<YtubeResponse?>()
    private var nextPageToken: String? = null
    var isNewSearch = false
    var searchQuery = ""
    init {
        getVideoList("skydiving")
    }

    fun getVideoList(search:String = searchQuery,newSearch:Boolean = false){

        isNewSearch = newSearch
        searchQuery = search
        val client = RetrofitInstance.getService().getVideo("snippet",search, nextPageToken)
        client.enqueue(object : Callback<YtubeResponse>{
            override fun onResponse(call: Call<YtubeResponse>, response: Response<YtubeResponse>) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        if (data.nextPageToken != null){
                            nextPageToken = data.nextPageToken
                        }
                        if (data.items.isNotEmpty()){
                            searchList.postValue(data)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<YtubeResponse>, t: Throwable) {

            }


        })
    }


}