package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.miniproject02.adapter.TheaterAdapter;
import com.example.miniproject02.database.AppDatabase;
import com.example.miniproject02.model.Movie;
import com.example.miniproject02.model.Theater;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements TheaterAdapter.OnTheaterClickListener {
    private TextView tvTitle, tvGenre, tvDuration, tvDesc;
    private RecyclerView rvTheaters;
    private AppDatabase db;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        db = AppDatabase.getInstance(this);

        tvTitle = findViewById(R.id.tvDetailTitle);
        tvGenre = findViewById(R.id.tvDetailGenre);
        tvDuration = findViewById(R.id.tvDetailDuration);
        tvDesc = findViewById(R.id.tvDetailDesc);
        rvTheaters = findViewById(R.id.rvTheaters);
        rvTheaters.setLayoutManager(new LinearLayoutManager(this));

        movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        if (movieId != -1) {
            Movie movie = db.movieDao().getMovieById(movieId);
            if (movie != null) {
                tvTitle.setText(movie.title);
                tvGenre.setText("Genre: " + movie.genre);
                tvDuration.setText("Duration: " + movie.duration + " mins");
                tvDesc.setText(movie.description);

                List<Theater> theaters = db.theaterDao().getTheatersForMovie(movieId);
                TheaterAdapter adapter = new TheaterAdapter(theaters, this);
                rvTheaters.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onTheaterClick(Theater theater) {
        Intent intent = new Intent(this, ShowtimeActivity.class);
        intent.putExtra("MOVIE_ID", movieId);
        intent.putExtra("THEATER_ID", theater.id);
        startActivity(intent);
    }
}
