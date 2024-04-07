package io.github.markyav.ui.util

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Preview(
    device = Devices.PHONE,
    showSystemUi = true,
    name = "Devices - Phone",
)
@Preview(
    device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=300",
    showSystemUi = true,
    name = "Devices - Tablet",
)
@Preview(
    device = "spec:width=1280dp,height=800dp,dpi=300,orientation=portrait",
    showSystemUi = true,
    name = "Devices - Tablet landscape",
)
/*@Preview(
    device = "spec:parent=Nexus 7,orientation=landscape",
    showSystemUi = true,
    name = "Devices - Tablet landscape",
)*/
@Preview(
    device = Devices.DESKTOP,
    showSystemUi = true,
    name = "Devices - Desktop",
)
annotation class AndroidPreviewDevices

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun calculatePreviewSize(): WindowSizeClass {
    val config = LocalConfiguration.current
    val size = DpSize(config.screenWidthDp.dp, config.screenHeightDp.dp)
    return WindowSizeClass.calculateFromSize(size)
}