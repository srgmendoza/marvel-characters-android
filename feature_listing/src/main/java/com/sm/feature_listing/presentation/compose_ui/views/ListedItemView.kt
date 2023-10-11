package com.sm.feature_listing.presentation.compose_ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sm.feature_listing.R
import com.sm.feature_listing.presentation.models.ListedCharacter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListedItemView(item: ListedCharacter, onClick: (ListedCharacter) -> Unit) {
    val paddingModifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
    Card(elevation = 10.dp,
        modifier = paddingModifier,
        onClick = { onClick(item) }
    ) {
        Column {
            AsyncImage(
                model = item.imageUrl,
                placeholder = painterResource(id = R.drawable.marvel_placeholder),
                contentDescription = "${item.name} image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.White),
                contentScale = ContentScale.Fit
            )
            Text(text = item.id.toString())
            Text(text = item.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }

}

@Preview
@Composable
//preview doesn't accept parameters
fun ColoredTextPreview() = ListedItemView(getExampleCharacter(), {})
private fun getExampleCharacter() = ListedCharacter(
    id = 1,
    name = "name",
    description = "description",
    imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/b/b0/4ce59ea2103ac.jpg"
)