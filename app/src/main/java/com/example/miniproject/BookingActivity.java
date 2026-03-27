package com.example.miniproject02;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.miniproject02.database.AppDatabase;
import com.example.miniproject02.model.Showtime;
import com.example.miniproject02.model.Ticket;
import com.example.miniproject02.utils.SessionManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {
    private TextView tvShowtimeInfo, tvSelectedSeat;
    private GridLayout glSeats;
    private Button btnConfirm;
    private AppDatabase db;
    private SessionManager sessionManager;
    private int showtimeId;
    private String selectedSeat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        db = AppDatabase.getInstance(this);
        sessionManager = new SessionManager(this);

        tvShowtimeInfo = findViewById(R.id.tvBookingShowtime);
        tvSelectedSeat = findViewById(R.id.tvSelectedSeat);
        glSeats = findViewById(R.id.glSeats);
        btnConfirm = findViewById(R.id.btnConfirmBooking);

        showtimeId = getIntent().getIntExtra("SHOWTIME_ID", -1);
        Showtime showtime = db.showtimeDao().getShowtimeById(showtimeId);

        if (showtime != null) {
            tvShowtimeInfo.setText("Showtime ID: " + showtime.id + " at " + showtime.startTime);
            generateSeats(showtimeId);
        }

        btnConfirm.setOnClickListener(v -> {
            if (!selectedSeat.isEmpty()) {
                String bookingTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                Ticket ticket = new Ticket(sessionManager.getUserId(), showtimeId, selectedSeat, bookingTime);
                long ticketId = db.ticketDao().insert(ticket);
                
                Intent intent = new Intent(this, TicketResultActivity.class);
                intent.putExtra("TICKET_ID", ticketId);
                startActivity(intent);
                finish();
            }
        });
    }

    private void generateSeats(int showtimeId) {
        List<String> bookedSeats = db.ticketDao().getBookedSeats(showtimeId);
        String[] rows = {"A", "B", "C", "D"};
        
        for (String row : rows) {
            for (int i = 1; i <= 5; i++) {
                String seatName = row + i;
                Button seatBtn = new Button(this);
                seatBtn.setText(seatName);
                
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 150;
                params.height = 150;
                params.setMargins(8, 8, 8, 8);
                seatBtn.setLayoutParams(params);

                if (bookedSeats.contains(seatName)) {
                    seatBtn.setEnabled(false);
                    seatBtn.setBackgroundColor(Color.GRAY);
                } else {
                    seatBtn.setBackgroundColor(Color.LTGRAY);
                    seatBtn.setOnClickListener(v -> {
                        selectedSeat = seatName;
                        tvSelectedSeat.setText("Selected Seat: " + selectedSeat);
                        btnConfirm.setEnabled(true);
                        
                        for (int j = 0; j < glSeats.getChildCount(); j++) {
                            Button b = (Button) glSeats.getChildAt(j);
                            if (b.isEnabled()) {
                                b.setBackgroundColor(Color.LTGRAY);
                            }
                        }
                        seatBtn.setBackgroundColor(Color.GREEN);
                    });
                }
                glSeats.addView(seatBtn);
            }
        }
    }
}
