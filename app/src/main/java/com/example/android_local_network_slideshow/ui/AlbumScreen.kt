package com.example.android_local_network_slideshow.ui

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_local_network_slideshow.data.AlbumViewModel

@Composable
fun AlbumScreen(viewModel: AlbumViewModel = viewModel()) {

    LaunchedEffect(Unit) {
        viewModel.loadAlbums()
    }

    LazyColumn {
        items(viewModel.albums) { album ->
            Text(
                text = "${album.title} (${album.mediaItemsCount})",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}