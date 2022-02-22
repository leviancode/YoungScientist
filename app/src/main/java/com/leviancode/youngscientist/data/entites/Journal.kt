package com.leviancode.youngscientist.data.entites

data class Journal(
    val number: Int,
    val numberInYear: Int,
    val year: Int,
    val cover: String,
    val link: String,
    var coverDescription: String = "",
    var articles: List<Article> = listOf()
) {
    val title: String get() = "№ $numberInYear ($number)"
}
