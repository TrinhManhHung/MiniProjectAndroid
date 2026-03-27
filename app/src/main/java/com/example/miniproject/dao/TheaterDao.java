package com.example.miniproject02.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.miniproject02.model.Theater;
import java.util.List;

@Dao
public interface TheaterDao {
    @Insert
    void insertAll(Theater... theaters);

    @Query("SELECT * FROM theaters")
    List<Theater> getAllTheaters();

    @Query("SELECT DISTINCT t.* FROM theaters t INNER JOIN showtimes s ON t.id = s.theaterId WHERE s.movieId = :movieId")
    List<Theater> getTheatersForMovie(int movieId);
}
