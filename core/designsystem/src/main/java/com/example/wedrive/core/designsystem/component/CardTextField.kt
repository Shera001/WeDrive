package com.example.wedrive.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wedrive.core.designsystem.theme.Primary
import com.example.wedrive.core.designsystem.theme.WeDriveTestTheme

@Composable
fun CardTextField(
    value: String,
    placeHolderText: String,
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onClick: () -> Unit = {}
) {
    val density = LocalDensity.current

    val infiniteTransition = rememberInfiniteTransition()
    val cursorAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    var cursorHeight by remember { mutableStateOf(0.dp) }

    val transformedText = remember(value) {
        visualTransformation.filter(AnnotatedString(value))
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onClick
            ),
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            AnimatedVisibility(
                visible = value.isEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = placeHolderText,
                    modifier = Modifier.onGloballyPositioned {
                        cursorHeight = with(density) { it.size.height.toDp() }
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row {
                AnimatedVisibility(
                    visible = value.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = transformedText.text,
                        modifier = Modifier.onGloballyPositioned {
                            cursorHeight = with(density) { it.size.height.toDp() }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                AnimatedVisibility(
                    visible = isActive,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .height(cursorHeight)
                            .width(1.dp)
                            .background(Primary.copy(alpha = cursorAlpha))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryTextFieldPreview() {
    WeDriveTestTheme {
        CardTextField(
            value = "000",
            placeHolderText = "0000 0000 0000",
            isActive = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UnActivePrimaryTextFieldPreview() {
    WeDriveTestTheme {
        CardTextField(
            value = "",
            placeHolderText = "0000 0000 0000",
            isActive = false
        )
    }
}