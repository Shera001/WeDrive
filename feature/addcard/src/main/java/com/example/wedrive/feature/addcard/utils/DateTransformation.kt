package com.example.wedrive.feature.addcard.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class DateTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        if (text.isEmpty()) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        val formattedText = if (text.length <= 2) {
            text
        } else {
            val prefix = text.substring(0, 2)
            val suffix = text.substring(2)
            AnnotatedString("$prefix/$suffix")
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return if (offset <= 2) offset else offset + 1
            }

            override fun transformedToOriginal(offset: Int): Int {
                return if (offset <= 2) offset else offset - 1
            }
        }

        return TransformedText(formattedText, offsetMapping)
    }
}