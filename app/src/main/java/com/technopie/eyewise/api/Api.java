package com.technopie.eyewise.api;

import com.technopie.eyewise.model.UserResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("createuser")
    Call<ResponseBody> createUser(
            @Field("name") String name,
            @Field("phone_no") String phone,
            @Field("email") String username,
            @Field("password") String password,
            @Field("dob") String designation,
            @Field("pin_code") String last_login
    );

    @FormUrlEncoded
    @POST("userlogin")
    Call<UserResponse> userLogin(
            @Field("email") String username,
            @Field("password") String password
    );
}
