package com.example.sunidhi.inclass04;


import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ayush on 7/13/2018.
 */

public interface APIInterface {

    @POST("list")
    Call<ArrayList<Results.ResultsValue>> getProducts(@Body ProductRequest productRequest);

    @POST("get_image")
    Call<ResponseBody> getImage(@Body ImageRequest imageRequest);
}
