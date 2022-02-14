package com.leviancode.youngscientist.data.entites

data class Journal(
    val number: Int,
    val numberInYear: Int,
    val year: Int,
    val cover: String,
    val coverDescription: String,
    val articles: List<Article>
)
