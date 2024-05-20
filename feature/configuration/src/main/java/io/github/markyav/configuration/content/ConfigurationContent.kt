package io.github.markyav.configuration.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.markyav.configuration.component.ConfigurationComponent
import io.github.markyav.configuration.component.ConfigurationComponentTest
import io.github.markyav.configuration.content.composables.Configuration
import io.github.markyav.configuration.content.composables.DrawInput
import io.github.markyav.configuration.content.composables.SeedConfiguration

@Composable
fun ConfigurationContent(component: ConfigurationComponent, modifier: Modifier = Modifier) {
    val isGenerationEnabled by component.isGenerationEnabled.collectAsState()

    val scribble by component.scribble.collectAsState()
    val prompt by component.prompt.collectAsState()
    val runtime by component.runtime.collectAsState()
    val seed by component.seed.collectAsState()
    val guidanceScale by component.guidanceScale.collectAsState()
    val additionalPrompt by component.additionalPrompt.collectAsState()
    val negativePrompt by component.negativePrompt.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                Row(
                    Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextButton(onClick = component::selectFromSaved) {
                        Text(text = "Select from saved")
                    }
                    TextButton(
                        onClick = component::saveConfiguration,
                        enabled = isGenerationEnabled,
                    ) {
                        Text(text = "Save current")
                    }
                }
            }
            item {
                DrawInput(
                    scribble = scribble,
                    onOpenDrawing = component::drawScribble,
                    onRemoveDrawing = component::deleteScribble,
                    onCreateDrawingClicked = component::drawScribble,
                    onUploadDrawingClicked = component::selectFromSaved
                )
            }
            item {
                OutlinedTextField(
                    value = prompt,
                    onValueChange = { newPrompt -> component.updatePrompt(newPrompt) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    minLines = 3,
                    maxLines = 6,
                    label = { Text(text = "Enter image description") },
                    placeholder = { Text(text = "A photo of a brightly colored turtle") },
                    shape = RoundedCornerShape(16.dp),
                )
            }
            Configuration(
                title = "Runtime",
                extra = { Text(text = runtime.toInt().toString()) },
            ) {
                Slider(
                    value = runtime,
                    onValueChange = component::updateRuntime,
                    valueRange = 10f..50f,
                    steps = 3,
                )
            }
            SeedConfiguration(
                value = seed,
                onValueChange = component::updateSeed,
            )
            Configuration(
                title = "Guidance scale",
                extra = { Text(text = guidanceScale.toString()) },
            ) {
                Slider(
                    value = guidanceScale,
                    onValueChange = component::updateGuidanceScale,
                    valueRange = 0f..10f,
                )
            }
            Configuration(title = "Additional prompt") {
                OutlinedTextField(
                    value = additionalPrompt,
                    onValueChange = { newAdditionalPrompt -> component.updateAdditionalPrompt(newAdditionalPrompt) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                )
            }
            Configuration(title = "Negative prompt") {
                OutlinedTextField(
                    value = negativePrompt,
                    onValueChange = { newNegativePrompt -> component.updateNegativePrompt(newNegativePrompt) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                )
            }
        }
        Column (modifier = Modifier.padding(16.dp)) {
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = component::generate,
                modifier = Modifier.fillMaxWidth(),
                enabled = isGenerationEnabled
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