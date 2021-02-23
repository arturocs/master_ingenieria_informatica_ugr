package com.dss.cortessanchezarturosanchezalbaabeljose.api;

import com.dss.cortessanchezarturosanchezalbaabeljose.models.Computer;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ComputerApiService {

    @GET("api/computers")
    Call<ArrayList<Computer>> getAllComputers();


}
