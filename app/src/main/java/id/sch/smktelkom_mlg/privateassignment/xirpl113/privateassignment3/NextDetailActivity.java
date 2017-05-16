package id.sch.smktelkom_mlg.privateassignment.xirpl113.privateassignment3;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NextDetailActivity extends AppCompatActivity {
    private static final String URL_DATA = "https://api.themoviedb.org/3/discover/movie?primary_release_year=2017&sort_by=release_date.dsc&api_key=07a414c01835fd0e21580fe28c87a19f";
    public TextView textViewTitle;
    public TextView textViewDesc;
    public TextView textViewRelease;
    public TextView textViewAnother;
    public ImageView imageViewPict;
    private Integer mKey = null;
    public String url;
    public String imageUrl;
    PinnedItem pinnedItem;
    boolean isPressed = true;
    ArrayList<PinnedItem> fItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mKey = getIntent().getExtras().getInt("blog_id");
        loadRecyclerViewData();
        
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewRelease = (TextView) findViewById(R.id.textViewRelease);
        textViewDesc = (TextView) findViewById(R.id.textViewDesc);
        textViewAnother = (TextView) findViewById(R.id.textViewAnother);
        imageViewPict = (ImageView) findViewById(R.id.imageViewDetail);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSimpan();
                Snackbar.make(view, "The Selected Movie is Bookmarked", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void doSimpan() {

        String judul = textViewTitle.getText().toString();
        String deskripsi = textViewDesc.getText().toString();
        String urlgambar = imageUrl;
        pinnedItem = new PinnedItem(judul, deskripsi, urlgambar);
        pinnedItem.save();

        SharedPreferences.Editor editor = getSharedPreferences(judul, MODE_PRIVATE).edit();
        editor.putBoolean("isNew", true);
        editor.commit();
    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ac) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(ac);
                            JSONArray array = jsonObject.getJSONArray("results");
                            JSONObject o = array.getJSONObject(mKey);

                            setTitle(o.getString("title"));
                            textViewTitle.setText(o.getString("title"));
                            textViewRelease.setText("Release Date " + "\n" + o.getString("release_date"));
                            textViewDesc.setText("Overview " + "\n" + o.getString("overview"));
                            textViewAnother.setText("Popularity : " + "\n" + o.getString("popularity"));
//                            url = o.getJSONObject("link").getString("url");
                            imageUrl = "http://image.tmdb.org/t/p/w500" + o.getString("backdrop_path");
                            Glide
                                    .with(NextDetailActivity.this)
                                    .load("http://image.tmdb.org/t/p/w500" + o.getString("backdrop_path"))
                                    .into(imageViewPict);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                    }

                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}