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

public class DetailActivity  extends AppCompatActivity {

    private static final String URL_DATA = "https://api.nytimes.com/svc/movies/v2/reviews/search.json?api-key=211b58de33064238b255f09b04a38274";
    public TextView textViewHeadet;
    public TextView textViewDescet;
    public TextView textViewReview;
    public ImageView imageViewPhoto;
    private Integer mPostkey = null;
    public String url;
    public String urlGambar;
    PinnedItem pinnedItem;
    boolean isPressed = true;
    ArrayList<PinnedItem> fItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPostkey = getIntent().getExtras().getInt("blog_id");
        loadRecyclerViewData(); //menampilkan detail pada sesuatu yg dipilih

        textViewHeadet = (TextView) findViewById(R.id.textViewHeadet);
        textViewDescet = (TextView) findViewById(R.id.textViewDescet);
        textViewReview = (TextView) findViewById(R.id.textViewReview);
        imageViewPhoto = (ImageView) findViewById(R.id.imageViewDetail);

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
        String judul = textViewHeadet.getText().toString();
        String deskripsi = textViewDescet.getText().toString();
        String urlgambar = urlGambar;
        pinnedItem = new PinnedItem(judul, deskripsi, urlgambar);
        pinnedItem.save();

        SharedPreferences.Editor editor = getSharedPreferences(judul, MODE_PRIVATE).edit();
        editor.putBoolean("isNew", true);
        editor.commit();
    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("results");
                            JSONObject o = array.getJSONObject(mPostkey);

                            setTitle(" ");
                            textViewHeadet.setText(o.getString("display_title"));
                            textViewDescet.setText(" Producer " + "\n" + o.getString("byline"));
                            textViewReview.setText(" Summary " + "\n" + o.getString("summary_short"));
                            url = o.getJSONObject("link").getString("url");
                            urlGambar = o.getJSONObject("multimedia").getString("src");
                            Glide
                                    .with(DetailActivity.this)
                                    .load(o.getJSONObject("multimedia").getString("src"))
                                    .into(imageViewPhoto);


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

