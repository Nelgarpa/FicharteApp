package com.example.asistencia

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asistencia.models.Administrador
import com.example.asistencia.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarAdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_agregar_admin)

        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoAdmin = Administrador(null ,usuario, password)

            RetrofitClient.instance.createAdministrador(nuevoAdmin)
                .enqueue(object : Callback<Administrador> {
                    override fun onResponse(call: Call<Administrador>, response: Response<Administrador>) {
                        Toast.makeText(this@AgregarAdminActivity, "Administrador creado", Toast.LENGTH_SHORT).show()
                        finish() // Vuelve a la lista
                    }

                    override fun onFailure(call: Call<Administrador>, t: Throwable) {
                        Toast.makeText(this@AgregarAdminActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
        }
    }
}