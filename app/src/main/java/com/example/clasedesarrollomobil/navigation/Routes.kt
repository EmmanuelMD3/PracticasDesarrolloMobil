package com.example.clasedesarrollomobil.navigation

object Routes {
    const val LOGIN = "login"
    const val MENU = "menu"
    const val TEXTO = "texto"
    const val BOTONES = "botones"
    const val SELECCION = "seleccion"
    const val LISTAS = "listas"
    const val MULTIMEDIA = "multimedia"
    const val BARRAS = "barras"
    const val NAVEGACION = "navegacion"
    const val NAVEGACION_DETALLE = "navegacion_detalle"
    const val LAYOUT = "layout"
    const val FECHA_HORA = "fecha_hora"
    const val SCROLL = "scroll"
    const val DIALOGOS = "dialogos"
    const val MATERIAL = "material"
    const val GOOGLE = "google"
    const val ACERCA = "acerca"

    const val ARG_NOMBRE = "nombre"
    const val NAVEGACION_DETALLE_WITH_ARG = "$NAVEGACION_DETALLE/{$ARG_NOMBRE}"

    fun navegacionDetalle(nombre: String): String = "$NAVEGACION_DETALLE/$nombre"
}
