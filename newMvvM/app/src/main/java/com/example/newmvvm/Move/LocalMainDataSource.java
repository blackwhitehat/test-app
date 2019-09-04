package com.example.newmvvm.Move;

import com.example.newmvvm.Model.ApiProvider;
import com.example.newmvvm.Move.Pro.ListMove;
import com.example.newmvvm.Move.Pro.Search;

import java.util.List;

import io.reactivex.Single;

public class LocalMainDataSource implements MainDataSource{
    @Override
    public Single<List<ListMove>> getList() {
        return ApiProvider.apiProvider().getList();
    }
}
