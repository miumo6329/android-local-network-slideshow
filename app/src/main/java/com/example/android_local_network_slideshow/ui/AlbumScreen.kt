package com.example.android_local_network_slideshow.ui

import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_local_network_slideshow.data.AlbumViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay


@Composable
fun AlbumScreen(viewModel: AlbumViewModel = viewModel()) {

    val albums = viewModel.albums
    val mediaItems = viewModel.mediaItems

    LaunchedEffect(Unit) {
        viewModel.loadAlbums()
    }

    if (mediaItems.isNotEmpty()) {

        var currentIndex by remember { mutableStateOf(0) }

        // 自動切替
        LaunchedEffect(mediaItems) {
            while (true) {
                delay(10000)
                currentIndex = (currentIndex + 1) % mediaItems.size
            }
        }

        val currentItem = mediaItems[currentIndex]

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // フェードして画像切替
            AnimatedContent(
                targetState = currentItem,
                transitionSpec = {
                    fadeIn(tween(3000)) togetherWith fadeOut(tween(3000))
                }
            ) { item ->

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("${item.baseUrl}?w=1920&h=1080")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
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
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Loading...",
                fontSize = 24.sp,
                color = Color.White)
        }
    }
}