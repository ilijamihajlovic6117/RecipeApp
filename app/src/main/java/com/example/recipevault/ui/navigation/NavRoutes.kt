package com.example.recipevault.ui.navigation

object NavRoutes {
    const val LIST = "list"
    const val DETAIL = "detail/{id}"
    const val FORM = "form?id={id}"

    fun detail(id: Long) = "detail/$id"
    fun form(id: Long? = null) = if (id == null) "form" else "form?id=$id"
}
