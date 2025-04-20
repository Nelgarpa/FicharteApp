package com.example.asistencia

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asistencia.models.Empleado
import com.example.asistencia.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarEmpleadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_agregar_empleado)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellido = findViewById<EditText>(R.id.etApellido)
        val etDni = findViewById<EditText>(R.id.etDni)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarEmpleado)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()
            val dni = etDni.text.toString().trim()

            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val empleado = Empleado(nombre, apellido, dni)

            RetrofitClient.instance.createEmpleado(empleado)
                .enqueue(object : Callback<Empleado> {
                    override fun onResponse(call: Call<Empleado>, response: Response<Empleado>) {
                        Toast.makeText(this@AgregarEmpleadoActivity, "Empleado creado", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                    override fun onFailure(call: Call<Empleado>, t: Throwable) {
                        Toast.makeText(this@AgregarEmpleadoActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}
