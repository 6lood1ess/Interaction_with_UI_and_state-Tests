package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                ArtSpaceApp()
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    var currentArtwork by remember { mutableIntStateOf(1) }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWide = maxWidth > 600.dp

        if (isWide) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ArtworkImage(currentArtwork, Modifier.weight(1f))
                ArtworkInfoColumn(
                    currentArtwork,
                    onPrevious = { if (currentArtwork > 1) currentArtwork-- },
                    onNext = { if (currentArtwork < 3) currentArtwork++ },
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ArtworkImage(currentArtwork, Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(45.dp))
                ArtworkCard(currentArtwork)
                Spacer(modifier = Modifier.height(14.dp))
                NavigationButtons(
                    onPrevious = { if (currentArtwork > 1) currentArtwork-- },
                    onNext = { if (currentArtwork < 3) currentArtwork++ }
                )
            }
        }
    }
}

@Composable
private fun ArtworkImage(currentArtwork: Int, modifier: Modifier = Modifier) {
    val imageRes = when (currentArtwork) {
        1 -> R.drawable.image1
        2 -> R.drawable.image2
        3 -> R.drawable.image3
        else -> R.drawable.image1
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .border(1.dp, Color.Black)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun ArtworkCard(currentArtwork: Int) {
    val (title, artist, year) = when (currentArtwork) {
        1 -> Triple("Moonlit Night on Capri", "Ivan Aivazovsky", "1841")
        2 -> Triple("Moonlit Night on the Dnieper", "Arkhip Kuindzhi", "1882")
        3 -> Triple("Morning in a Pine Forest", "Ivan Shishkin", "1889")
        else -> Triple("Unknown", "Unknown", "Unknown")
    }

    Box(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "$artist ($year)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun NavigationButtons(onPrevious: () -> Unit, onNext: () -> Unit) {
    val buttonColor = colorResource(id = R.color.button_color)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(buttonColor)
                .border(1.dp, Color.Black)
                .clickable { onPrevious() }
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.previous),
                color = Color.Black,
                fontSize = 19.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .background(buttonColor)
                .border(1.dp, Color.Black)
                .clickable { onNext() }
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.next),
                color = Color.Black,
                fontSize = 19.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ArtworkInfoColumn(
    currentArtwork: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ArtworkCard(currentArtwork)
        Spacer(modifier = Modifier.height(14.dp))
        NavigationButtons(onPrevious = onPrevious, onNext = onNext)
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceAppTheme {
        ArtSpaceApp()
    }
}