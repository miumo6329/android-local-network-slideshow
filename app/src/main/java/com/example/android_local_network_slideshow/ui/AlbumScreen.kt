package com.example.android_local_network_slideshow.ui

import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_local_network_slideshow.data.AlbumViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay


@Composable
fun AlbumScreen(viewModel: AlbumViewModel = viewModel()) {

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
                },
                label = ""
            ) { item ->

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("${item.baseUrl}?w=1920&h=1080")
                            .crossfade(true)
                            .allowHardware(false)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )

                    // EXIF日付
                    item.mediaMetadata?.creationTime?.let { rawDate ->

                        val formattedDate = formatExifDate(rawDate)

                        Text(
                            text = formattedDate,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(24.dp)
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }

    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            LoadingText()
        }
    }
}

@Composable
fun LoadingText() {

    var dotCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            dotCount = (dotCount + 1) % 4
        }
    }

    val dots = ".".repeat(dotCount)
    val paddedDots = dots.padEnd(3, ' ')

    Text(
        text = "Loading$paddedDots",
        color = Color.White,
        fontSize = 28.sp,
        fontFamily = FontFamily.Monospace
    )
}

fun formatExifDate(raw: String?): String {
    if (raw == null) return ""

    return try {
        val datePart = raw.substring(0, 10) // YYYY-MM-DD
        datePart.replace("-", "/")
    } catch (e: Exception) {
        ""
    }
}