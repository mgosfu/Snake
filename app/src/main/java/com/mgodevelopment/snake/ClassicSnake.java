package com.mgodevelopment.snake;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class ClassicSnake extends AppCompatActivity {

    private boolean playMusic;
    private MediaPlayer musicPlayer;

    private RelativeLayout classicSnakeLayout;
    private boolean isInitialized;

    private GestureDetector mGestureDetector;
    private boolean isPaused;

    private boolean isGoingLeft = false;
    private boolean isGoingRight = false;
    private boolean isGoingUp = false;
    private boolean isGoingDown = false;

    private boolean clickLeft;
    private boolean clickRight;
    private boolean clickUp;
    private boolean clickDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_snake);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        musicOnOff();

        classicSnakeLayout = (RelativeLayout) findViewById(R.id.class_snake_layout);
        classicSnakeLayout.setBackgroundResource(R.mipmap.background_for_snake);
        classicSnakeLayout.setPaddingRelative(GameSettings.LAYOUT_PADDDING, GameSettings.LAYOUT_PADDDING, GameSettings.LAYOUT_PADDDING, GameSettings.LAYOUT_PADDDING);

        isInitialized = false;

    }

    private void musicOnOff() {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("SnakePreferences", Context.MODE_PRIVATE);
        playMusic = preferences.getBoolean("PlayMusic", true);
        musicPlayer = MediaPlayer.create(ClassicSnake.this, R.raw.music);
        if (playMusic) {

            musicPlayer.setLooping(true);
            musicPlayer.start();

        } else {
            musicPlayer.stop();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return mGestureDetector.onTouchEvent(event) || super.onTouchEvent(event);

    }

    @Override
    protected void onPause() {

        super.onPause();
        isPaused = true;
        musicPlayer.release();

    }

    private void onSwipeRight() {
        if (!isGoingRight && !isGoingLeft) {

            isGoingRight = true;
            isGoingLeft = false;
            isGoingUp = false;
            isGoingDown = false;

        }
    }

    private void onSwipeLeft() {
        if (!isGoingLeft && !isGoingRight) {

            isGoingRight = false;
            isGoingLeft = true;
            isGoingUp = false;
            isGoingDown = false;

        }
    }

    private void onSwipeUp() {
        if (!isGoingDown && !isGoingUp) {

            isGoingRight = false;
            isGoingLeft = false;
            isGoingUp = true;
            isGoingDown = false;

        }
    }

    private void onSwipeDown() {
        if (!isGoingDown && !isGoingUp) {

            isGoingRight = false;
            isGoingLeft = false;
            isGoingUp = false;
            isGoingDown = true;

        }
    }

    private void clickLeft() {
        if (!clickLeft && !clickRight) {

            clickLeft = true;
            clickRight = false;
            clickUp = false;
            clickDown = false;

        }
    }

    private void clickRight() {
        if (!clickRight && !clickLeft) {

            clickLeft = false;
            clickRight = true;
            clickUp = false;
            clickDown = false;

        }
    }

    private void ClickUp() {
        if (!clickUp && !clickDown) {

            clickLeft = false;
            clickRight = false;
            clickUp = true;
            clickDown = false;

        }
    }

    private void clickDown() {
        if (!clickDown && !clickUp) {

            clickLeft = false;
            clickRight = false;
            clickUp = false;
            clickDown = true;

        }
    }

}
