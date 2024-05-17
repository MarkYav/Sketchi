package io.github.markyav.configuration.content.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

fun LazyListScope.SeedConfiguration(
    value: Int?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Configuration(
        title = "Seed",
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = value?.toString() ?: "",
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            placeholder = { Text(text = "Random seed when empty") },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}