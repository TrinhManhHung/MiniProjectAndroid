package com.example.miniproject02.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.miniproject02.model.Movie;
import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    void insertAll(Movie... movies);

    @Query("SELECT * FROM movies")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM movies WHERE id = :movieId")
    Movie getMovieById(int movieId);
}
