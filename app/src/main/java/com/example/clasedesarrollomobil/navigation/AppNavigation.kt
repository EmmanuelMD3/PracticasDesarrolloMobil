package com.example.clasedesarrollomobil.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clasedesarrollomobil.data.local.AppDatabase
import com.example.clasedesarrollomobil.data.repository.AccessLogRepository
import com.example.clasedesarrollomobil.ui.screens.acerca.AcercaDeScreen
import com.example.clasedesarrollomobil.ui.screens.barras.BarrasIndicadoresScreen
import com.example.clasedesarrollomobil.ui.screens.botones.BotonesScreen
import com.example.clasedesarrollomobil.ui.screens.dialogos.DialogosMensajesScreen
import com.example.clasedesarrollomobil.ui.screens.fechahora.FechaHoraScreen
import com.example.clasedesarrollomobil.ui.screens.google.GoogleScreen
import com.example.clasedesarrollomobil.ui.screens.layouts.LayoutScreen
import com.example.clasedesarrollomobil.ui.screens.listas.ListasColeccionesScreen
import com.example.clasedesarrollomobil.ui.screens.login.LoginScreen
import com.example.clasedesarrollomobil.ui.screens.material.MaterialDesignScreen
import com.example.clasedesarrollomobil.ui.screens.menu.MenuScreen
import com.example.clasedesarrollomobil.ui.screens.multimedia.ImagenesMultimediaScreen
import com.example.clasedesarrollomobil.ui.screens.navegacion.NavegacionDemoScreen
import com.example.clasedesarrollomobil.ui.screens.navegacion.NavegacionDetalleScreen
import com.example.clasedesarrollomobil.ui.screens.scroll.ContenedoresDesplazablesScreen
import com.example.clasedesarrollomobil.ui.screens.seleccion.SeleccionScreen
import com.example.clasedesarrollomobil.ui.screens.texto.TextoScreen
import com.example.clasedesarrollomobil.viewmodel.AccessLogViewModel
import com.example.clasedesarrollomobil.viewmodel.AccessLogViewModelFactory
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

        composable(Routes.TEXTO) { TextoScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.BOTONES) { BotonesScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.SELECCION) { SeleccionScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.LISTAS) { ListasColeccionesScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.MULTIMEDIA) { ImagenesMultimediaScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.BARRAS) { BarrasIndicadoresScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.NAVEGACION) {
            NavegacionDemoScreen(
                onBackToMenu = ::navigateToMenu,
                onOpenDetail = { name ->
                    navController.navigate(Routes.navegacionDetalle(name))
                }
            )
        }
        composable(Routes.LAYOUT) { LayoutScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.FECHA_HORA) { FechaHoraScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.SCROLL) { ContenedoresDesplazablesScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.DIALOGOS) { DialogosMensajesScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.MATERIAL) { MaterialDesignScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.GOOGLE) { GoogleScreen(onBackToMenu = ::navigateToMenu) }
        composable(Routes.ACERCA) {
            val accessLogViewModel: AccessLogViewModel = viewModel(
                factory = AccessLogViewModelFactory(accessLogRepository)
            )
            AcercaDeScreen(
                accessLogViewModel = accessLogViewModel,
                onBackToMenu = ::navigateToMenu
            )
        }

        composable(
            route = Routes.NAVEGACION_DETALLE_WITH_ARG,
            arguments = listOf(
                navArgument(Routes.ARG_NOMBRE) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            NavegacionDetalleScreen(
                nombre = backStackEntry.arguments?.getString(Routes.ARG_NOMBRE).orEmpty(),
                onBackToMenu = ::navigateToMenu,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
