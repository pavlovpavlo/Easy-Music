package com.pavlov.easymusic.model;

public class Song {

    private final long id;
    private final String title;
    private final String artist;
    private final String path;
    private final Long duration;
    private boolean isActive = false;

    public Song(long id, String title, String artist, String path, Long duration) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.path = path;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getPath() {
        return path;
    }

    public Long getDuration() {
        return duration;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
