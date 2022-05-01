package com.jabirdev.youtubeapikotlin.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.youtubesearcher.models.YoutubeDataModel

class VideoDiffUtil(
    private val oldList: List<YoutubeDataModel>,
    private val newList: List<YoutubeDataModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldVideo = oldList[oldItemPosition]
        val newVideo = newList[newItemPosition]
        return oldVideo.snippet.title == newVideo.snippet.title
    }
}