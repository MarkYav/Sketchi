package io.github.markyav.store.content.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.markyav.data.repository.SavedControlNetParamsDto
import io.github.markyav.domain.ControlNetParams
import io.github.markyav.domain.DiffusionModelParams
import io.github.markyav.ui.util.AndroidPreviewDevices
import io.github.markyav.ui.util.toTimeAgo
import java.time.LocalDateTime

@Composable
fun StoreItem(
    item: SavedControlNetParamsDto,
    onClick: () -> Unit,
) {
    val isCollapsed = remember { mutableStateOf(true) }

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Image(
                item.controlNetParams.scribble,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(96.dp).clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column(
                modifier = Modifier.height(96.dp).weight(1f),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = item.controlNetParams.diffusionModelParams.prompt,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "Created ${item.createdTime.toTimeAgo()}",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            IconButton(onClick = { isCollapsed.value = !isCollapsed.value }) {
                if (isCollapsed.value) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                } else {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = null)
                }
            }
        }
        if (!isCollapsed.value) {
            Column(Modifier.padding(8.dp)) {
                Text(text = "Runtime: " + item.controlNetParams.diffusionModelParams.numberOfSteps)
                Text(text = "Seed: " + (item.controlNetParams.diffusionModelParams.seed ?: "random"))
                Text(text = "Guidance scale: " + item.controlNetParams.diffusionModelParams.guidanceScale)
                Text(text = "Additional prompt: " + item.controlNetParams.diffusionModelParams.additionalPrompt)
                Text(text = "Negative prompt: " + item.controlNetParams.diffusionModelParams.negativePrompt)
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
                    item = SavedControlNetParamsDto(
                        id = 0,
                        createdTime = LocalDateTime.now(),
                        controlNetParams = ControlNetParams(
                            scribble = ImageBitmap(512, 512),
                            diffusionModelParams = DiffusionModelParams(prompt = "a photo of a cat on a roof with a lot of tasty snacks")
                        )
                    ),
                    { }
                )
            }
        }
    }
}