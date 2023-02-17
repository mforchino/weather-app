package com.weather.mforchino.view.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weather.mforchino.view.ui.theme.illustrationLighter

@Composable
fun LineSeparator() {
    Divider(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .height(1.dp),
        color = illustrationLighter,
    )
}
