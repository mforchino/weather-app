package com.weather.mforchino.view.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.constraintlayout.compose.Dimension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.weather.mforchino.view.ui.theme.illustrationLighter
import androidx.constraintlayout.compose.ConstraintLayout



@Composable
fun SelectableItem(selected: Boolean, label: String, selectedCallback: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(if (selected) illustrationLighter else Color.White)
            .clickable {
                selectedCallback()
            }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (title, check) = createRefs()
            Text(
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width= Dimension.fillToConstraints
                },
                text = label,
            )
        }
    }
}