package com.example.android_local_network_slideshow.data.network

import com.example.android_local_network_slideshow.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path

interface PhotoApi {

    @GET("albums")
    suspend fun getAlbums(): AlbumsResponse

    @GET("albums/{id}/mediaItems")
    suspend fun getMediaItems(
        @Path("id") albumId: String
    ): MediaItemsResponse
}