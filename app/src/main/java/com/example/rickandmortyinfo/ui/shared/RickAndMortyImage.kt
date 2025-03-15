package com.example.rickandmortyinfo.ui.shared

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.rickandmortyinfo.R
import com.example.rickandmortyinfo.preview.RickAndMortyPreview
import com.example.rickandmortyinfo.ui.theme.RickAndMortyInfoTheme

@Composable
fun RickAndMortyImage(
    modifier: Modifier = Modifier,
    context: Context,
    imageUrl: String,
    name: String,
    contentScale: ContentScale = ContentScale.Crop,
    onClick: () -> Unit = {}
) {
    AsyncImage(
        modifier = modifier
            .clickable {
                onClick()
            },
        contentScale = contentScale,
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .error(R.drawable.ic_launcher_background)
            .placeholder(R.drawable.ic_launcher_background)
            .transformations(RoundedCornersTransformation(12f))
            .build(),
        contentDescription = stringResource(R.string.character_grid_image_description, name)
    )
}

@RickAndMortyPreview
@Composable
fun Preview_RickAndMortyImage() {
    RickAndMortyInfoTheme {
        RickAndMortyImage(
            modifier = Modifier.size(128.dp),
            context = LocalContext.current,
            imageUrl = "",
            name = "Morty",
            onClick = {},
        )
    }
}