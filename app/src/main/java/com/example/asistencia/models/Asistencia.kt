package com.example.asistencia.models

data class Asistencia(
    val empleado: Empleado,
    val horaEntrada: String,
    var horaSalida: String? = null,
    val id: Long? = null
)
