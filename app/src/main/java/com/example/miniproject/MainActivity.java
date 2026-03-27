package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject02.adapter.MovieAdapter;
import com.example.miniproject02.database.AppDatabase;
import com.example.miniproject02.model.Movie;
import com.example.miniproject02.model.Showtime;
import com.example.miniproject02.model.Theater;
import com.example.miniproject02.model.User;
import com.example.miniproject02.utils.SessionManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {
    private RecyclerView rvMovies;
    private AppDatabase db;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = AppDatabase.getInstance(this);
        sessionManager = new SessionManager(this);

        rvMovies = findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        initDummyData();
        loadMovies();
    }

    private void initDummyData() {
        // Thêm User mặc định nếu chưa có
        if (db.userDao().findByUsername("admin") == null) {
            db.userDao().insert(new User("admin", "123", "Administrator"));
        }

        if (db.movieDao().getAllMovies().isEmpty()) {
            Movie m1 = new Movie("Dune: Part Two", "Sci-Fi", 166, "Paul Atreides unites with Chani and the Fremen.");
            Movie m2 = new Movie("Oppenheimer", "Biography", 180, "The story of American scientist J. Robert Oppenheimer.");
            Movie m3 = new Movie("Spider-Man: Across the Spider-Verse", "Animation", 140, "Miles Morales catapults across the Multiverse.");
            db.movieDao().insertAll(m1, m2, m3);

            Theater t1 = new Theater("CGV Vincom", "District 1, HCMC");
            Theater t2 = new Theater("Lotte Cinema", "District 7, HCMC");
            db.theaterDao().insertAll(t1, t2);

            db.showtimeDao().insertAll(
                    new Showtime(1, 1, "2023-10-27 10:00", 100000),
                    new Showtime(1, 2, "2023-10-27 14:00", 90000),
                    new Showtime(2, 1, "2023-10-27 18:00", 120000),
                    new Showtime(3, 2, "2023-10-27 20:00", 85000)
            );
        }
    }

    private void loadMovies() {
        List<Movie> movies = db.movieDao().getAllMovies();
        MovieAdapter adapter = new MovieAdapter(movies, this);
        rvMovies.setAdapter(adapter);
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("MOVIE_ID", movie.id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem loginItem = menu.findItem(R.id.action_login);
        MenuItem logoutItem = menu.findItem(R.id.action_logout);
        
        if (sessionManager.isLoggedIn()) {
            loginItem.setVisible(false);
            logoutItem.setVisible(true);
            logoutItem.setTitle("Logout (" + sessionManager.getUsername() + ")");
        } else {
            loginItem.setVisible(true);
            logoutItem.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_login) {
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        } else if (id == R.id.action_logout) {
            sessionManager.logout();
            invalidateOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }
}
