package com.example.newmvvm;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.newmvvm.AllLink.AllMove;
import com.example.newmvvm.AppController.AppController;
import com.example.newmvvm.Details.Detail;
import com.example.newmvvm.Move.Adapter.MAdapter;
import com.example.newmvvm.Move.Adapter.MModel;
import com.example.newmvvm.Move.MainViewModel;
import com.example.newmvvm.Move.Pro.ListMove;
import com.example.newmvvm.Move.Pro.Search;
import com.example.newmvvm.Move.Save_Info;
import com.example.newmvvm.Spl.Splash;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity  {


    private CarouselView carouselView;
    private MainViewModel viewModel;
    private RecyclerView recyclerView;
    private MAdapter adapter;
    private List<MModel> data=new ArrayList<>();
    private String[] title,year,imdbId,type,poster;
    private Save_Info saveInfo;
    private TextView allpro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carouselView=findViewById(R.id.carouselView);



       saveInfo=new Save_Info(this);

        set();
        carouselView.setImageListener(imageListener);
       // new AA().execute();
        if (saveInfo.GetInfo().equals("null"))
        {
            startActivity(new Intent(getApplicationContext(), Splash.class));
        }else{

            new AA().execute();
        }


allpro.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), AllMove.class));
    }
});


        //get();
    }

    private void set()
    {
        allpro=findViewById(R.id.main_all);
        recyclerView=findViewById(R.id.main_rcv);
        recyclerView.setHasFixedSize(true);


    }

    private void get()
    {
        viewModel=new MainViewModel();
        viewModel.getList().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<ListMove>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<ListMove> searches) {
                Log.i("tag","success");

                //
            }

            @Override
            public void onError(Throwable e) {
                Log.i("tag","err->"+e.toString());

            }
        });
    }

    private void getListAll() {
        String url="http://www.omdbapi.com/?apikey=3e974fca&s=batman";

        Response.Listener<String> listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String strjson) {
                //Toast.makeText(MainActivity.this, strjson+"", Toast.LENGTH_SHORT).show();

                if (strjson!=null) {
                    try {
                        JSONObject jsonObject=new JSONObject(strjson);
                        JSONArray jsonArray=jsonObject.getJSONArray("Search");
                        title=new String[jsonArray.length()];
                        year=new String[jsonArray.length()];
                        imdbId=new String[jsonArray.length()];
                        poster=new String[jsonArray.length()];
                        type=new String[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            MModel search=new MModel();
                            JSONObject object=jsonArray.getJSONObject(i);
                            title[i]=object.getString("Title");
                            year[i]=object.getString("Year");
                            imdbId[i]=object.getString("imdbID");
                            type[i]=object.getString("Type");
                            poster[i]=object.getString("Poster");
                            search.setYear(year[i]);
                            search.setTitle(title[i]);
                            search.setImdbID(imdbId[i]);
                            search.setType(type[i]);
                            search.setPoster(poster[i]);
                            data.add(search);

                        }

                        adapter=new MAdapter(MainActivity.this,data);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,true));
                        adapter.notifyDataSetChanged();
                        carouselView.setPageCount(poster.length);
                        carouselView.setImageListener(imageListener);
                       adapter.setPrime(new MAdapter.OnPrime() {
                           @Override
                           public void ocClick(int ids) {
                               Intent j=new Intent(getApplicationContext(),Detail.class);
                               j.putExtra("key",data.get(ids).getImdbID().toString());
                               startActivity(j);
                           }
                       });
                    } catch (Exception e) {

                    }

                }else

                    Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
            }
        };

        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,"خطا..."+"", Toast.LENGTH_SHORT).show();
            }
        };

        StringRequest request=new StringRequest(Request.Method.GET,url,listener,errorListener);
        AppController.getInstance().addToRequestQueue(request);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
             //Toast.makeText(getApplicationContext(), poster[position]+" ", Toast.LENGTH_SHORT).show();

            Picasso.get()
                    .load(poster[position])
                    .fit()
                    .into(imageView);
        }
    };


    public class AA extends AsyncTask{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            getListAll();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }
}
