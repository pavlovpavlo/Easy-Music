package com.pavlov.easymusic.service;

public interface OnAudioStateChange {
    void onTrackPause();

    void onTrackStart();

    void onTrackChange();
}
