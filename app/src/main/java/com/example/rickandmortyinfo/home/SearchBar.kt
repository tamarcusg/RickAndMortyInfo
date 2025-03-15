package com.example.rickandmortyinfo.home

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.rickandmortyinfo.R
import com.example.rickandmortyinfo.preview.RickAndMortyPreview
import com.example.rickandmortyinfo.ui.theme.RickAndMortyInfoTheme
import com.example.rickandmortyinfo.ui.theme.SearchBarShape

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchString: String,
    enabled: Boolean,
    onValueChanged: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier,
        value = searchString,
        onValueChange = onValueChanged,
        enabled = enabled,
        label = {
            Text(
                text = stringResource(R.string.search_bar_placeholder),
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
            focusManager.clearFocus()
        }),
        trailingIcon = {


            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.search_icon_description)
            )

        },
        shape = SearchBarShape,
        colors = TextFieldDefaults.colors().copy(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )
}

@RickAndMortyPreview
@Composable
private fun Preview_SearchBar() {
    RickAndMortyInfoTheme {
        SearchBar(
            searchString = "",
            enabled = true,
            onValueChanged = {}
        )
    }
}