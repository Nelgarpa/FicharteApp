package com.example.asistencia.models

data class Empleado(
    var nombre: String,
    var apellido: String,
    var dni: String,
    var id: Long? = null
)
