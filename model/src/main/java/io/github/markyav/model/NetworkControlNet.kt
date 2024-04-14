package io.github.markyav.model

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readBytes
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.Serializable
import io.ktor.http.isSuccess
import kotlinx.coroutines.delay
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class NetworkControlNet : ControlNet {
    private val URL = "https://api.replicate.com/v1/predictions"
    private val REPLICATE_API_TOKEN = ""
    private val client = HttpClient() {
        install(Logging) {
            logger = Logger.SIMPLE
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 10_000
        }
    }

    override suspend fun process(params: ControlNet.ControlNetParams): ImageBitmap? {
        val inputData = InputData(
            createDataUriFromBitmap(params.scribble.asAndroidBitmap()),
            params.prompt
        )
        val version = "435061a1b5a4c1e26740464bf786efdfa9cb3a3ac488595a2de23e143fdb0117"
        val predictionRequest = ReplicatePredictionRequest(version, inputData)
        val jsonData = Json.encodeToString(predictionRequest)

        try {
            val response = makeReplicateRequest(jsonData)

            if (response.status.isSuccess()) {
                val responseText = response.bodyAsText()
                val apiResponse = Json.decodeFromString<PredictionResponse>(responseText)
                val getUrl = apiResponse.urls.get
                var imageUrl: String? = null

                while (imageUrl == null) {
                    val responce2 = client.get(getUrl) {
                        header(HttpHeaders.Authorization, "Bearer $REPLICATE_API_TOKEN")
                        header(HttpHeaders.ContentType, "application/json")
                    }

                    val responseText2 = responce2.bodyAsText()
                    val apiResponse2 = Json.decodeFromString<PredictionResponse2>(responseText2)

                    if (apiResponse2.output?.size == 2) {
                        imageUrl = apiResponse2.output[1]
                    } else {
                        delay(3000)
                    }
                }

                val bitmap = getImageBitmap(imageUrl)
                return bitmap?.asImageBitmap()
            } else {
                // Handle error status code
            }
        } catch (e: Exception) {
            Log.i("TAG_aaa", "greeting: $e")
            return null
        }
        return null
    }

    suspend fun makeReplicateRequest(jsonData: String): HttpResponse {
        val response = client.post(URL) {
            header(HttpHeaders.Authorization, "Bearer $REPLICATE_API_TOKEN")
            header(HttpHeaders.ContentType, "application/json")
            setBody(jsonData) // Note: You'll need a JSON serialization library to convert your map to a JSON String (see below)
        }

        println("Response status: ${response.status}")
        println("Response body: ${response.bodyAsText()}")

        return response
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun createDataUriFromBitmap(bitmap: Bitmap, imageFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, quality: Int = 100): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(imageFormat, quality, stream)
        val byteArray = stream.toByteArray()
        val base64Data = Base64.encode(byteArray)

        return when (imageFormat) {
            Bitmap.CompressFormat.JPEG -> "data:image/jpeg;base64,$base64Data"
            Bitmap.CompressFormat.PNG -> "data:image/png;base64,$base64Data"
            // Add other formats if needed (e.g., Bitmap.CompressFormat.WEBP)
            else -> throw IllegalArgumentException("Unsupported image format")
        }
    }

    @Serializable
    data class InputData(
        val image: String,
        val prompt: String,
        // Add other fields from the schema as needed
    )

    @Serializable
    data class ReplicatePredictionRequest(
        val version: String,
        val input: InputData
    )

    //---

    @Serializable
    data class PredictionResponse(
        val id: String,
        val model: String,
        val version: String,
        val input: Input,
        val logs: String,
        val error: String? = null, // Make error nullable
        val status: String,
        @Serializable(with = DateAsStringSerializer::class) val created_at: Date,
        val urls: Urls
    )

    @Serializable
    data class Input(
        val image: String,
        val prompt: String
    )

    @Serializable
    data class Urls(
        val cancel: String,
        val get: String
    )

    object DateAsStringSerializer : KSerializer<Date> {
        @SuppressLint("SimpleDateFormat")
        private val df: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        init {
            df.timeZone = TimeZone.getTimeZone("GMT")
        }

        override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: Date) {
            encoder.encodeString(df.format(value))
        }

        override fun deserialize(decoder: Decoder): Date {
            return df.parse(decoder.decodeString())
        }
    }

    //---

    @Serializable
    data class PredictionResponse2(
        @Serializable(with = DateAsStringSerializer::class) val completed_at: Date? = null, // Completed_at can be null
        @Serializable(with = DateAsStringSerializer::class) val created_at: Date? = null,
        val error: String? = null,
        val id: String,
        val input: Input,
        val logs: String? = null,
        val metrics: Metrics? = null,
        val output: List<String>? = null, // Output can be null
        @Serializable(with = DateAsStringSerializer::class) val started_at: Date? = null,
        val status: String,
        val urls: Urls,
        val version: String,
        val model: String,
    )

    @Serializable
    data class Metrics(
        val predict_time: Double,
    )

    //---

    suspend fun getImageBitmap(imageUrl: String): Bitmap? {
        try {
            client.use { client ->
                val imageByteArray: ByteArray = client.get(imageUrl){
                    header(HttpHeaders.Authorization, "Bearer $REPLICATE_API_TOKEN")
                }.body()
                return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
            }
        } catch (e: Exception) {
            Log.i("TAG_aaa", "greeting: $e")
            return null
        }
    }
}