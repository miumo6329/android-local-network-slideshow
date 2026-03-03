package com.example.android_local_network_slideshow.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_local_network_slideshow.data.model.*
import com.example.android_local_network_slideshow.data.network.ApiClient
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class AlbumViewModel : ViewModel() {

    private val api = ApiClient.api

    var albums by mutableStateOf<List<Album>>(emptyList())
        private set

    var mediaItems by mutableStateOf<List<MediaItem>>(emptyList())
        private set

    fun loadAlbums() {
        viewModelScope.launch {
            albums = api.getAlbums().albums

            if (albums.isNotEmpty()) {
                loadMediaItems(albums.first().id)
            }
        }
    }

    fun loadMediaItems(albumId: String) {
        viewModelScope.launch {
            try {
                val response = api.getMediaItems(albumId)
                mediaItems = response.mediaItems.shuffled()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}