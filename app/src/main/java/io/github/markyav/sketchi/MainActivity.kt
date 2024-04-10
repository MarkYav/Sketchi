package io.github.markyav.sketchi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import io.github.markyav.sketchi.component.RootComponentImpl
import io.github.markyav.sketchi.content.RootContent
import io.github.markyav.ui.theme.SketchiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        val root = RootComponentImpl(componentContext = defaultComponentContext(), applicationContext)
        setContent {
            SketchiTheme {
                Surface {
                    RootContent(component = root, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}