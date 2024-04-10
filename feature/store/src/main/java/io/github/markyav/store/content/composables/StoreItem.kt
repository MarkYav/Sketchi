package io.github.markyav.store.content.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.github.markyav.store.item.SavedSketchItem
import io.github.markyav.ui.util.AndroidPreviewDevices

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreItem(
    item: SavedSketchItem,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column {
            Image(
                item.sketch,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            )
            Column(Modifier.padding(16.dp)) {
                Text(text = "edited ${item.createdTime}")
            }
        }
    }
}

@AndroidPreviewDevices
@Composable
fun StoreItemPreview() {
    MaterialTheme {
        Surface {
            Box(modifier = Modifier.padding(100.dp)) {
                StoreItem(
                    SavedSketchItem(
                        sketch = ImageBitmap(512, 512),
                        createdTime = "Mon 16 2023",
                    ),
                    { }
                )
            }
        }
    }
}