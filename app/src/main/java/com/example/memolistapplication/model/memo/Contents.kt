package com.example.memolistapplication.model.memo


import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

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
            val json = Json(JsonConfiguration.Stable)
            return json.stringify(serializer(), this)
        }

        companion object {
            fun stringToObject(str: String): MemoContent {
                val json = Json(JsonConfiguration.Stable)

                return json.parse(serializer(), str)
            }
        }

    }

    override fun toString(): String {
        val json = Json(JsonConfiguration.Stable)
        return json.stringify(serializer(), this)
    }

    companion object {
        fun stringToObject(str: String): List<MemoContent> {
            if (str.isEmpty()) return emptyList()

            val json = Json(JsonConfiguration.Stable)
            return json.parse(serializer(), str).list
        }

        fun objectToString(contents: List<MemoContent>): String {
            if (contents.isEmpty()) return ""

            val json = Json(JsonConfiguration.Stable)
            return json.stringify(serializer(), Contents(contents))
        }
    }
}