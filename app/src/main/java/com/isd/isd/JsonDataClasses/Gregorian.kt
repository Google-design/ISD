package com.isd.isd.JsonDataClasses

data class Gregorian(
    val date: String,
    val day: String,
    val designation: Designation,
    val format: String,
    val month: Month,
    val weekday: Weekday,
    val year: String
)