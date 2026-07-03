package com.nexifotech.hotelsaas.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject

@Composable
fun AppNavGraph(
    navigator: AppNavigator = koinInject()
){
    val navController = rememberNavController()


}