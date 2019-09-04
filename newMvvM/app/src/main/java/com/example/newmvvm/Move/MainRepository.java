package com.example.newmvvm.Move;

import com.example.newmvvm.Move.Pro.ListMove;
import com.example.newmvvm.Move.Pro.Search;

import java.util.List;

import io.reactivex.Single;

public class MainRepository implements MainDataSource{
    ApiMainDataSource apiMainDataSource=new ApiMainDataSource();
    @Override
    public Single<List<ListMove>> getList() {
        return apiMainDataSource.getList();
    }
}
