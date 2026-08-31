package com.example.clasedesarrollomobil.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clasedesarrollomobil.activities.PestanasActivity
import com.example.clasedesarrollomobil.data.local.AppDatabase
import com.example.clasedesarrollomobil.data.repository.AccessLogRepository
import com.example.clasedesarrollomobil.ui.screens.calculadora.CalculadoraScreen
import com.example.clasedesarrollomobil.ui.screens.camara.CamaraScreen
import com.example.clasedesarrollomobil.ui.screens.gps.GpsScreen
import com.example.clasedesarrollomobil.ui.screens.imagenes.ImagenesScreen
import com.example.clasedesarrollomobil.ui.screens.login.LoginScreen
import com.example.clasedesarrollomobil.ui.screens.menu.MenuScreen
import com.example.clasedesarrollomobil.ui.screens.video.VideoScreen
import com.example.clasedesarrollomobil.ui.screens.web.WebScreen
import com.example.clasedesarrollomobil.viewmodel.LoginViewModel
import com.example.clasedesarrollomobil.viewmodel.LoginViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val accessLogRepository = remember {
        AccessLogRepository(
            AppDatabase.getDatabase(context).accessLogDao()
        )
    }

    fun navigateToMenu() {
        navController.navigate(Routes.MENU) {
            popUpTo(Routes.MENU) {
                inclusive = false
            }
            launchSingleTop = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(accessLogRepository)
            )
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(Routes.MENU) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.MENU) {
            MenuScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                },
                onOpenTabs = {
                    context.startActivity(Intent(context, PestanasActivity::class.java))
                },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.MENU) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.CALCULADORA) { CalculadoraScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.CAMARA) { CamaraScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.IMAGENES) { ImagenesScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.WEB) { WebScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.VIDEO) { VideoScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.GPS) { GpsScreen(onBackToMenu = ::navigateToMenu) }
    }
}
