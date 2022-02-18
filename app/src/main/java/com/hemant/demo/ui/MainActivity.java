package com.hemant.demo.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.hemant.demo.R;
import com.hemant.demo.adapter.MovieAdapter;
import com.hemant.demo.data.Movie;
import com.hemant.demo.data.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);

        mTitle.setText(toolbar.getTitle());
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        movieList = new ArrayList<>();

        fetchMovies();
    }

    private void fetchMovies() {

        String url = "https://mocki.io/v1/bdf9cc29-fc34-4c61-b143-d47593ac2576";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0 ; i < response.length() ; i ++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String title = jsonObject.getString("title");
                            String overview = jsonObject.getString("overview");
                            String poster = jsonObject.getString("poster");
                            Double rating = jsonObject.getDouble("rating");

                            Movie movie = new Movie(title , poster , overview , rating);
                            movieList.add(movie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        MovieAdapter adapter = new MovieAdapter(MainActivity.this , movieList);
                        recyclerView.setAdapter(adapter);
                    }
                }, error -> Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show());
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}