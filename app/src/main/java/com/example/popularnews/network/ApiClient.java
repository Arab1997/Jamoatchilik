package com.example.popularnews.network;


import com.example.popularnews.model.Example;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {

    // http://jamoatchilik.uz/all.php?page=2
    @GET("list.php")
    Call<List<Example>> getResult();
    //   @Query("page") int pageIndex
    @GET("page=")
    Call<List<Example>> getPage(

            String page2,
            String page3,
            String page4,
            String page5

    );
}