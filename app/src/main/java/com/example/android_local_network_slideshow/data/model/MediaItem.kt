package com.example.android_local_network_slideshow.data.model

data class MediaItemsResponse(
    val mediaItems: List<MediaItem>
)

data class MediaItem(
    val id: String,
    val baseUrl: String,
    val filename: String,
    val mimeType: String,
    val mediaMetadata: MediaMetadata?
)

data class MediaMetadata(
    val creationTime: String?,
    val width: Int?,
    val height: Int?
)
