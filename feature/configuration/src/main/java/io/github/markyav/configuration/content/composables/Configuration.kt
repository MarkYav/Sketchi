package io.github.markyav.configuration.content.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Configuration(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    OutlinedCard(
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
//    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(start = 8.dp)
        ) {
            Icon(Icons.Default.AccessTime, contentDescription = null)
            Text(
                title,
//                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(12.dp)
            )
        }
        content()
    }
/*    Column(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(4.dp))
    ) {
        Text(title)
        content()
    }*/
}

@Preview(device = "id:Nexus One", showSystemUi = true)
@Composable
fun ConfigurationPreview() {
//    MaterialTheme {
        Configuration("Title") {
            Slider(
                value = 30f,
                onValueChange = {  },
                valueRange = 10f..50f,
                steps = 3,
            )
        }
//    }
}
