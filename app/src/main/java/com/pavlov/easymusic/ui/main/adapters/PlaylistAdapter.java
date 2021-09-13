package com.pavlov.easymusic.ui.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private OnSongClickListener listener;

    public interface OnSongClickListener{
        void onSongClick(int position);
    }

    public void setListener(OnSongClickListener listener) {
        this.listener = listener;
    }

    public PlaylistAdapter(Context ctx) {
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
        Song song = arrayList.get(position);
        holder.title.setText(song.getTitle());
        holder.artist.setText(song.getArtist());

        long milliseconds = song.getDuration();
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;

        holder.duration.setText((minutes > 9 ? minutes : "0" + minutes)
                + ":"
                + (seconds > 9 ? seconds : "0" + seconds));

        holder.itemView.setOnClickListener(view -> listener.onSongClick(position));

        if(song.isActive()){
            holder.itemView.setBackgroundResource(R.drawable.music_item_bg_active);
            holder.imageContainer.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.gray_light));
            holder.title.setTextColor(holder.itemView.getResources().getColor(R.color.white));
            holder.duration.setTextColor(holder.itemView.getResources().getColor(R.color.gray_light));
            holder.artist.setTextColor(holder.itemView.getResources().getColor(R.color.gray_light));
        }else{
            holder.itemView.setBackgroundResource(R.drawable.music_item_bg);
            holder.imageContainer.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.gray_ee));
            holder.title.setTextColor(holder.itemView.getResources().getColor(R.color.black));
            holder.duration.setTextColor(holder.itemView.getResources().getColor(R.color.gray_main));
            holder.artist.setTextColor(holder.itemView.getResources().getColor(R.color.gray_main));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {

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
