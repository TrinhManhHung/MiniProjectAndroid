package com.example.miniproject02.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tickets",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Showtime.class,
                        parentColumns = "id",
                        childColumns = "showtimeId",
                        onDelete = ForeignKey.CASCADE)
        })
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId;
    public int showtimeId;
    public String seatNumber;
    public String bookingTime;

    public Ticket(int userId, int showtimeId, String seatNumber, String bookingTime) {
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatNumber = seatNumber;
        this.bookingTime = bookingTime;
    }
}
