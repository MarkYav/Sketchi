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
import io.github.markyav.store.item.SavedSketchItem
import io.github.markyav.ui.util.AndroidPreviewDevices

@Composable
fun StoreItemList(items: List<SavedSketchItem>, onClick: (ImageBitmap) -> Unit, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(items) {
            StoreItem(item = it, onClick = { onClick(it.sketch) })
        }
    }
}

@AndroidPreviewDevices
@Composable
fun StoreItemListPreview() {
    val list = mutableListOf<SavedSketchItem>()
    repeat(5) {
        list.add(
            SavedSketchItem(
                sketch = ImageBitmap(512, 512),
                createdTime = "16.04.2023",
            )
        )
    }
    MaterialTheme {
        Surface {
            StoreItemList(list, { })
        }
    }
}