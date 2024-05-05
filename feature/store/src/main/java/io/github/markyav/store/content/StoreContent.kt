package io.github.markyav.store.content

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import io.github.markyav.store.component.StoreComponent
import io.github.markyav.store.content.composables.StoreItemList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreContent(component: StoreComponent, modifier: Modifier = Modifier) {
    val savedSketchItems by component.savedSketches.subscribeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Saved sketches") },
                navigationIcon = {
                    IconButton(onClick = component::onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        StoreItemList(items = savedSketchItems, component::select, modifier.padding(it))
    }
}