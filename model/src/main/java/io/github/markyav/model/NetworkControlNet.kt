package io.github.markyav.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import io.github.markyav.domain.ControlNet
import io.github.markyav.domain.ControlNetParams
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.readBytes
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import java.io.ByteArrayOutputStream

class NetworkControlNet : ControlNet {
    private val URL = "https://mayar000-test2.hf.space/generate"
    private val client = HttpClient() {
        install(Logging) {
            logger = Logger.SIMPLE
        }
        install(HttpTimeout) {
            requestTimeoutMillis = Long.MAX_VALUE //10_000
        }
    }

    override suspend fun process(params: ControlNetParams): ImageBitmap? {
        val stream = ByteArrayOutputStream()
        params.scribble.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()

        try {
            val response = client.post(URL) {
                setBody(MultiPartFormDataContent(
                    formData {
                        append(
                            key = "scribble",
                            value = byteArray,
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, "image/jpeg")
                                append(HttpHeaders.ContentDisposition, "filename=Mark.jpg")
                            }
                        )
                        append(key = "prompt", value = params.diffusionModelParams.prompt)
                        append(key = "num_inference_steps", value = params.diffusionModelParams.numberOfSteps)
                        append(key = "negative_prompt", value = params.diffusionModelParams.negativePrompt)
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