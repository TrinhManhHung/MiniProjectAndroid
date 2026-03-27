package com.example.miniproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.miniproject02.R;
import com.example.miniproject02.model.Theater;
import java.util.List;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder> {
    private List<Theater> theaters;
    private OnTheaterClickListener listener;

    public interface OnTheaterClickListener {
        void onTheaterClick(Theater theater);
    }

    public TheaterAdapter(List<Theater> theaters, OnTheaterClickListener listener) {
        this.theaters = theaters;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theater, parent, false);
        return new TheaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterViewHolder holder, int position) {
        Theater theater = theaters.get(position);
        holder.tvName.setText(theater.name);
        holder.tvLocation.setText(theater.location);
        holder.itemView.setOnClickListener(v -> listener.onTheaterClick(theater));
    }

    @Override
    public int getItemCount() {
        return theaters.size();
    }

    static class TheaterViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvLocation;

        public TheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTheaterName);
            tvLocation = itemView.findViewById(R.id.tvTheaterLocation);
        }
    }
}
