
package com.example.asistencia.models

data class Estadisticas(
    val horasTrabajadas: String,
    val diasAsistidos: Int,
    val diasFaltados: Int,
    val resumenSemanal: Map<String, Boolean>
)