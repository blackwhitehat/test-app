package com.example.newmvvm.Details;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.newmvvm.Details.ModelDetail.DetalList;
import com.example.newmvvm.Details.ModelDetail.Rating;
import com.example.newmvvm.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;

public class Detail extends AppCompatActivity {

    private String[] title;
    ProgressDialog pd;
    private TextView writer,point;
    private RatingBar ratingBar;
    private TextView det_txt,det_country,det_yr,det_direct;
    private ImageView det_poster;
    private FloatingActionButton fab;
    List<DetalList> detalListList=new ArrayList<>();
    List<Rating> ratings=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        set();

        Bundle ex=getIntent().getExtras();

         new AT(ex.getString("key")).execute();
        CollapsingToolbarLayout ct = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ct.setTitle("MoveLinkDoni");
    }

    private void set() {
        writer=findViewById(R.id.det_writer);
        det_txt=findViewById(R.id.det_txt);
        det_country=findViewById(R.id.det_country);
        det_yr=findViewById(R.id.det_year_runtime);
        det_direct=findViewById(R.id.det_director);
        det_poster=findViewById(R.id.det_poster);
        point=findViewById(R.id.det_point);
        ratingBar=findViewById(R.id.det_rate);
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(Detail.this,"امتیاز ثبت شد",Toasty.LENGTH_LONG).show();
            }
        });
    }

    public class AT extends AsyncTask
    {

        private String val;

        public AT(String val) {
            this.val = val;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(Detail.this);
            pd.setTitle("صبرکنید.");
            pd.setMessage("در حال ارسال...");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String URLline="http://www.omdbapi.com/?apikey=3e974fca&i="+val;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            parseData(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            // request queue
            RequestQueue requestQueue = Volley.newRequestQueue(Detail.this);
            requestQueue.add(stringRequest);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);


        }
    }




    public void parseData(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            title=new String[jsonObject.length()];
            for (int i = 0; i < jsonObject.length(); i++) {
                    DetalList list=new DetalList();
                    list.setTitle(jsonObject.getString("Title"));
                    list.setYear(jsonObject.getString("Year"));
                    list.setReleased(jsonObject.getString("Released"));
                    list.setRuntime(jsonObject.getString("Runtime"));
                    list.setGenre(jsonObject.getString("Genre"));
                    list.setDirector(jsonObject.getString("Director"));
                    list.setWriter(jsonObject.getString("Writer"));
                    list.setPlot(jsonObject.getString("Plot"));
                    list.setLanguage(jsonObject.getString("Language"));
                    list.setCountry(jsonObject.getString("Country"));
                    list.setPoster(jsonObject.getString("Poster"));
                    detalListList.add(list);
                }
            JSONObject jObject = new JSONObject(response);
            JSONArray jsonArray=jObject.getJSONArray("Ratings");
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Rating rat=new Rating();
                rat.setSource(object.getString("Source"));
                rat.setValue(object.getString("Value"));
                ratings.add(rat);
            }
           // Toast.makeText(this, detalListList.get(0).getDirector()+"", Toast.LENGTH_SHORT).show();
            det_direct.setText("Director:"+detalListList.get(0).getDirector());
            det_txt.setText(detalListList.get(0).getTitle());
            det_country.setText("Country:"+detalListList.get(0).getCountry());
            det_yr.setText(detalListList.get(0).getRuntime()+"/"+detalListList.get(0).getReleased());
            Picasso.get().load(detalListList.get(0).getPoster()).into(det_poster);
            writer.setText(detalListList.get(0).getWriter());
            point.setText(ratings.get(0).getValue());
            String b=point.getText().toString().substring(0,3);

            ratingBar.setRating(Float.parseFloat(b));
            pd.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "err", Toast.LENGTH_SHORT).show();
        }

    }


}
