package ru.korolevss.l_tech.model

data class Post(
    var date: String,
    val id: String,
    var image: String,
    val sort: Int,
    val text: String,
    val title: String
)