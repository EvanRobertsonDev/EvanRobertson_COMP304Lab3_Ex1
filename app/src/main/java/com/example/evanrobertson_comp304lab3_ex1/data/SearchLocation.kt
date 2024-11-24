package com.example.evanrobertson_comp304lab3_ex1.data

import kotlinx.serialization.Serializable

//API Friendly search response
@Serializable
data class SearchLocation(
    val id : Int = 0,
    val name : String = "",
    val region : String = "",
    val country : String = "",
    val lat : Double = 0.0,
    val lon : Double = 0.0,
    val url : String = ""
)
