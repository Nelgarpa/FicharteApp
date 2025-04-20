package com.example.asistencia

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import com.example.asistencia.R
import com.example.asistencia.models.Administrador
import com.example.asistencia.services.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_admin)

        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)

        btnEntrar.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val password = etContrasena.text.toString().trim()

            if (usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa ambos campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val admin = Administrador(null ,usuario, password)

            val call = RetrofitClient.instance.login(admin)


//            call.enqueue(object : Callback<Boolean> {
//                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
//                    val acceso = response.body() ?: false
//                    if (acceso) {
//                        Toast.makeText(this@AdminActivity, "Acceso concedido", Toast.LENGTH_SHORT).show()
//                        // Aquí podrías lanzar otra pantalla o guardar sesión
//                    } else {
//                        Toast.makeText(this@AdminActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//
//                override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                    t.printStackTrace()
//                    Toast.makeText(this@AdminActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
//                }
//
//            })

            RetrofitClient.instance.login(admin).enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    val acceso = response.body() ?: false
                    if (acceso) {
                        Toast.makeText(this@AdminActivity, "Acceso concedido", Toast.LENGTH_SHORT).show()

                        // 👉 Ir al menú administrativo
                        val intent = Intent(this@AdminActivity, MenuAdminActivity::class.java)
                        startActivity(intent)
                        finish() // para que no pueda volver con el botón atrás
                    } else {
                        Toast.makeText(this@AdminActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(this@AdminActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })

        }
    }
}