package com.example.oilimit.api;

import com.example.oilimit.model.cari.Cari;
import com.example.oilimit.model.login.Login;
import com.example.oilimit.model.register.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginResponse(
            @Field("handphone") String handphone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> registerResponse(
            @Field("handphone") String handphone,
            @Field("password") String password,
            @Field("nama") String nama
    );

    @FormUrlEncoded
    @POST("cari.php")
    Call<Cari> cariResponse(
            @Field("password") String password
    );
}
