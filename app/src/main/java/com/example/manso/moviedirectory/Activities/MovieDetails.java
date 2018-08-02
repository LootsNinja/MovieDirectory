package com.example.manso.moviedirectory.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.manso.moviedirectory.Model.Movie;
import com.example.manso.moviedirectory.R;
import com.example.manso.moviedirectory.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetails extends AppCompatActivity {
    private Movie movie;
    private  TextView movieTitle;
    private  TextView movieYear;
    private  TextView director;
    private  TextView actors;
    private  TextView category;
    private  TextView rating;
    private  TextView writers;
    private  TextView plot;
    private  TextView boxOffice;
    private  TextView runTime;
    private  ImageView movieImage;

    private  RequestQueue queue;
    private String movieID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        queue = Volley.newRequestQueue(this);

        movie = (Movie) getIntent().getSerializableExtra("movie");
        movieID = movie.getId();

        setUpUI();

        setMovieDetails(movieID);




    }



    public  void setMovieDetails(String id) {


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.URL + id + Constants.API_KEY, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(response.has("ratings")){
                        JSONArray ratings = response.getJSONArray("Ratings");

                        String source = null;
                        String value = null;

                        if (ratings.length()>0){
                            JSONObject mRatings = ratings.getJSONObject(ratings.length()-1);
                            source = mRatings.getString("Source");
                            value = mRatings.getString("value");
                            rating.setText(source + value);
                        }else{
                            rating.setText("Ratings: Not Available");
                        }

                        movieTitle.setText(response.getString("Title"));
                        movieYear.setText("Released: " + response.getString("Year"));
                        director.setText("Director: " + response.getString("Director"));
                        writers.setText("Writers: " + response.getString("Writers"));
                        plot.setText("Plot: " + response.getString("Plot"));
                        runTime.setText("Runtime: " + response.getString("Runtime"));
                        actors.setText("Actors: " + response.getString("Actors"));

                        Picasso.get()
                                .load(response.getString("Poster"))
                                .into(movieImage);

                        boxOffice.setText(response.getString("BoxOffice"));
                        //Toast.makeText(getApplicationContext(), "Setting data", Toast.LENGTH_LONG).show();
                    }

                }catch(JSONException e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "JSON Error", Toast.LENGTH_LONG).show();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("Error: ", error.getMessage());

            }
        });
        queue.add(jsonObjectRequest);
    }


    private void setUpUI() {
        movieTitle = (TextView) findViewById(R.id.movieTitleIDDetails);
        movieImage = (ImageView) findViewById(R.id.movieImageIDDetails);
        movieYear = (TextView) findViewById(R.id.movieReleaseIDDetails);
        director = (TextView) findViewById(R.id.directedByDetails);
        category = (TextView) findViewById(R.id.movieCategoryIDDetails);
        rating = (TextView) findViewById(R.id.movieRatingIDDetails);
        writers = (TextView) findViewById(R.id.writersDetails);
        plot = (TextView) findViewById(R.id.plotDetails);
        boxOffice = (TextView) findViewById(R.id.boxOfficeDetails);
        runTime = (TextView) findViewById(R.id.runTimeDetails);
        actors = (TextView) findViewById(R.id.actorsDetails);
        Toast.makeText(getApplicationContext(), "setUpUI", Toast.LENGTH_LONG).show();

    }


}
