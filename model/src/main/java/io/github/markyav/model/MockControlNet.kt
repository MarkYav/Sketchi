package io.github.markyav.model

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap

class MockControlNet : ControlNet {
    override suspend fun process(params: ControlNet.ControlNetParams): ImageBitmap? {
        return invert(params.scribble.asAndroidBitmap())?.asImageBitmap()
    }

    private fun invert(original: Bitmap): Bitmap? {
        val RGB_MASK = 0x00FFFFFF
        val inversion = original.copy(Bitmap.Config.ARGB_8888, true)

        val width = inversion.width
        val height = inversion.height
        val pixels = width * height

        val pixel = IntArray(pixels)
        inversion.getPixels(pixel, 0, width, 0, 0, width, height)

        for (i in 0 until pixels) {
            pixel[i] = pixel[i] xor RGB_MASK
        }
        inversion.setPixels(pixel, 0, width, 0, 0, width, height)

        return inversion
    }
}