package com.mgodevelopment.snake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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

    private boolean useButtons;

    private ImageView btnRight, btnLeft, btnDown, btnUp;

    private int playerScore = 0;

    private boolean gameOver;

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

        classicSnakeLayout = (RelativeLayout) findViewById(R.id.classic_snake_layout);
        classicSnakeLayout.setBackgroundResource(R.mipmap.background_for_snake);
        classicSnakeLayout.setPaddingRelative(GameSettings.LAYOUT_PADDDING, GameSettings.LAYOUT_PADDDING, GameSettings.LAYOUT_PADDDING, GameSettings.LAYOUT_PADDDING);

        isInitialized = false;

    }

    private void musicOnOff() {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
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

    private void clickButtonLeft() {
        if (!clickLeft && !clickRight) {

            clickLeft = true;
            clickRight = false;
            clickUp = false;
            clickDown = false;

        }
    }

    private void clickButtonRight() {
        if (!clickRight && !clickLeft) {

            clickLeft = false;
            clickRight = true;
            clickUp = false;
            clickDown = false;

        }
    }

    private void clickButtonUp() {
        if (!clickUp && !clickDown) {

            clickLeft = false;
            clickRight = false;
            clickUp = true;
            clickDown = false;

        }
    }

    private void clickButtonDown() {
        if (!clickDown && !clickUp) {

            clickLeft = false;
            clickRight = false;
            clickUp = false;
            clickDown = true;

        }
    }

    private void buttonsDirectionInit() {

        btnRight = (ImageView) findViewById(R.id.btn_right);
        btnLeft = (ImageView) findViewById(R.id.btn_left);
        btnUp = (ImageView) findViewById(R.id.btn_up);
        btnDown = (ImageView) findViewById(R.id.btn_down);

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButtonRight();
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButtonLeft();
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButtonUp();
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButtonDown();
            }
        });

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
        useButtons = preferences.getBoolean("UseButtonControls", true);

        if (useButtons) {

            btnRight.setVisibility(View.VISIBLE);
            btnLeft.setVisibility(View.VISIBLE);
            btnUp.setVisibility(View.VISIBLE);
            btnDown.setVisibility(View.VISIBLE);

        } else {

            btnRight.setVisibility(View.INVISIBLE);
            btnLeft.setVisibility(View.INVISIBLE);
            btnUp.setVisibility(View.INVISIBLE);
            btnDown.setVisibility(View.INVISIBLE);

        }

    }

    private void shake() {

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        shake.setDuration(GameSettings.SHAKE_DURATION);
        classicSnakeLayout = (RelativeLayout) findViewById(R.id.classic_snake_layout);
        classicSnakeLayout.setBackgroundResource(R.mipmap.background_for_snake);
        classicSnakeLayout.startAnimation(shake);

    }

    private void fadeAnim() {

        if (playerScore % GameSettings.POINTS_ANIMATION == 0) {

            Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            classicSnakeLayout = (RelativeLayout) findViewById(R.id.classic_snake_layout);
            classicSnakeLayout.setBackgroundResource(R.mipmap.background_for_snake_change);
            classicSnakeLayout.startAnimation(fadeIn);
            fadeIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    Animation fadeOut = AnimationUtils.loadAnimation(ClassicSnake.this, R.anim.fade_out);
                    classicSnakeLayout = (RelativeLayout) findViewById(R.id.classic_snake_layout);
                    classicSnakeLayout.setBackgroundResource(R.mipmap.background_for_snake);
                    classicSnakeLayout.startAnimation(fadeOut);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }

    }

    private void collide() {

        gameOver = true;
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Score", playerScore);
        editor.commit();
        Intent intentScore = new Intent(ClassicSnake.this, ClassicScore.class);
        intentScore.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intentScore);

    }

}