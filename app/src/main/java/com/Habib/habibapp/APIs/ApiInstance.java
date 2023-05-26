package com.Habib.habibapp.APIs;

import com.Habib.habibapp.Response.GetPoetryResponse;
import com.Habib.habibapp.Response.deleteResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInstance {
    @GET("getPoetry.php")
    Call<GetPoetryResponse> getPoetry();

    @FormUrlEncoded
    @POST("deletePoetry.php")
    Call<deleteResponse> deletePoetry(@Field("poetry_id")String poetry_id);

    @FormUrlEncoded
    @POST("addPoetry.php")
    Call<deleteResponse> addPoetry(@Field("poetry_data")String poetryData ,@Field("poet_name") String poetName);

    @FormUrlEncoded
    @POST("updatePoetry.php")
    Call<deleteResponse> updatePoetry(@Field("poetry_data")String poetryData,@Field("poet_name")String poetName,@Field("id")String pId);
}
