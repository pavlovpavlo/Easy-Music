package com.pavlov.easymusic.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.pavlov.easymusic.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StorageUtil {
    public static List<Song> getSongList(Context context) {
        List<Song> songs = new ArrayList<>();

        ContentResolver musicResolver = context.getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor =
                musicResolver.query(musicUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int data = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int duration = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisData = musicCursor.getString(data);
                Long thisDuration = musicCursor.getLong(duration);
                songs.add(new Song(thisId, thisTitle, thisArtist, thisData, thisDuration));

            }
            while (musicCursor.moveToNext());

            Collections.sort(songs, (a, b) -> a.getTitle().compareTo(b.getTitle()));

        }

        return songs;
    }
}
