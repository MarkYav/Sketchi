package io.github.markyav.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.readBytes
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import java.io.ByteArrayOutputStream

class NetworkControlNet : ControlNet {
    private val URL = "http://10.0.2.2:5000/predict"
    private val client = HttpClient() {
        install(Logging) {
            logger = Logger.SIMPLE
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 10_000
        }
    }

    override suspend fun process(params: ControlNet.ControlNetParams): ImageBitmap? {
        val stream = ByteArrayOutputStream()
        params.scribble.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()

        try {
            val response = client.post(URL) {
                header(HttpHeaders.ContentType, "image/jpeg")
                setBody(MultiPartFormDataContent(
                    formData {
                        append(
                            key = "file",
                            value = byteArray,
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, "image/jpeg")
                                append(HttpHeaders.ContentDisposition, "filename=Mark.jpg")
                            }
                        )
                    }
                ))
            }

            return if (response.status.isSuccess()) {
                Log.i("TAG_aaa", "result returned")
                // Successful response (2xx status code)

                // Read the response body as a byte array
                val imageByteArray: ByteArray = response.readBytes()

                // Convert the byte array back to ImageBitmap
                val imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)

                return imageBitmap.asImageBitmap()
            } else {
                Log.i("TAG_aaa", "null returned: ${response.status}")
                null
            }
        } catch (e: Exception) {
            Log.i("TAG_aaa", "greeting: $e")
            return null
        }
    }
}