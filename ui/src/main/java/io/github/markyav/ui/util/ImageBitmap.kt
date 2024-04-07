package io.github.markyav.ui.util

import android.content.Context
import android.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.ContextCompat

fun getBitmapFromImage(context: Context, drawable: Int): ImageBitmap {

    // on below line we are getting drawable
    val db = ContextCompat.getDrawable(context, drawable)

    // in below line we are creating our bitmap and initializing it.
    val bit = ImageBitmap(
        db!!.intrinsicWidth, db.intrinsicHeight//, ImageBitmap.Config.ARGB_8888
    )

    // on below line we are
    // creating a variable for canvas.
    val canvas = Canvas(bit.asAndroidBitmap())

    // on below line we are setting bounds for our bitmap.
    db.setBounds(0, 0, canvas.width, canvas.height)

    // on below line we are simply
    // calling draw to draw our canvas.
    db.draw(canvas)

    // on below line we are
    // returning our bitmap.
    return bit
}