package com.example.miniproject02.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String genre;
    public int duration;
    public String description;

    public Movie(String title, String genre, int duration, String description) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.description = description;
    }
}
