package com.isd.isd.HadeethDataClasses

data class HadeethData(
    val attribution: String,
    val attribution_ar: String,
    val categories: List<String>,
    val explanation: String,
    val explanation_ar: String,
    val grade: String,
    val grade_ar: String,
    val hadeeth: String,
    val hadeeth_ar: String,
    val hints: List<Any>,
    val hints_ar: List<String>,
    val id: String,
    val title: String,
    val translations: List<String>,
    val words_meanings_ar: List<WordsMeaningsAr>
)