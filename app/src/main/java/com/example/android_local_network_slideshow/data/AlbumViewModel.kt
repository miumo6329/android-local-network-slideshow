package com.example.android_local_network_slideshow.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_local_network_slideshow.data.model.*
import com.example.android_local_network_slideshow.data.network.ApiClient
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class AlbumViewModel : ViewModel() {

    var albums by mutableStateOf<List<Album>>(emptyList())
        private set

    var mediaItems by mutableStateOf<List<MediaItem>>(emptyList())
        private set

    fun loadAlbums() {
        viewModelScope.launch {
            albums = ApiClient.api.getAlbums().albums
        }
    }

    fun loadMediaItems(albumId: String) {
        viewModelScope.launch {
            mediaItems = ApiClient.api.getMediaItems(albumId).mediaItems
        }
    }
}