package com.example.newmvvm.Model;

import com.example.newmvvm.Move.Pro.ListMove;
import com.example.newmvvm.Move.Pro.Search;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface ApiService {

    @GET("?apikey=3e974fca&s=batman")
    Single<List<ListMove>> getList();
}
