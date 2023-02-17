package com.weather.mforchino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.weather.mforchino.view.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar() {
                            Text(text = getString(R.string.app_name), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                        }
                    }
                ) {
                    MainScreen()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MainScreen()
    }
}