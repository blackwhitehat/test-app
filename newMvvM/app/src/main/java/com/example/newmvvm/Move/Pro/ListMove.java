
package com.example.newmvvm.Move.Pro;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ListMove {
    private List<Search> Search;

    private String totalResults;

    private String Response;

    public void setSearch(List<Search> Search){
        this.Search = Search;
    }
    public List<Search> getSearch(){
        return this.Search;
    }
    public void setTotalResults(String totalResults){
        this.totalResults = totalResults;
    }
    public String getTotalResults(){
        return this.totalResults;
    }
    public void setResponse(String Response){
        this.Response = Response;
    }
    public String getResponse(){
        return this.Response;
    }
}
