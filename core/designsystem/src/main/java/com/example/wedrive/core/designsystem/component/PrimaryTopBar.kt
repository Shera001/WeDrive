package com.example.wedrive.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wedrive.core.designsystem.R
import com.example.wedrive.core.designsystem.theme.WeDriveTestTheme

@Composable
fun PrimaryTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null
) {

    Row(
        modifier = modifier
            .padding(top = 12.dp)
            .heightIn(min = 42.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (onBackClick != null) {
            BackButton(onClick = onBackClick)

            Spacer(modifier = Modifier.width(16.dp))
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 25.sp
        )
    }
}

@Composable
private fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = CircleShape,
                spotColor = Color(0x80000000),
                ambientColor = Color(0x4D000000),
                clip = false
            )
            .size(44.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                role = Role.Button,
                interactionSource = interactionSource,
                indication = ripple(color = Color.Black),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_arrow_left),
            contentDescription = "back",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PrimaryButtonPreview() {
    WeDriveTestTheme {
        PrimaryTopBar(
            title = "Wallet"
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PrimaryButtonPreview2() {
    WeDriveTestTheme {
        PrimaryTopBar(
            title = "Wallet",
            onBackClick = {}
        )
    }
}