package com.pavlov.easymusic.ui.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlov.easymusic.R;
import com.pavlov.easymusic.model.Song;

import java.util.ArrayList;
import java.util.List;

public class PlayerDetailImageAdapter extends RecyclerView.Adapter<PlayerDetailImageAdapter.MyViewHolder> {

    private Context context;
    private List<Song> arrayList = new ArrayList<>();

    public PlayerDetailImageAdapter(Context context, List<Song> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider_player, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getTitle());
        holder.artist.setText(arrayList.get(position).getArtist());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView artist;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.music_name);
            artist = itemView.findViewById(R.id.music_album);
        }
    }
};