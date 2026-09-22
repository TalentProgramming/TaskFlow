package com.tp.taskflow.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tp.taskflow.core.ui.ThemeViewModel
import com.tp.taskflow.feature.chat.presentation.ChatScreen
import com.tp.taskflow.feature.auth.presentation.LoginScreen
import com.tp.taskflow.feature.auth.presentation.RegisterScreen
import com.tp.taskflow.feature.home.presentation.DashboardScreen
import com.tp.taskflow.feature.note.presentation.NotesScreen
import com.tp.taskflow.feature.product.presentation.ProductDetailScreen
import com.tp.taskflow.feature.product.presentation.ProductSearchScreen
import com.tp.taskflow.feature.profile.presentation.ProfileScreen
import com.tp.taskflow.feature.settings.presentation.SettingsScreen

@Composable
fun TaskFlowNavHost(
    darkMode: Boolean,
    themeViewModel: ThemeViewModel
) {
    val nav = rememberNavController()
    val backStack by nav.currentBackStackEntryAsState()
    val route = backStack?.destination?.route
    val tabs = listOf(Routes.Home, Routes.Search, Routes.Notes, Routes.Profile, Routes.Chat)
    val showBar = route in tabs

    Scaffold(
        bottomBar = {
            if (showBar) {
                NavigationBar {
                    tabs.forEach { tab ->
                        NavigationBarItem(
                            selected = route == tab,
                            onClick = {
                                nav.navigate(tab) {
                                    popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Text(tab.take(1).uppercase()) },
                            label = { Text(tab.replaceFirstChar { it.uppercase() }) }
                        )
                    }
                }
            }
        }
    ) { inner ->
        NavHost(
            navController = nav,
            startDestination = Routes.Login,
            modifier = Modifier.padding(inner)
        ) {
            composable(Routes.Login) {
                LoginScreen(
                    viewModel = hiltViewModel(),
                    onContinueToDashboard = {
                        nav.navigate(Routes.Home) {
                            popUpTo(Routes.Login) { inclusive = true }
                        }
                    },
                    onOpenRegister = { nav.navigate(Routes.Register) }
                )
            }
            composable(Routes.Register) {
                RegisterScreen(
                    viewModel = hiltViewModel(),
                    onBack = { nav.popBackStack() },
                    onSuccess = {
                        nav.navigate(Routes.Home) {
                            popUpTo(Routes.Login) { inclusive = true }
                        }
                    }
                )
            }
            composable(Routes.Home) {
                DashboardScreen(
                    viewModel = hiltViewModel(),
                    darkMode = darkMode,
                    onToggleDarkMode = themeViewModel::setDark,
                    onLogout = {
                        nav.navigate(Routes.Login) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onOpenSearch = { nav.navigate(Routes.Search) },
                    onOpenNotes = { nav.navigate(Routes.Notes) },
                    onOpenProfile = { nav.navigate(Routes.Profile) },
                    onOpenStudioMug = { nav.navigate(Routes.product("studio-mug")) },
                    onOpenSettings = { nav.navigate(Routes.Settings) }
                )
            }
            composable(Routes.Search) {
                ProductSearchScreen(
                    viewModel = hiltViewModel(),
                    onBack = { nav.popBackStack() },
                    onOpenProduct = { id -> nav.navigate(Routes.product(id)) }
                )
            }
            composable(Routes.Notes) {
                NotesScreen(viewModel = hiltViewModel(), onBack = { nav.popBackStack() })
            }
            composable(Routes.Profile) {
                ProfileScreen(viewModel = hiltViewModel(), onBack = { nav.popBackStack() })
            }
            composable(Routes.Chat) {
                ChatScreen(viewModel = hiltViewModel())
            }
            composable(Routes.ProductDetail) {
                ProductDetailScreen(viewModel = hiltViewModel(), onBack = { nav.popBackStack() })
            }
            composable(Routes.Settings) {
                SettingsScreen(
                    viewModel = hiltViewModel(),
                    darkMode = darkMode,
                    onToggleDarkMode = themeViewModel::setDark,
                    onBack = { nav.popBackStack() }
                )
            }
        }
    }
}
