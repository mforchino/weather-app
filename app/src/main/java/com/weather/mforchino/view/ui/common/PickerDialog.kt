package com.weather.mforchino.view.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.weather.mforchino.view.ui.theme.graysPlaceholder

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PickerDialog(
    labels: List<String>,
    selected: String = "",
    dismissCallback: () -> Unit,
    onItemSelected: (index: Int) -> Unit,
) = Dialog(
    onDismissRequest = dismissCallback,
    properties = DialogProperties(
        dismissOnClickOutside = true,
        dismissOnBackPress = true,
        usePlatformDefaultWidth = false
    ),
) {
    val scrollState = rememberLazyListState()
    var selectedLabel by remember { mutableStateOf(selected) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 48.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
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
                    .padding(all = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(labels) { label ->
                        SelectableItem(
                            selected = label == selectedLabel,
                            label = label,
                            selectedCallback = {
                                selectedLabel = label
                                onItemSelected(labels.indexOf(label))
                                dismissCallback()
                            }
                        )
                        Divider(color = graysPlaceholder, thickness = 1.dp)
                    }
                }
            }

        }
    }
}