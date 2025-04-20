package com.example.asistencia.services

import com.example.asistencia.models.Administrador
import com.example.asistencia.models.Asistencia
import com.example.asistencia.models.Empleado
import com.example.asistencia.models.Estadisticas
import com.example.asistencia.models.Historial
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("administradores/login")
    fun login(@Body admin: Administrador): Call<Boolean>

    @GET("administradores") // 👈 este sí responde en navegador del emulador
    fun test(): Call<ResponseBody>

    @GET("administradores")
    fun getAdministradores(): Call<List<Administrador>>

    @DELETE("administradores/{id}")
    fun deleteAdministrador(@Path("id") id: Long): Call<Void>

    @GET("administradores/{id}")
    fun getAdministradorById(@Path("id") id: Long): Call<Administrador>

    @PUT("administradores/{id}")
    fun actualizarAdministrador(@Path("id") id: Long, @Body admin: Administrador): Call<Administrador>

    @POST("administradores")
    fun createAdministrador(@Body admin: Administrador): Call<Administrador>


    /***********************Empleados******************/

    @GET("empleados")
    fun getEmpleados(): Call<List<Empleado>>

    @POST("empleados")
    fun createEmpleado(@Body empleado: Empleado): Call<Empleado>

    @GET("empleados/{id}")
    fun getEmpleadoById(@Path("id") id: Long): Call<Empleado>

    @PUT("empleados/{id}")
    fun updateEmpleado(@Path("id") id: Long, @Body empleado: Empleado): Call<Empleado>

    @DELETE("empleados/{id}")
    fun deleteEmpleado(@Path("id") id: Long): Call<Void>

    /***********************Asistencias******************/

    @POST("asistencias")
    fun fichar(@Body asistencia: Asistencia): Call<Asistencia>

    @GET("asistencias/ultimo")
    fun obtenerUltimaAsistenciaSinSalida(@Query("empleadoId") empleadoId: Long): Call<Asistencia>

    @PUT("asistencias/{id}")
    fun actualizarAsistencia(@Path("id") id: Long, @Body asistencia: Asistencia): Call<Asistencia>

    @PUT("asistencias/ficharSalida")
    fun ficharSalida(@Query("empleadoId") empleadoId: Long): Call<Asistencia>

    /**************************Estadisticas***********************/

    @GET("asistencias/estadisticas")
    fun obtenerEstadisticasEmpleado(@Query("empleadoId") empleadoId: Long): Call<Estadisticas>

    @GET("asistencias/historial")
    fun getHistorial(@Query("empleadoId") empleadoId: Long): Call<List<Historial>>

}