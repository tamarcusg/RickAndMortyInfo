package com.example.rickandmortyinfo.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.rickandmortyinfo.R
import com.example.rickandmortyinfo.preview.RickAndMortyPreview
import com.example.rickandmortyinfo.repository.CharacterData
import com.example.rickandmortyinfo.repository.toFormattedDate
import com.example.rickandmortyinfo.ui.shared.RickAndMortyImage
import com.example.rickandmortyinfo.ui.theme.RickAndMortyInfoTheme

@Composable
internal fun DetailScreen(
    modifier: Modifier = Modifier,
    characterData: CharacterData
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.padding(bottom = 12.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            RickAndMortyImage(
                context = context,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                imageUrl = characterData.imageUrl,
                name = characterData.name,
                contentScale = ContentScale.FillBounds
            )
        }
        DetailTextEntry(
            labelResource = R.string.detail_label_species,
            entry = characterData.species
        )
        DetailTextEntry(
            labelResource = R.string.detail_label_status,
            entry = characterData.status
        )
        DetailTextEntry(
            labelResource = R.string.detail_label_origin,
            entry = characterData.origin
        )
        if (!characterData.type.isNullOrEmpty()) {
            DetailTextEntry(
                labelResource = R.string.detail_label_type,
                entry = characterData.type
            )
        }
        DetailTextEntry(
            labelResource = R.string.detail_label_date_created,
            entry = characterData.createdDate.toFormattedDate()
        )
    }
}

@Composable
internal fun DetailTextEntry(labelResource: Int, entry: String) {
    Column {
        Row {
            Text(
                text = stringResource(labelResource) + ": ",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = entry
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@RickAndMortyPreview
@Composable
private fun Preview_CharacterDetailsScreen() {
    RickAndMortyInfoTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer)) {
            DetailScreen(
                characterData = CharacterData(
                    id = 2,
                    name = "Morty Smith",
                    imageUrl = "",
                    species = "Human",
                    status = "Alive",
                    origin = "Earth",
                    type = "",
                    createdDate = "2017-11-04T18:50:21.651Z"
                )
            )
        }
    }
}