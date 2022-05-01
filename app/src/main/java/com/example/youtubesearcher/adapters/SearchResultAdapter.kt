package com.example.youtubesearcher.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtubesearcher.R
import com.example.youtubesearcher.models.YoutubeDataModel
import com.example.youtubesearcher.models.YtubeResponse
import com.jabirdev.youtubeapikotlin.diffutils.VideoDiffUtil

class SearchResultAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var searchResults:MutableList<YoutubeDataModel> = mutableListOf<YoutubeDataModel>()

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val videoThumbnail:ImageView = view.findViewById(R.id.imgThumbnail)
        val videoTitle:TextView = view.findViewById(R.id.txtTitle)
        val videoDate:TextView = view.findViewById(R.id.txtPublished)
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VideoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_video,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){
            is VideoViewHolder->{
                holder.videoTitle.text = searchResults[position].snippet.title
                holder.videoDate.text = searchResults[position].snippet.publishTime
                Glide.with(holder.itemView)
                    .load(searchResults[position].snippet.thumbnails.default.url).into(holder.videoThumbnail)
            }
        }
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<YoutubeDataModel>, rv: RecyclerView, isNewSearch:Boolean){
        if(!isNewSearch)
        {
            val videoDiff = VideoDiffUtil(searchResults, newList)
            val diff = DiffUtil.calculateDiff(videoDiff)
            searchResults.addAll(newList)
            diff.dispatchUpdatesTo(this)
            rv.scrollToPosition(searchResults.size - newList.size)
        }
        else
        {
            searchResults = newList as MutableList<YoutubeDataModel>
            notifyDataSetChanged()
        }
    }

}