package com.pavlov.easymusic.ui.splash;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.pavlov.easymusic.R;
import com.pavlov.easymusic.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView cardPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initViews();
    }

    private void initViews() {
        cardPlay = findViewById(R.id.card_play);

        startAnimation();
    }

    private void startAnimation() {
        cardPlay.animate().withLayer()
                .rotationX(720)
                .setDuration(2500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).start();
    }

}