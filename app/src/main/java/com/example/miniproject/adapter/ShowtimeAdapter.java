package com.example.miniproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.miniproject02.R;
import com.example.miniproject02.database.AppDatabase;
import com.example.miniproject02.model.Showtime;
import com.example.miniproject02.model.Theater;
import java.util.List;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {
    private List<Showtime> showtimes;
    private AppDatabase db;
    private OnShowtimeClickListener listener;

    public interface OnShowtimeClickListener {
        void onShowtimeClick(Showtime showtime);
    }

    public ShowtimeAdapter(List<Showtime> showtimes, AppDatabase db, OnShowtimeClickListener listener) {
        this.showtimes = showtimes;
        this.db = db;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent, false);
        return new ShowtimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeViewHolder holder, int position) {
        Showtime showtime = showtimes.get(position);
        
        // This is not ideal to query DB in onBind, but following "allowMainThreadQueries" simplicity
        Theater theater = null;
        for (Theater t : db.theaterDao().getAllTheaters()) {
            if (t.id == showtime.theaterId) {
                theater = t;
                break;
            }
        }

        if (theater != null) {
            holder.tvTheater.setText(theater.name + " (" + theater.location + ")");
        }
        holder.tvTime.setText(showtime.startTime);
        holder.tvPrice.setText(String.format("%,.0f VND", showtime.price));
        holder.itemView.setOnClickListener(v -> listener.onShowtimeClick(showtime));
    }

    @Override
    public int getItemCount() {
        return showtimes.size();
    }

    static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTheater, tvTime, tvPrice;

        public ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTheater = itemView.findViewById(R.id.tvShowtimeTheater);
            tvTime = itemView.findViewById(R.id.tvShowtimeTime);
            tvPrice = itemView.findViewById(R.id.tvShowtimePrice);
        }
    }
}
