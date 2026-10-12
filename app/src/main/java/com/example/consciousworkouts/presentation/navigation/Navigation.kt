package com.example.consciousworkouts.presentation.navigation


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.consciousworkouts.R
import com.example.consciousworkouts.presentation.create.CreateScreen
import com.example.consciousworkouts.presentation.past.PastScreen
import com.example.consciousworkouts.presentation.today.TodayScreen
import com.example.consciousworkouts.presentation.today.TodayViewModel
import com.example.consciousworkouts.presentation.ui.theme.backgroundBlack
import com.example.consciousworkouts.presentation.ui.theme.consciousGrey
import com.example.consciousworkouts.presentation.ui.theme.primaryGreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation() {

    val navController = rememberNavController()

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val navigationItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = primaryGreen,
        selectedTextColor = primaryGreen,
        unselectedIconColor = Color.White.copy(alpha = 0.45f),
        unselectedTextColor = Color.White.copy(alpha = 0.45f),
        indicatorColor = primaryGreen.copy(alpha = 0.14f)
    )

    val todayViewModel: TodayViewModel = koinViewModel()

    val navToCreate = {navController.navigate(Screen.Create) }

    val navToToday = {navController.navigate(Screen.Today) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        restoreState = true
        launchSingleTop = true
    } }

    val navToPast = {navController.navigate(Screen.Past) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        restoreState = true
        launchSingleTop = true
    } }

    val showBottomBar = currentDestination?.hasRoute<Screen.Create>() != true





    Scaffold(
        containerColor = backgroundBlack,


        bottomBar = {
            if (showBottomBar) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(start = 20.dp, end = 20.dp, top = 6.dp, bottom = 10.dp)
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        color = consciousGrey,
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f)),
                        shadowElevation = 10.dp
                    ) {
                        NavigationBar(
                            modifier = Modifier.height(68.dp),
                            containerColor = Color.Transparent,
                            contentColor = Color.White,
                            windowInsets = WindowInsets(0, 0, 0, 0)
                        ) {
                            NavigationBarItem(
                                selected = currentDestination?.hasRoute<Screen.Today>() == true,
                                onClick = navToToday,
                                colors = navigationItemColors,
                                alwaysShowLabel = true,
                                icon = {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_sprint),
                                        contentDescription = "Today",
                                        modifier = Modifier.size(21.dp)
                                    )
                                },
                                label = {
                                    NavigationLabel("Today")
                                }
                            )

                            NavigationBarItem(
                                selected = currentDestination?.hasRoute<Screen.Past>() == true,
                                onClick = navToPast,
                                colors = navigationItemColors,
                                alwaysShowLabel = true,
                                icon = {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                                        contentDescription = "History",
                                        modifier = Modifier.size(21.dp)
                                    )
                                },
                                label = {
                                    NavigationLabel("History")
                                }
                            )
                        }
                    }
                }
            }
        }


    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = Screen.Today) {

                composable<Screen.Today> {
                    TodayScreen(navToCreate = navToCreate, viewModel = todayViewModel)
                }

                composable<Screen.Past> {
                    PastScreen(navToToday = navToToday, todayViewModel = todayViewModel)
                }

                composable<Screen.Create> {
                    CreateScreen(navBack = { navController.popBackStack() })
                }


            }
        }


    }


}

@Composable
private fun NavigationLabel(text: String) {
    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.2.sp
    )
}

sealed class Screen() {

    @Serializable
    data object Create

    @Serializable
    data object Past

    @Serializable
    data object Today




}