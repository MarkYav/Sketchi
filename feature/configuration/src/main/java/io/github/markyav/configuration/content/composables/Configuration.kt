package io.github.markyav.configuration.content.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//fun LazyListScope.Configuration(
//    title: String,
//    modifier: Modifier = Modifier,
//    content: @Composable () -> Unit
//) {
//    item {
//        OutlinedCard(
//            modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = modifier.padding(start = 8.dp)
//            ) {
//                Icon(Icons.Default.AccessTime, contentDescription = null)
//                Text(
//                    title,
////                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier.padding(12.dp)
//                )
//            }
//            content()
//        }
//    }
//}

fun LazyListScope.Configuration(
    title: String,
    modifier: Modifier = Modifier,
    extra: @Composable () -> Unit = { },
    content: @Composable () -> Unit,
) {
    item {
        Column(modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
                extra()
//            Switch(checked = isChecked, onCheckedChange = onCheckedChange)
            }
            content()
        }
    }
}

@Preview(device = "id:Nexus One", showSystemUi = true)
@Composable
fun ConfigurationPreview() {
    MaterialTheme {
        LazyColumn {
            Configuration("Title") {
                Slider(
                    value = 30f,
                    onValueChange = { },
                    valueRange = 10f..50f,
                    steps = 3,
                )
            }
        }
    }
}
