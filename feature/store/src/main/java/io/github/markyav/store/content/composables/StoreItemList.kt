package io.github.markyav.store.content.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import io.github.markyav.data.repository.SavedControlNetParamsDto
import io.github.markyav.domain.ControlNetParams
import io.github.markyav.domain.DiffusionModelParams
import io.github.markyav.ui.util.AndroidPreviewDevices
import java.time.LocalDateTime

@Composable
fun StoreItemList(
    items: List<SavedControlNetParamsDto>,
    onClick: (ControlNetParams) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(items) {
            StoreItem(item = it, onClick = { onClick(it.controlNetParams) })
        }
    }
}

@AndroidPreviewDevices
@Composable
fun StoreItemListPreview() {
    val list = mutableListOf<SavedControlNetParamsDto>()
    repeat(5) {
        list.add(
            SavedControlNetParamsDto(
                id = 0,
                createdTime = LocalDateTime.now(),
                controlNetParams = ControlNetParams(
                    scribble = ImageBitmap(512, 512),
                    diffusionModelParams = DiffusionModelParams(prompt = "a photo of a cat on a roof with a lot of tasty snacks")
                )
            )
        )
    }
    MaterialTheme {
        Surface {
            StoreItemList(list, { })
        }
    }
}