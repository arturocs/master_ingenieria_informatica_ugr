package com.dss.cortessanchezarturosanchezalbaabeljose.api;

import retrofit2.Call;
import retrofit2.http.POST;


// Ahora mismo no se usa
public interface ClientApiService {
    @POST("api/clients/login")
    Call<Boolean> login();


    @POST("api/clients/login")
    Call<Boolean> register();
}
