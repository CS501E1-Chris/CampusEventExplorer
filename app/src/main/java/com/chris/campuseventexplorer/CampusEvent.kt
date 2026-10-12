package com.chris.campuseventexplorer

data class CampusEvent(
    val id: Long,
    val title: String,
    val category: String,
    val location: String,
    val time: String,
    val description: String
)