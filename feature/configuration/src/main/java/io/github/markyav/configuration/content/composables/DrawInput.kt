package io.github.markyav.configuration.content.composables

import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DrawInput(
    scribble: ImageBitmap?,
    onOpenDrawing: () -> Unit,
    onRemoveDrawing: () -> Unit,
    onCreateDrawingClicked: () -> Unit,
    onUploadDrawingClicked: (ImageBitmap) -> Unit,
    modifier: Modifier = Modifier,
) {
    val contextResolver = LocalContext.current.contentResolver
    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            // https://stackoverflow.com/questions/56651444/deprecated-getbitmap-with-api-29-any-alternative-codes
            val bitmap = MediaStore.Images.Media.getBitmap(contextResolver, uri)
            onUploadDrawingClicked(bitmap.asImageBitmap())
        }
    }

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (scribble != null) {
            Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Image(
                    bitmap = scribble,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable(onClick = onOpenDrawing::invoke),
                )
                IconButton(
                    onClick = onRemoveDrawing,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }
        } else {
            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Draw", modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onCreateDrawingClicked),
                    textAlign = TextAlign.Center,
                )
                VerticalDivider(modifier = Modifier.height(32.dp))
                Text(text = "Upload sketch", modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = { pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DrawInputPreview() {
    MaterialTheme {
        DrawInput(
            scribble = ImageBitmap(512, 512),
            {},
            {},
            {},
            {},
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun DrawInputPreview2() {
    MaterialTheme {
        DrawInput(
            scribble = null,
            {},
            {},
            {},
            {},
        )
    }
}