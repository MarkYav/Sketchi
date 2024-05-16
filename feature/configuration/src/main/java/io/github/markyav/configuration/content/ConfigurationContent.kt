package io.github.markyav.configuration.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import io.github.markyav.configuration.component.ConfigurationComponent
import io.github.markyav.configuration.component.ConfigurationComponentTest
import io.github.markyav.configuration.content.composables.Configuration
import io.github.markyav.configuration.content.composables.DrawInput
import io.github.markyav.drawbox.box.DrawBox

@Composable
fun ConfigurationContent(component: ConfigurationComponent, modifier: Modifier = Modifier) {
    val prompt by component.prompt.collectAsState()
    val scribble by component.scribble.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            DrawInput(
                scribble = scribble,
                onOpenDrawing = component::drawScribble,
                onRemoveDrawing = component::deleteScribble,
                onCreateDrawingClicked = component::drawScribble,
                onUploadDrawingClicked = component::selectFromSaved
            )
            OutlinedTextField(
                value = prompt,
                onValueChange = { newPrompt -> component.updatePrompt(newPrompt) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                minLines = 3,
                maxLines = 6,
                label = { Text(text = "Enter image description") },
                placeholder = { Text(text = "A photo of a brightly colored turtle") }
            )
            Configuration(title = "Runtime") {
                Slider(
                    value = 30f,
                    onValueChange = {  },
                    valueRange = 10f..50f,
                    steps = 3,
                )
            }
            Configuration(title = "Seed") { }
            Configuration(title = "guidanceScale") { }
            Configuration(title = "additionalPrompt") { }
            Configuration(title = "negativePrompt") { }
        }
        Column (modifier = Modifier.padding(16.dp)) {
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = component::generate,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Generate")
            }
        }
    }
}

@Preview
@Composable
fun ConfigurationContentPreview() {
    MaterialTheme {
        ConfigurationContent(component = ConfigurationComponentTest())
    }
}