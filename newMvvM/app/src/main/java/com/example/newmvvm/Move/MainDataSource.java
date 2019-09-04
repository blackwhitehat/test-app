package com.example.newmvvm.Move;

import com.example.newmvvm.Move.Pro.ListMove;
import com.example.newmvvm.Move.Pro.Search;

import java.util.List;

import io.reactivex.Single;

public interface MainDataSource
{
    Single<List<ListMove>> getList();
}
