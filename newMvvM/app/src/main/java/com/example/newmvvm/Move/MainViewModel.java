package com.example.newmvvm.Move;

import com.example.newmvvm.Move.Pro.ListMove;
import com.example.newmvvm.Move.Pro.Search;

import java.util.List;

import io.reactivex.Single;

public class MainViewModel
{
    MainRepository repository=new MainRepository();
    public Single<List<ListMove>> getList()
    {
        return repository.getList();
    }
}
