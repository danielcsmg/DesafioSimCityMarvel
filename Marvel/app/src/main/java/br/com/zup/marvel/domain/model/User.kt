package br.com.zup.marvel.domain.model

data class User(
    var name: String = "",
    val mail: String,
    val password: String = ""
)