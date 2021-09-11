package com.pavlov.easymusic.ui.main.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlov.easymusic.R;
import com.pavlov.easymusic.model.Song;

import java.util.ArrayList;


public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    private ArrayList<Song> arrayList;
    private Context ctx;

    public PlaylistAdapter(ArrayList<Song> arrayList, Context ctx){
        this.arrayList = arrayList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist,parent, false);

        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Song song =arrayList.get(position);
        holder.title.setText(song.getTitle());
        holder.artist.setText(song.getArtist());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class PlaylistViewHolder extends  RecyclerView.ViewHolder{

        public ImageView image;
        public TextView title;
        public TextView artist;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            //title = itemView.findViewById(R.id.title);
            //artist = itemView.findViewById(R.id.artist);
        }
    }
}
