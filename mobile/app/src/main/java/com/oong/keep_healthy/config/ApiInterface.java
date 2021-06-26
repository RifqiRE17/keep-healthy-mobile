package com.oong.keep_healthy.config;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("login")
    @FormUrlEncoded
    @Headers("Accept: */*")
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);

}
