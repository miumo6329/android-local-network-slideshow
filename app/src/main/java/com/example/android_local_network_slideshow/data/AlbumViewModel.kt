package com.example.android_local_network_slideshow.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_local_network_slideshow.data.model.*
import com.example.android_local_network_slideshow.data.network.ApiClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.compose.runtime.*

class AlbumViewModel : ViewModel() {

    private val api = ApiClient.api

    var albums by mutableStateOf<List<Album>>(emptyList())
        private set

    var mediaItems by mutableStateOf<List<MediaItem>>(emptyList())
        private set

    var isError by mutableStateOf(false)
        private set

    fun loadAlbums() {
        viewModelScope.launch {
            while (true) {
                try {
                    val response = api.getAlbums()
                    albums = response.albums
                    isError = false

                    if (albums.isNotEmpty()) {
                        loadMediaItems(albums.first().id)
                    }

                    break // 成功したらループを抜ける

                } catch (e: Exception) {
                    e.printStackTrace()
                    isError = true
                    delay(2000) // 2秒後に再試行
                }
            }
        }
    }

    fun loadMediaItems(albumId: String) {
        viewModelScope.launch {
            while (true) {
                try {
                    val response = api.getMediaItems(albumId)
                    mediaItems = response.mediaItems.shuffled()
                    isError = false
                    break // 成功したらループを抜ける
                } catch (e: Exception) {
                    e.printStackTrace()
                    isError = true
                    delay(2000) // 2秒後に再試行
                }
            }
        }
    }
}