package com.example.android_local_network_slideshow.data.model

data class AlbumsResponse(
    val albums: List<Album>
)

data class Album(
    val id: String,
    val title: String,
    val mediaItemsCount: Int
)
