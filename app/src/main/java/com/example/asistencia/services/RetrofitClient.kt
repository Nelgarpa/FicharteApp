package com.example.asistencia.services
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val instance: ApiService by lazy {
        Retrofit.Builder()
          //  .baseUrl("http://192.168.1.14:8081/")
           .baseUrl("http://10.0.2.2:8081/")
          .addConverterFactory(GsonConverterFactory.create())
           .build()
           .create(ApiService::class.java)
    }
}
/*
package com.example.asistencia.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val baseUrl: String
        get() {
            return if (isEmulator()) {
                // Emulador: usar localhost del PC anfitrión
                "http://10.0.2.2:8081/"
            } else {
                // Dispositivo físico: requiere adb reverse para que funcione
                "http://127.0.0.1:8081/"
            }
        }

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun isEmulator(): Boolean {
        return android.os.Build.FINGERPRINT.lowercase().contains("generic") ||
                android.os.Build.MODEL.lowercase().contains("emulator") ||
                android.os.Build.MODEL.lowercase().contains("android sdk built for x86") ||
                android.os.Build.MANUFACTURER.lowercase().contains("genymotion")
    }
}*/
