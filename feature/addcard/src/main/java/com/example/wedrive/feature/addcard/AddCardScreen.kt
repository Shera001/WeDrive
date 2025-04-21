package com.example.wedrive.feature.addcard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wedrive.core.designsystem.component.CardTextField
import com.example.wedrive.core.designsystem.component.LoadingContent
import com.example.wedrive.core.designsystem.component.PrimaryButton
import com.example.wedrive.core.designsystem.component.PrimaryTopBar
import com.example.wedrive.core.designsystem.theme.WeDriveTestTheme
import com.example.wedrive.feature.addcard.event.AddCardEvent
import com.example.wedrive.feature.addcard.intent.AddCardIntent
import com.example.wedrive.feature.addcard.state.AddCardState
import com.example.wedrive.feature.addcard.utils.CardNumberTransformation
import com.example.wedrive.feature.addcard.utils.DateTransformation

enum class CardFieldType(val maxValueLength: Int) {
    NUMBER(maxValueLength = 16),
    DATE(maxValueLength = 4)
}

sealed class KeypadItem {
    data class Number(val value: Int) : KeypadItem()
    data object Clear : KeypadItem()
    data object Empty : KeypadItem()
}

@Composable
internal fun AddCardRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackClick: () -> Unit,
    viewModel: AddCardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddCardScreen(
        state = state,
        onIntent = viewModel::onIntent,
        onBackClick = onBackClick
    )

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is AddCardEvent.OnError -> {
                    onShowSnackbar(event.message, null)
                }

                AddCardEvent.OnSuccess -> {
                    onBackClick()
                }
            }
        }
    }
}

@Composable
internal fun AddCardScreen(
    state: AddCardState,
    onIntent: (AddCardIntent) -> Unit,
    onBackClick: () -> Unit,
) {

    Scaffold(
        topBar = {
            PrimaryTopBar(
                title = stringResource(R.string.add_card),
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 20.dp),
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            BottomBar(
                saveButtonEnabled = state.saveButtonEnabled,
                modifier = Modifier.navigationBarsPadding(),
                onNumberClick = { number ->
                    onIntent(AddCardIntent.OnNumberClick(number.toString()))
                },
                onClearClick = {
                    onIntent(AddCardIntent.OnRemoveNumberClick)
                },
                onAddCardClick = {
                    onIntent(AddCardIntent.OnAddCardClick)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            CardInfo(
                cardNumber = state.number,
                cardDate = state.expireDate,
                cardFieldType = state.cardFieldType,
                onChangeActiveTextField = {
                    onIntent(AddCardIntent.ChangeCardFieldType(it))
                }
            )
        }
    }

    AnimatedVisibility(
        visible = state.isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LoadingContent()
    }
}

@Composable
private fun CardInfo(
    cardNumber: String,
    cardDate: String,
    cardFieldType: CardFieldType,
    modifier: Modifier = Modifier,
    onChangeActiveTextField: (CardFieldType) -> Unit,
) {
    val numberTransformation = remember { CardNumberTransformation() }
    val dateTransformation = remember { DateTransformation() }

    Column(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color(0x80000000),
                ambientColor = Color(0x4D000000),
                clip = false
            )
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        CardTextField(
            value = cardNumber,
            placeHolderText = stringResource(R.string.card_number_placeholder),
            modifier = Modifier.fillMaxWidth(),
            isActive = cardFieldType == CardFieldType.NUMBER,
            visualTransformation = numberTransformation,
            onClick = { onChangeActiveTextField(CardFieldType.NUMBER) }
        )

        Spacer(modifier = Modifier.height(15.dp))

        CardTextField(
            value = cardDate,
            placeHolderText = stringResource(R.string.card_date_placeholder),
            modifier = Modifier
                .widthIn(min = 72.dp)
                .animateContentSize(),
            isActive = cardFieldType == CardFieldType.DATE,
            visualTransformation = dateTransformation,
            onClick = { onChangeActiveTextField(CardFieldType.DATE) }
        )
    }
}

@Composable
private fun BottomBar(
    saveButtonEnabled: Boolean,
    onNumberClick: (Int) -> Unit,
    onClearClick: () -> Unit,
    onAddCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        PrimaryButton(
            onClick = onAddCardClick,
            text = stringResource(R.string.save),
            modifier = Modifier.padding(horizontal = 20.dp),
            enabled = saveButtonEnabled,
        )

        Spacer(modifier = Modifier.height(38.dp))

        NumberPanel(
            onNumberClick = onNumberClick,
            onClearClick = onClearClick,
            modifier = Modifier.padding(bottom = 48.dp)
        )
    }
}

private val items = listOf(
    KeypadItem.Number(1),
    KeypadItem.Number(2),
    KeypadItem.Number(3),
    KeypadItem.Number(4),
    KeypadItem.Number(5),
    KeypadItem.Number(6),
    KeypadItem.Number(7),
    KeypadItem.Number(8),
    KeypadItem.Number(9),
    KeypadItem.Empty,
    KeypadItem.Number(0),
    KeypadItem.Clear
)

@Composable
private fun NumberPanel(
    onNumberClick: (Int) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 20.dp),
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(items) { item ->
            when (item) {
                is KeypadItem.Number -> NumberButton(
                    number = item.value,
                    onClick = { onNumberClick(item.value) }
                )

                KeypadItem.Clear -> ClearButton(onClick = onClearClick)
                KeypadItem.Empty -> {}
            }
        }
    }
}

@Composable
private fun NumberButton(
    number: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                role = Role.Button,
                interactionSource = interactionSource,
                indication = ripple(color = Color.Black),
                onClick = onClick
            )
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.labelSmall,
            fontSize = 36.sp,
        )
    }
}

@Composable
private fun ClearButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                role = Role.Button,
                interactionSource = interactionSource,
                indication = ripple(color = Color.Black),
                onClick = onClick
            )
            .padding(vertical = 26.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_clear),
            contentDescription = "Clear",
            modifier = Modifier.size(28.dp, 16.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun AddCardScreenPreview() {
    WeDriveTestTheme {
        AddCardRoute(onShowSnackbar = { _, _ -> true }, onBackClick = {})
    }
}