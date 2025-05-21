package com.polije.sosrobahufactoryapp.utils

import PesananPerBulan
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject


object PesananPerBulanMapSerializer : KSerializer<Map<String, PesananPerBulan>> {
    override val descriptor: SerialDescriptor =
        MapSerializer(String.serializer(), PesananPerBulan.serializer()).descriptor

    override fun deserialize(decoder: Decoder): Map<String, PesananPerBulan> {
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw SerializationException("Only JSON is supported")
        val element = jsonDecoder.decodeJsonElement()
        return when (element) {
            is JsonObject -> element.mapValues { (_, jsonElem) ->
                jsonDecoder.json.decodeFromJsonElement(PesananPerBulan.serializer(), jsonElem)
            }

            is JsonArray -> emptyMap()
            else -> throw SerializationException("Unexpected JSON for pesananPerBulan: $element")
        }
    }

    override fun serialize(encoder: Encoder, value: Map<String, PesananPerBulan>) {
        val jsonEncoder = encoder as? JsonEncoder
            ?: throw SerializationException("Only JSON is supported")
        val obj = buildJsonObject {
            for ((key, item) in value) {
                put(key, jsonEncoder.json.encodeToJsonElement(PesananPerBulan.serializer(), item))
            }
        }
        jsonEncoder.encodeJsonElement(obj)
    }
}