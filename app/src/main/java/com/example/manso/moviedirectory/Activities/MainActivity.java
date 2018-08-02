package com.example.manso.moviedirectory.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.manso.moviedirectory.Data.MovieRecyclerViewAdapter;
import com.example.manso.moviedirectory.Model.Movie;
import com.example.manso.moviedirectory.R;
import com.example.manso.moviedirectory.Util.Constants;
import com.example.manso.moviedirectory.Util.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private List<Movie> movieList;
    private RequestQueue queue;
    private AlertDialog.Builder alertDialogBuilder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queue = Volley.newRequestQueue(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ALERT DIALOG WILL BE CREATED LATER
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieList = new ArrayList<>();

        Prefs prefs = new Prefs(MainActivity.this);
        String search = prefs.getSearch();
        //getMovies(search);

        movieList = getMovies(search);
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this, movieList);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        movieRecyclerViewAdapter.notifyDataSetChanged();

    }

    //GET MOVIES
    public List<Movie> getMovies (String searchTerm){
        movieList.clear();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.URL_LEFT + "batman" + Constants.URL_RIGHT + Constants.API_KEY,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray moviesArray = response.getJSONArray("Search");

                    for(int i = 0; i<moviesArray.length();i++){
                        JSONObject movieObject = moviesArray.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setTitle(movieObject.getString("Title"));
                        movie.setYear("Year released: " + movieObject.getString("Year"));
                        movie.setType("Type: " + movieObject.getString("Type"));
                        movie.setPosterURL(movieObject.getString("Poster"));
                        movie.setId(movieObject.getString("imdbID"));

                        //Log.d("Movies: ", movie.getTitle());
                        movieList.add(movie);
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Toast.makeText(getApplicationContext(), "adding to queue 1", Toast.LENGTH_LONG).show();
        queue.add(jsonObjectRequest);

        return movieList;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showInputDialog(){

        alertDialogBuilder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.dialog_view, null);
    }
}
