package com.example.miniproject02.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "theaters")
public class Theater {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String location;

    public Theater(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
