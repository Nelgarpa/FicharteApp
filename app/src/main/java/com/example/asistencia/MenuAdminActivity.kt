package com.example.asistencia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuAdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_menu_admin)

        val btnAdministradores = findViewById<Button>(R.id.btnAdministradores)
        val btnEmpleados = findViewById<Button>(R.id.btnEmpleados)
        val btnAsistencias = findViewById<Button>(R.id.btnAsistencias)


        btnAdministradores.setOnClickListener {
            startActivity(Intent(this, AdministradoresActivity::class.java))
        }

        btnEmpleados.setOnClickListener {
            startActivity(Intent(this, EmpleadosActivity::class.java))
        }

        btnAsistencias.setOnClickListener {
            startActivity(Intent(this, AsistenciasActivity::class.java))
        }

        btnAsistencias.setOnClickListener {
            val intent = Intent(this, EstadisticasActivity::class.java)
            startActivity(intent)
        }
    }
}