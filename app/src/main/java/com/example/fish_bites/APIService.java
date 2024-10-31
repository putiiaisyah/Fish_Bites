package com.example.fish_bites;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {
    @POST("login") // Adjust the endpoint according to your Laravel route
    Call<loginresponse> login(@Body loginrequest loginerequest);
}
