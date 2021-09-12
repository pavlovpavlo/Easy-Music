package com.pavlov.easymusic.ui.main;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.pavlov.easymusic.R;
import com.pavlov.easymusic.model.Song;
import com.pavlov.easymusic.service.MediaPlayerService;
import com.pavlov.easymusic.ui.main.adapters.PlayerDetailImageAdapter;
import com.pavlov.easymusic.ui.main.adapters.PlaylistAdapter;
import com.pavlov.easymusic.util.GetSongUtil;
import com.pavlov.easymusic.util.StorageUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    private final int REQUEST_CODE = 1001;
    public static final String Broadcast_PLAY_NEW_AUDIO = "easy_player.PlayNewAudio";
    private List<Song> songs;
    private RecyclerView recyclerView;
    private ViewPager2 imagePlayerSlider;
    private PlaylistAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private View peekControl;
    private View mainPlayer;
    private CardView linearLayoutBSheet;
    private BottomSheetBehavior bottomSheetBehavior;

    private float containerHeight;
    private MediaPlayerService player;
    boolean serviceBound = false;
    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private boolean checkIfAlreadyHavePermission() {
        int resultWakeLock = ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK);
        int resultReadExtStor= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return resultWakeLock == PackageManager.PERMISSION_GRANTED &&
                resultReadExtStor == PackageManager.PERMISSION_GRANTED;
    }

    private void sendPermission(){
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WAKE_LOCK,Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE);
    }

    private void initViews(){
        peekControl = findViewById(R.id.peek_control);
        mainPlayer = findViewById(R.id.main_player);
        linearLayoutBSheet = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutBSheet);
        recyclerView = findViewById(R.id.songs_list);
        imagePlayerSlider = findViewById(R.id.slider_view);

        initBottomSheet();
        initAnimation();
        initPlaylist();
    }

    private void initBottomSheet(){
        ViewTreeObserver viewTreeObserver = recyclerView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    containerHeight = recyclerView.getHeight();
                    if (containerHeight != 0) {
                        recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                }
            });
        }
    }

    private void initPlaylist(){
        adapter = new PlaylistAdapter(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        if(checkIfAlreadyHavePermission()){
            getSongs();
        }else{
            sendPermission();
        }
    }

    private void initSlider(){
        PlayerDetailImageAdapter adapter = new PlayerDetailImageAdapter(this, songs);

        imagePlayerSlider.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getSongs();
            }
        }
    }
    private void getSongs() {
        songs = GetSongUtil.getSongList(this);
        adapter.setArrayList(songs);
        initSlider();
        playAudio(0);
    }

    private void initAnimation(){
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View view, int i) {
            }

            @Override
            public void onSlide(View view, float v) {
                recyclerView.setTranslationY(-v * (containerHeight + 50) / (float) 2);
                recyclerView.setScaleX(1-v/2);
                recyclerView.setScaleY(1-v/2);
                peekControl.setAlpha(v == 0 ? 1 : (1 - v * 2));
                mainPlayer.setAlpha(v == 0 ? 0 : (0 + v));
                imagePlayerSlider.setAlpha(v == 0 ? 0 : (0 + v));
                if (v == 1) {
                    linearLayoutBSheet.setElevation(0);
                } else {
                    linearLayoutBSheet.setElevation(35);
                }

            }
        });
    }

    private void playAudio(int audioIndex) {
        //Check is service is active
        StorageUtil storage = new StorageUtil(getApplicationContext());
        if (!serviceBound) {
            //Store Serializable audioList to SharedPreferences
            storage.storeSong(songs);
            storage.storeSongIndex(audioIndex);

            Intent playerIntent = new Intent(this, MediaPlayerService.class);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Store the new audioIndex to SharedPreferences
            storage.storeSongIndex(audioIndex);

            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(Broadcast_PLAY_NEW_AUDIO);
            sendBroadcast(broadcastIntent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            unbindService(serviceConnection);
            //service is active
            player.stopSelf();
        }
    }

}