package io.github.markyav.store.component

import io.github.markyav.data.repository.SavedControlNetParamsDto
import io.github.markyav.domain.ControlNetParams
import kotlinx.coroutines.flow.StateFlow

interface StoreComponent {
    val savedSketches: StateFlow<List<SavedControlNetParamsDto>>

    fun select(params: ControlNetParams)
    fun onBackClick()
}