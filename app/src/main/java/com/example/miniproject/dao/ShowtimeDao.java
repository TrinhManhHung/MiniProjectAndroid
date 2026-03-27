package com.example.miniproject02.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.miniproject02.model.Showtime;
import java.util.List;

@Dao
public interface ShowtimeDao {
    @Insert
    void insertAll(Showtime... showtimes);

    @Query("SELECT * FROM showtimes WHERE movieId = :movieId")
    List<Showtime> getShowtimesForMovie(int movieId);

    @Query("SELECT * FROM showtimes WHERE movieId = :movieId AND theaterId = :theaterId")
    List<Showtime> getShowtimesForMovieAndTheater(int movieId, int theaterId);

    @Query("SELECT * FROM showtimes")
    List<Showtime> getAllShowtimes();

    @Query("SELECT * FROM showtimes WHERE id = :id")
    Showtime getShowtimeById(int id);
}
