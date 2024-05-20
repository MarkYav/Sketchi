package io.github.markyav.store.component

import com.arkivanov.decompose.value.Value
import io.github.markyav.data.repository.SavedControlNetParamsDto
import io.github.markyav.domain.ControlNetParams

interface StoreComponent {
    val savedSketches: Value<List<SavedControlNetParamsDto>>

    fun select(params: ControlNetParams)
    fun onBackClick()
}