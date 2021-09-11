package com.pavlov.easymusic.ui.main;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.pavlov.easymusic.R;
import com.pavlov.easymusic.model.Song;
import com.pavlov.easymusic.ui.main.adapters.PlaylistAdapter;
import com.pavlov.easymusic.util.StorageUtil;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private final int REQUEST_CODE = 1001;
    private List<Song> songs;
    private RecyclerView recyclerView;
    private PlaylistAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

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
        recyclerView = findViewById(R.id.songs_list);
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
        songs = StorageUtil.getSongList(this);
        adapter.setArrayList(songs);
    }

}