package com.weather.mforchino

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.weather.mforchino.view.ui.common.LineSeparator
import com.weather.mforchino.view.ui.common.PermissionTracker
import com.weather.mforchino.view.ui.common.PickerDialog
import com.weather.mforchino.view.ui.common.showPermissionsDeniedPrettyToast
import com.weather.mforchino.view.ui.theme.PurpleGrey80


@Composable
internal fun MainScreen() {

    val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
    val state = mainScreenViewModel.state
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var showCitiesDialog by remember { mutableStateOf(false) }
    var locationPermissionsGranted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.h3)

        Button(onClick = { showCitiesDialog = true }) {
            Text(text = stringResource(id = R.string.select_city))
        }

        state.data?.cityWeather.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 10.dp, horizontal = 40.dp)
                    .background(color = PurpleGrey80, shape = RoundedCornerShape(20.dp))
            )
            {
                if (state.loading) {
                    Box(
                        modifier = Modifier.matchParentSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                } else {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 10.dp)
                    ) {
                        if (state.data?.citySelected != null) {

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 5.dp),
                                text = "Tiempo actual en: ${state.data.citySelected.name}".uppercase(),
                                textAlign = TextAlign.Center
                            )

                            it?.main.let {
                                Text(
                                    text = "Temperatura",
                                    style = MaterialTheme.typography.subtitle2
                                )
                                Text(
                                    text = "Actual: " + it?.tempCelsius + "°C",
                                    style = MaterialTheme.typography.body2
                                )
                                Text(
                                    text = "Mínima: " + it?.tempMinCelsius + "°C",
                                    style = MaterialTheme.typography.body2
                                )
                                Text(
                                    text = "Máxima: " + it?.tempMaxCelsius + "°C",
                                    style = MaterialTheme.typography.body2
                                )
                                Text(
                                    text = "Humedad: " + it?.humidity + "%",
                                    style = MaterialTheme.typography.body2
                                )
                                Text(
                                    text = "Presión: " + it?.pressure + " hpa",
                                    style = MaterialTheme.typography.body2
                                )
                            }

                            LineSeparator()

                            it?.wind.let {
                                Text(text = "Vientos", style = MaterialTheme.typography.subtitle2)
                                Text(
                                    text = "Velocidad: " + it?.speed + "km/h",
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCitiesDialog) {
        state.data?.cities?.let { citiesList ->

            PickerDialog(
                labels = citiesList.mapNotNull { it.name },
                selected = "",
                dismissCallback = { showCitiesDialog = false }) {
                citiesList[it].name.let { city ->
                    mainScreenViewModel.setCitiSelected(citiesList[it], lifecycleOwner, it == 0)

                }
            }
        }
    }

    PermissionTracker(onPermissionStateChanged = { granted ->
        if (locationPermissionsGranted != granted) {
            locationPermissionsGranted = granted
            if (locationPermissionsGranted) {

            } else context.showPermissionsDeniedPrettyToast(
                "Ups, activá la localizacion para una mejor experiencia."
            )
        }
    })
}
