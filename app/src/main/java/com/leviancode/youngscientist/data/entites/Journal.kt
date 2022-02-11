package com.leviancode.youngscientist.data.entites

data class Journal(
    val id: String,
    val year: Int,
    val numberInYear: Int,
    val numberTotal: Int,
    val cover: String,
    val coverDescription: String,
    val articles: List<Article>
)
