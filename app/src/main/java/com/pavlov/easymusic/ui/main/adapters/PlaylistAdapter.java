package com.pavlov.easymusic.ui.main.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlov.easymusic.R;
import com.pavlov.easymusic.model.Song;

import java.util.ArrayList;
import java.util.List;


public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    private List<Song> arrayList;
    private Context ctx;

    public PlaylistAdapter(Context ctx){
        this.ctx = ctx;
        arrayList = new ArrayList<>();
    }

    public void setArrayList(List<Song> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);

        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Song song =arrayList.get(position);
        holder.title.setText(song.getTitle());
        holder.artist.setText(song.getArtist());
        holder.duration.setText(song.getDuration().toString());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class PlaylistViewHolder extends  RecyclerView.ViewHolder{

        public CardView imageContainer;
        public TextView title;
        public TextView artist;
        public TextView duration;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.music_name);
            artist = itemView.findViewById(R.id.music_album);
            duration = itemView.findViewById(R.id.music_duration);
            imageContainer = itemView.findViewById(R.id.image_container);
        }
    }
}
