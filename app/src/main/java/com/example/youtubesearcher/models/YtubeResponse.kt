package com.example.youtubesearcher.models

data class YtubeResponse(
    val nextPageToken: String?,
    val items :  List<YoutubeDataModel>
)
