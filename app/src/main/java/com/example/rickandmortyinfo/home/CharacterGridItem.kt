package com.example.rickandmortyinfo.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.rickandmortyinfo.preview.RickAndMortyPreview
import com.example.rickandmortyinfo.ui.shared.RickAndMortyImage
import com.example.rickandmortyinfo.ui.theme.RickAndMortyInfoTheme

@Composable
fun CharacterGridItem(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = modifier.padding(vertical = 12.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            RickAndMortyImage(
                modifier = Modifier.height(128.dp),
                context = context,
                imageUrl = imageUrl,
                name = name,
                contentScale = ContentScale.FillWidth,
                onClick = onClick
            )
            Text(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                text = name,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@RickAndMortyPreview
@Composable
private fun Preview_CharacterGridItem() {
    RickAndMortyInfoTheme {
        CharacterGridItem(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
            name = "Rick And Morty Morty Morty",
            imageUrl = "",
            onClick = {}
        )
    }
}