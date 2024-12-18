package com.puce.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging
        .HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdaptadorRetrofit {
    Retrofit retrofit;

    public AdaptadorRetrofit() {
    }

    public Retrofit getAdaptador() {
        // Crear un interceptor para capturar las solicitudes y respuestas HTTP
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);  // Nivel de detalle: BODY

        // Configurar el cliente HTTP con el interceptor
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        // Configurar Retrofit con el cliente HTTP y la URL base
        String URL = "https://atellar.com.ec/";
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)  // Agregar el cliente con el interceptor
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}

