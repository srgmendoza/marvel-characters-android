package com.sm.feature_detail.presentation.composable_ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.core_utils.openTab
import com.sm.feature_detail.presentation.models.CharacterDetail

@Composable
fun DetailsView(
    item: CharacterDetail,
    onSeeMoreClick: (String) -> Unit,
    onGotoTestClick: () -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        SubcomposeAsyncImage(
            model = item.imageUrl,
            contentDescription = "${item.name} image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.White),
            contentScale = ContentScale.Fit
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                ShimmerView()
            } else {
                SubcomposeAsyncImageContent()
                ImageTitle(id = item.id.toString(), name = item.name)
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        SeeMoreButton(item.moreContentUrl, onSeeMoreClick)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        GotoTestScreenButton(onGotoTestClick)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        GotoTestScreenButton(onGotoTestClick)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        GotoTestScreenButton(onGotoTestClick)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        GotoTestScreenButton(onGotoTestClick)


        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        GotoTestScreenButton(onGotoTestClick)


        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )

        GotoTestScreenButton(onGotoTestClick)
    }

}

@Composable
@Preview
private fun ImageTitle(id: String = "123456", name: String = "A Cool hero name") {
    Box(
        Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.Black
                )
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,

            ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Text(text = id, fontSize = 24.sp, color = Color.LightGray)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            Text(
                textAlign = TextAlign.Left,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                text = name
            )
        }
    }
}

@Composable
@Preview
fun SeeMoreButton(moreContentUrl: String = "", onSeeMoreClick: (String) -> Unit = {}) {
    val context = LocalContext.current
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { openTab(context, moreContentUrl) }) {
            Text("See More")
        }
    }
}

@Composable
@Preview
fun GotoTestScreenButton(onClick: () -> Unit = {}) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Button(onClick = onClick) {
            Text("Goto Test Screen")
        }
    }
}