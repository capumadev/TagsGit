package com.puce.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface APIRest {
    @GET("API_RESTC2.php")
    Call<List<User>> obtenerUsuarios();

    @GET("API_RESTC2.php")
    Call<User> obtenerUsuario(
            @Query("record") String record
    );

    @POST("API_RESTC2.php")
    Call<Void> agregarUsuario(
            @Body User usuario
    );

    @PUT("API_RESTC2.php")
    Call<Void> editarUsuario(
            @Body User usuario
    );

    @DELETE("API_RESTC2.php")
    Call<Void> eliminarUsuario(
            @Query("record") String record
    );
}
