package com.example.evanrobertson_comp304lab3_ex1.navigation

sealed interface ContentType {
    data object List : ContentType
    data object ListAndDetail : ContentType
}