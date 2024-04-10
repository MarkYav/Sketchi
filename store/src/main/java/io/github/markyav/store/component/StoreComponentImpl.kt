package io.github.markyav.store.component

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import io.github.markyav.data.room.SketchRepositoryImpl
import io.github.markyav.store.item.SavedSketchItem
import io.github.markyav.ui.util.toTimeAgo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoreComponentImpl(
    componentContext: ComponentContext,
    applicationContext: Context,
    val onSelect: (ImageBitmap) -> Unit,
    val onBackClick: () -> Unit,
) : StoreComponent, ComponentContext by componentContext {
    private val repository = SketchRepositoryImpl.getSketchRepositoryImpl(applicationContext)
    override val savedSketches: MutableValue<List<SavedSketchItem>> = MutableValue(emptyList())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            savedSketches.value = repository.getAll().map {
                SavedSketchItem(
                    sketch = it.sketch.asImageBitmap(),
                    createdTime = it.createdTime.toTimeAgo()
                )
            }
        }
    }

    override fun select(bitmap: ImageBitmap) {
        onSelect(bitmap)
    }

    override fun onBackClick() {
        this.onBackClick.invoke()
    }
}