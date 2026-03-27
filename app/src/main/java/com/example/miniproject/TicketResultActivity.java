package com.example.miniproject02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.miniproject02.database.AppDatabase;
import com.example.miniproject02.model.Movie;
import com.example.miniproject02.model.Showtime;
import com.example.miniproject02.model.Theater;
import com.example.miniproject02.model.Ticket;
import com.example.miniproject02.model.User;

public class TicketResultActivity extends AppCompatActivity {
    private TextView tvMovie, tvTheater, tvTime, tvSeat, tvPrice, tvUser, tvBookingId;
    private Button btnHome;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_result);

        db = AppDatabase.getInstance(this);

        tvMovie = findViewById(R.id.tvTicketMovie);
        tvTheater = findViewById(R.id.tvTicketTheater);
        tvTime = findViewById(R.id.tvTicketTime);
        tvSeat = findViewById(R.id.tvTicketSeat);
        tvPrice = findViewById(R.id.tvTicketPrice);
        tvUser = findViewById(R.id.tvTicketUser);
        tvBookingId = findViewById(R.id.tvBookingId);
        btnHome = findViewById(R.id.btnBackToHome);

        long ticketId = getIntent().getLongExtra("TICKET_ID", -1);
        if (ticketId != -1) {
            Ticket ticket = db.ticketDao().getTicketById(ticketId);
            if (ticket != null) {
                Showtime showtime = db.showtimeDao().getShowtimeById(ticket.showtimeId);
                Movie movie = db.movieDao().getMovieById(showtime.movieId);
                User user = db.userDao().getUserById(ticket.userId);
                
                Theater theater = null;
                for (Theater t : db.theaterDao().getAllTheaters()) {
                    if (t.id == showtime.theaterId) {
                        theater = t;
                        break;
                    }
                }

                tvMovie.setText(movie.title);
                if (theater != null) {
                    tvTheater.setText(theater.name + " (" + theater.location + ")");
                }
                tvTime.setText(showtime.startTime);
                tvSeat.setText(ticket.seatNumber);
                tvPrice.setText(String.format("%,.0f VND", showtime.price));
                
                if (user != null) {
                    tvUser.setText("Booked by: " + user.fullName);
                }
                tvBookingId.setText("Booking ID: #" + ticket.id + " | Date: " + ticket.bookingTime);
            }
        }

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
