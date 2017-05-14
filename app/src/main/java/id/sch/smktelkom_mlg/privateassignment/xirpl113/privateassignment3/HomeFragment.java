package id.sch.smktelkom_mlg.privateassignment.xirpl113.privateassignment3;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String URL_DATA ="https://api.nytimes.com/svc/movies/v2/reviews/search.json?api-key=211b58de33064238b255f09b04a38274";
    public List<HomeListItem> listItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItems = new ArrayList<>();

        setTitle(getIntent().getStringExtra(MainActivity.SOURCENAME));

        downloadDataArticles();

        loadRecyclerViewData();
        // Inflate the layout for this fragment
        return view;
    }

    private void downloadDataArticles()
    {
        String id = getIntent().getStringExtra(MainActivity.SOURCEID);
        String sortby = getIntent().getStringExtra(MainActivity.SOURCESORTBY);
        String url = "https://newsapi.org/v1/articles?source=" + id
                + "&sortBy=" + sortby + "&apiKey=d66e77860d7d4fa29be1832f09f8f996";

        GsonGetRequest<ArticlesResponse> myRequest = new GsonGetRequest<ArticlesResponse>
                (url, ArticlesResponse.class, null, new Response.Listener<ArticlesResponse>()
                {

                    @Override
                    public void onResponse(ArticlesResponse response)
                    {
                        Log.d("FLOW", "onResponse: " + (new Gson().toJson(response)));
                        if (response.status.equals("ok"))
                        {
                            mList.addAll(response.articles);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                }, new Response.ErrorListener()
                {

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("FLOW", "onErrorResponse: ", error);
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(myRequest);
    }

    @Override
    public void showDetail(String url)
    {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading data ...");
        progressDialog.show();

        //tigas baris diatas untuk menampilkan laodinng dalam pengambilan data

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();//untuk memghilangkan loading saat data sdh di dapat.
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            JSONArray array = jsonObject.getJSONArray("results");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                HomeListItem item = new HomeListItem(
                                        o.getJSONObject("multimedia").getString("src"),
                                        o.getString("display_title"),
                                        o.getString("byline")
                                );
                                listItems.add(item);
                            }
                            adapter = new HomeAdapter(listItems, getActivity().getApplication());//apabila di acyvity getactivity hanya ditulis this saja.
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }

                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
