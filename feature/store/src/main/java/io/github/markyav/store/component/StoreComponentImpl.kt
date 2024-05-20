package io.github.markyav.store.component

import android.content.Context
import com.arkivanov.decompose.ComponentContext
import io.github.markyav.data.repository.SavedControlNetParamsDto
import io.github.markyav.data.room.SavedControlNetParamsRepositoryImpl
import io.github.markyav.domain.ControlNetParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StoreComponentImpl(
    componentContext: ComponentContext,
    applicationContext: Context,
    val onSelect: (ControlNetParams) -> Unit,
    val onBackClick: () -> Unit,
) : StoreComponent, ComponentContext by componentContext {
    private val repository = SavedControlNetParamsRepositoryImpl.getSketchRepositoryImpl(applicationContext)
    override val savedSketches: MutableStateFlow<List<SavedControlNetParamsDto>> = MutableStateFlow(emptyList())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            savedSketches.value = repository.getAll().sortedByDescending { it.createdTime }
        }
    }

    override fun select(params: ControlNetParams) {
        onSelect(params)
    }

    override fun onBackClick() {
        this.onBackClick.invoke()
    }
}