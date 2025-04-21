package com.example.wedrive.feature.addcard.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CardNumberTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length > 16) text.text.substring(0..15) else text.text
        val formatted = buildString {
            for (i in trimmed.indices) {
                append(trimmed[i])
                if ((i + 1) % 4 == 0 && i != 15) {
                    append(" ")
                }
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val result = (offset / 4).takeIf {
                    offset % 4 != 0 || offset == 0
                } ?: (offset / 4 - 1)
                return offset + result
            }

            override fun transformedToOriginal(offset: Int): Int {
                return offset - (offset / 5).coerceAtLeast(0)
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}