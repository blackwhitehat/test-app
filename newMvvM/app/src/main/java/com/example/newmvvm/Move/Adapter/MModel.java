package com.example.newmvvm.Move.Adapter;

public class MModel
{
    private String Title;

    private String Year;

    private String imdbID;

    private String Type;

    private String Poster;

    public void setTitle(String Title){
        this.Title = Title;
    }
    public String getTitle(){
        return this.Title;
    }
    public void setYear(String Year){
        this.Year = Year;
    }
    public String getYear(){
        return this.Year;
    }
    public void setImdbID(String imdbID){
        this.imdbID = imdbID;
    }
    public String getImdbID(){
        return this.imdbID;
    }
    public void setType(String Type){
        this.Type = Type;
    }
    public String getType(){
        return this.Type;
    }
    public void setPoster(String Poster){
        this.Poster = Poster;
    }
    public String getPoster(){
        return this.Poster;
    }

}
