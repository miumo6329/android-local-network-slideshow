package com.example.android_local_network_slideshow.ui

import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_local_network_slideshow.data.AlbumViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun AlbumScreen(viewModel: AlbumViewModel = viewModel()) {

    val albums = viewModel.albums
    val mediaItems = viewModel.mediaItems

    LaunchedEffect(Unit) {
        viewModel.loadAlbums()
    }

    if (mediaItems.isNotEmpty()) {

        val firstItem = mediaItems.first()

        Box(modifier = Modifier.fillMaxSize()) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${firstItem.baseUrl}?w=1920&h=1080")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "${albums.first().title} (${albums.first().mediaItemsCount})",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(8.dp),
                color = Color.White
            )
        }

    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...")
        }
    }
}