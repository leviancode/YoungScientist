package com.leviancode.youngscientist.data.entites

data class Article(
    val journalNumber: Int,
    val author: String,
    val summary: String,
    val content: String,
    val section: String,
    val references: String
)
