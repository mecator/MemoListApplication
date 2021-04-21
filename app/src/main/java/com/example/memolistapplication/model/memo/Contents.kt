package com.example.memolistapplication.model.memo


import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.parse
import kotlinx.serialization.stringify

@Serializable
data class Contents(val list: List<MemoContent>) {
    @Serializable
    data class MemoContent(
        val type: ContentType,
        val text: String,
        val isCheck: Boolean = false
    ) {
        enum class ContentType(val type: String) {
            TEXT_MEMO("text"),
            CHECK_MEMO("check");
        }

        override fun toString(): String {
            val json = Json { allowStructuredMapKeys = true }
            return json.encodeToString(serializer(), this)
        }

        companion object {
            fun stringToObject(str: String): MemoContent {
                val json = Json { allowStructuredMapKeys = true }

                return json.decodeFromString(serializer(), str)
            }
        }

    }

    override fun toString(): String {
        val json = Json { allowStructuredMapKeys = true }
        return json.encodeToString(serializer(), this)
    }

    companion object {
        fun stringToObject(str: String): List<MemoContent> {
            if (str.isEmpty()) return emptyList()

            val json = Json { allowStructuredMapKeys = true }
            return json.decodeFromString(serializer(), str).list
        }

        fun objectToString(contents: List<MemoContent>): String {
            if (contents.isEmpty()) return ""

            val json = Json { allowStructuredMapKeys = true }
            return json.encodeToString(serializer(), Contents(contents))
        }
    }
}