package com.example.evanrobertson_comp304lab3_ex1.navigation

sealed interface NavigationType {
    data object BottomNavigation : NavigationType
    data object NavigationDrawer : NavigationType
    data object NavigationRail : NavigationType
}