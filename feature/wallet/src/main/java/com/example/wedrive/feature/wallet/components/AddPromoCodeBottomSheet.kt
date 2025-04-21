package com.example.wedrive.feature.wallet.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wedrive.core.designsystem.component.PrimaryButton
import com.example.wedrive.core.designsystem.component.PrimaryTopBar
import com.example.wedrive.core.designsystem.theme.WeDriveTestTheme
import com.example.wedrive.feature.wallet.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPromoCodeBottomSheet(
    onDismiss: () -> Unit,
    onSaveClick: (String) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = { WindowInsets(0, 0, 0, 0) }
    ) {
        Content(
            onBackClick = onDismiss,
            onSaveClick = onSaveClick,
        )
    }
}

@Composable
private fun Content(
    onBackClick: () -> Unit,
    onSaveClick: (String) -> Unit,
) {
    var code by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
            .fillMaxWidth()
    ) {
        PrimaryTopBar(
            title = stringResource(R.string.enter_promo_code),
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(32.dp))

        PromoCodeField(
            value = code,
            onValueChange = { code = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            onClick = {
                onBackClick()
                onSaveClick(code)
            },
            text = stringResource(R.string.save),
            enabled = code.isNotEmpty()
        )
    }
}

@Composable
private fun PromoCodeField(
    value: String,
    onValueChange: (String) -> Unit
) {

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.titleLarge,
        decorationBox = { innerTextField ->
            Column {
                Box {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = value.isEmpty(),
                        modifier = Modifier.align(Alignment.BottomStart)
                    ) {
                        Text(
                            text = stringResource(R.string.enter_promo_code),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 14.sp
                        )
                    }

                    innerTextField()
                }

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AddPromoCodePreview() {
    WeDriveTestTheme {
        Content(onBackClick = {}) { }
    }
}