package com.pavlov.easymusic.ui.main;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import com.pavlov.easymusic.R;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String[] permissions =
            new String[]{Manifest.permission.WAKE_LOCK, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final ActivityResultLauncher requestPermissions =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                    result -> updatePermissionsState());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void updatePermissionsState() {

    }
}