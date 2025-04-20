package com.example.asistencia.models

data class Historial(
    val fecha: String,
    val totalHoras: String,
    val fichajes: List<Fichaje>
)
