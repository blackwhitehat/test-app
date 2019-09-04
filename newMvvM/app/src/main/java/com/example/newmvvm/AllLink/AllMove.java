package com.example.newmvvm.AllLink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.newmvvm.AllLink.PAdapter.AllProduct;
import com.example.newmvvm.AppController.AppController;
import com.example.newmvvm.Details.Detail;
import com.example.newmvvm.MainActivity;
import com.example.newmvvm.Move.Adapter.MAdapter;
import com.example.newmvvm.Move.Adapter.MModel;
import com.example.newmvvm.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllMove extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<MModel> data=new ArrayList<>();
    private AllProduct adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_move);
        recyclerView=findViewById(R.id.am_rcv);

        recyclerView.setHasFixedSize(true);
     getListAll();
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

                        for(int i=0;i<jsonArray.length();i++)
                        {
                            MModel search=new MModel();
                            JSONObject object=jsonArray.getJSONObject(i);
                            search.setTitle(object.getString("Title"));
                            search.setPoster(object.getString("Poster"));
                            data.add(search);

                        }

                        adapter=new AllProduct(AllMove.this,data);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(AllMove.this,3));
                        adapter.notifyDataSetChanged();


                    } catch (Exception e) {

                    }

                }else

                    Toast.makeText(AllMove.this, "No Data", Toast.LENGTH_SHORT).show();
            }
        };

        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AllMove.this,"خطا..."+"", Toast.LENGTH_SHORT).show();
            }
        };

        StringRequest request=new StringRequest(Request.Method.GET,url,listener,errorListener);
        AppController.getInstance().addToRequestQueue(request);
    }
}
