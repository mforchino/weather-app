package com.weather.mforchino.view.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.weather.mforchino.view.ui.theme.graysPlaceholder
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ConfirmationDialog(
    title: String,
    message: String? = null,
    positiveText: String,
    negativeText: String? = null,
    cancellable: Boolean = true,
    dismissOnPositive: Boolean = true,
    onPositiveClicked: () -> Unit,
    dismissCallback: () -> Unit
) = Dialog(
    onDismissRequest = dismissCallback,
    properties = DialogProperties(
        dismissOnClickOutside = cancellable,
        dismissOnBackPress = cancellable,
        usePlatformDefaultWidth = false
    )
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 48.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        ConfirmationDialogContent(
            title = title,
            message = message,
            positiveText = positiveText,
            negativeText = negativeText,
            dismissOnPositive = dismissOnPositive,
            onPositiveClicked = onPositiveClicked,
            dismissCallback = dismissCallback
        )
    }
}

@Composable
private fun ConfirmationDialogContent(
    title: String,
    message: String?,
    positiveText: String,
    negativeText: String?,
    dismissOnPositive: Boolean,
    onPositiveClicked: () -> Unit,
    dismissCallback: () -> Unit
) {
    Card(
        modifier = Modifier
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(6.dp)),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title.uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
            message?.let {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = it,
                    style = MaterialTheme.typography.body1.copy(
                        color = graysPlaceholder,
                        fontWeight = FontWeight.Normal
                    ),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier
                        .wrapContentSize()
                        .weight(1f)
                        .padding(horizontal = 5.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    onClick = { onPositiveClicked(); if (dismissOnPositive) dismissCallback() }
                ) {
                    Text(text = positiveText)
                }
                negativeText?.let {
                    Button(
                        modifier = Modifier
                            .wrapContentSize()
                            .weight(1f),

                        onClick = dismissCallback
                    ) {
                        Text(text = it)
                    }
                }
            }
        }
    }
}