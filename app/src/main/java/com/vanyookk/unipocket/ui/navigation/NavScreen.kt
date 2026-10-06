package com.vanyookk.unipocket.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Event
import androidx.compose.ui.graphics.vector.ImageVector


sealed class NavScreen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    data object Schedule : NavScreen(
        route = "schedule",
        title = "Расписание",
        selectedIcon = Icons.Filled.CalendarToday,
        unselectedIcon = Icons.Outlined.CalendarToday,
    )

    data object Journal : NavScreen(
        route = "journal",
        title = "Журнал",
        selectedIcon = Icons.Filled.Book,
        unselectedIcon = Icons.Outlined.Book,
    )

    data object Events : NavScreen(
        route = "events",
        title = "События",
        selectedIcon = Icons.Filled.Event,
        unselectedIcon = Icons.Outlined.Event,
    )

    companion object {
        val bottomBarScreens = listOf(Schedule, Journal, Events)
    }
}
