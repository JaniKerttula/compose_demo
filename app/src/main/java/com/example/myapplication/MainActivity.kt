package com.example.myapplication

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.ExampleScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ExampleApplication: Application() {}

sealed class BottomRoutes(val route: String, val icon: ImageVector) {
    object Main: BottomRoutes("main", Icons.Default.Home)
    object Example: BottomRoutes("example", Icons.Default.Settings)
}
val bottomItems = listOf(
    BottomRoutes.Main,
    BottomRoutes.Example
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ExampleViewModel = hiltViewModel()
            val data = viewModel.exampleData.collectAsState()
            val navController = rememberNavController()
            val currentBackstackEntry by navController.currentBackStackEntryAsState()
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    bottomBar = { BottomAppBar(
                        content = {
                            bottomItems.map {
                                NavigationBarItem(
                                    selected = currentBackstackEntry?.destination?.route == it.route,
                                    onClick = { navController.navigate(it.route) },
                                    icon = { Icon(it.icon, contentDescription = it.route) }
                                )
                            }
                        }
                    ) }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(navController = navController, startDestination = BottomRoutes.Main.route) {
                            composable(BottomRoutes.Main.route) {
                                Greeting(
                                    data.value,
                                    onTextChange = { text -> viewModel.setExampleData(text)}
                                )
                            }
                            composable(BottomRoutes.Example.route) {
                                ExampleScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, onTextChange: (String) -> Unit, modifier: Modifier = Modifier) {
    var showHiddenText by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClick = { showHiddenText = !showHiddenText }) {
            Text(text = "Click me")
        }
        if (showHiddenText) {
            Text("Congrats, you clicked the button!")
        }
        OutlinedTextField(
            value = name,
            onValueChange = onTextChange,
            label = { Text(text = "Change name") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting(
            "Android",
            {}
        )
    }
}