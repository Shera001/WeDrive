package com.example.wedrive.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wedrive.core.designsystem.theme.OnSurface
import com.example.wedrive.core.designsystem.theme.WeDriveTestTheme

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            color = if (enabled) Color.White else OnSurface
        )
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    WeDriveTestTheme {
        PrimaryButton(
            onClick = {},
            text = "Save",
        )
    }
}

@Preview
@Composable
private fun DisabledPrimaryButtonPreview() {
    WeDriveTestTheme {
        PrimaryButton(
            onClick = {},
            text = "Save",
            enabled = false
        )
    }
}