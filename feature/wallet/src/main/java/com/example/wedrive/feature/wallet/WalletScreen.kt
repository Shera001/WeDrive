package com.example.wedrive.feature.wallet

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wedrive.core.designsystem.component.LoadingContent
import com.example.wedrive.core.designsystem.component.PrimaryTopBar
import com.example.wedrive.core.designsystem.theme.BalanceBackground
import com.example.wedrive.core.designsystem.theme.Blue
import com.example.wedrive.core.designsystem.theme.Tertiary
import com.example.wedrive.core.designsystem.theme.TertiaryContainer
import com.example.wedrive.core.designsystem.theme.WeDriveTestTheme
import com.example.wedrive.feature.wallet.components.AddPromoCodeBottomSheet
import com.example.wedrive.feature.wallet.event.WalletEvent
import com.example.wedrive.feature.wallet.intent.WalletIntent
import com.example.wedrive.feature.wallet.state.WalletSource
import com.example.wedrive.feature.wallet.state.WalletUIState

@Composable
internal fun WalletRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onAddCardClick: () -> Unit,
    walletViewModel: WalletViewModel = hiltViewModel<WalletViewModel>()
) {
    val state = walletViewModel.state.collectAsStateWithLifecycle()

    WalletScreen(
        state = state.value,
        onIntent = walletViewModel::onIntent,
        onAddCardClick = onAddCardClick,
    )

    if (state.value.showBottomSheet) {
        AddPromoCodeBottomSheet(
            onDismiss = {
                walletViewModel.onIntent(WalletIntent.HideAddPromoCodeDialog)
            },
            onSaveClick = { code ->
                walletViewModel.onIntent(WalletIntent.AddPromoCode(code))
            }
        )
    }

    val retryMessage = stringResource(R.string.retry)
    val errorMessage = stringResource(R.string.error_message)

    LaunchedEffect(Unit) {
        walletViewModel.onIntent(WalletIntent.Init)

        walletViewModel.event.collect { event ->
            when (event) {
                is WalletEvent.OnAddPromoCodeError -> {
                    val snackBarResult = onShowSnackbar(event.message, retryMessage)
                    if (snackBarResult) {
                        walletViewModel.onIntent(WalletIntent.ShowAddPromoCodeDialog)
                    }
                }

                is WalletEvent.ShowMessage -> {
                    onShowSnackbar(event.message, null)
                }

                WalletEvent.OnInitError -> {
                    val result = onShowSnackbar(errorMessage, retryMessage)
                    if (result) {
                        walletViewModel.onIntent(WalletIntent.Init)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WalletScreen(
    state: WalletUIState,
    onIntent: (WalletIntent) -> Unit,
    onAddCardClick: () -> Unit,
) {

    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = {
            Log.e("TAG", "WalletScreen: onrefresh")
            onIntent(WalletIntent.Init)
        }
    ) {
        WalletContent(
            state = state,
            onIntent = onIntent,
            onAddCardClick = onAddCardClick,
        )
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
private fun WalletContent(
    state: WalletUIState,
    onIntent: (WalletIntent) -> Unit,
    onAddCardClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {

        PrimaryTopBar(
            title = stringResource(R.string.wallet),
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(22.dp))

        Balance(
            balance = state.balance.toString(),
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Identification(
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                BaseItem(
                    text = stringResource(R.string.add_promo_code),
                    imageRes = R.drawable.img_promokod,
                    modifier = Modifier.animateItem(),
                    onClick = { onIntent(WalletIntent.ShowAddPromoCodeDialog) }
                )
            }

            item {
                SwitchItem(
                    checked = state.activeMethod == WalletSource.Cash,
                    text = stringResource(R.string.cash),
                    imageRes = R.drawable.img_cash,
                    modifier = Modifier.animateItem(),
                    onCheckedChange = {
                        onIntent(WalletIntent.UpdateWalletSource(WalletSource.Cash))
                    }
                )
            }

            items(state.cards) { card ->
                SwitchItem(
                    checked = card.id == state.activeMethod.activeCardId,
                    text = stringResource(R.string.card, card.number.takeLast(4)),
                    imageRes = R.drawable.img_card,
                    modifier = Modifier.animateItem(),
                    checkedTrackColor = Blue,
                    onCheckedChange = {
                        onIntent(WalletIntent.UpdateWalletSource(WalletSource.Card(card.id)))
                    }
                )
            }

            item {
                BaseItem(
                    text = stringResource(R.string.add_card),
                    imageRes = R.drawable.img_add_card,
                    modifier = Modifier.animateItem(),
                    onClick = { onAddCardClick() }
                )
            }
        }
    }
}


@Composable
private fun Balance(
    balance: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(BalanceBackground)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {

        Text(
            text = stringResource(R.string.balance),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = balance,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 24.sp
        )
    }
}

@Composable
private fun Identification(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { }
            .padding(vertical = 18.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_info_circle),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(R.string.identification_required),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.ic_arrow_up_right),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

private val switchTrackWidth = 44.dp
private val switchThumbSize = 20.dp
private val switchPadding = 2.dp

@Composable
private fun SwitchItem(
    checked: Boolean,
    text: String,
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier,
    checkedTrackColor: Color = Tertiary,
    onCheckedChange: (Boolean) -> Unit,
) {
    BaseItem(
        text = text,
        imageRes = imageRes,
        modifier = modifier,
        trailingContent = {
            val xOffset by animateDpAsState(
                targetValue = if (checked) switchTrackWidth - switchThumbSize - switchPadding
                else switchPadding,
                animationSpec = spring(stiffness = Spring.StiffnessLow),
            )

            val trackColor by animateColorAsState(
                targetValue = if (checked) checkedTrackColor
                else TertiaryContainer,
            )

            Box(
                modifier = Modifier
                    .size(switchTrackWidth, 24.dp)
                    .clip(CircleShape)
                    .background(trackColor)
                    .clickable {
                        onCheckedChange(!checked)
                    }
                    .padding(vertical = switchPadding)
            ) {

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(xOffset))
                    Box(
                        modifier = Modifier
                            .size(switchThumbSize)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
            }
        },
        onClick = {}
    )
}

@Composable
private fun BaseItem(
    text: String,
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier,
    trailingContent: @Composable () -> Unit = {
        Icon(
            painter = painterResource(R.drawable.ic_right),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    },
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = Color(0x801C1917),
                ambientColor = Color(0x4D1C1917),
                clip = false
            )
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {

        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        trailingContent()
    }
}

@Preview
@Composable
private fun WalletScreenPreview() {
    WeDriveTestTheme {
        WalletRoute(
            onShowSnackbar = { _, _ -> true },
            onAddCardClick = {})
    }
}