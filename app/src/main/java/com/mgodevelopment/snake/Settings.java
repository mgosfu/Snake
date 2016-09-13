package com.mgodevelopment.snake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private TextView titleLeft, titleMiddle, titleRight;
    private ImageView btnSwipe, btnMusic, btnHome;

    private Animation compileAnimation;
    private boolean isMusicOn, isSwipe;

    private RelativeLayout settingsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        settingsLayout = (RelativeLayout) findViewById(R.id.settings_layout);
        initSwipeButton();
        initMusicSwitch();
        initHomeButton();
        title();

    }

    private void initSwipeButton() {

        btnSwipe = (ImageView) findViewById(R.id.swipe);
        compileAnimation = AnimationUtils.loadAnimation(Settings.this, R.anim.anim_for_button_top_left);
        compileAnimation.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);
        compileAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                final SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
                isSwipe = preferences.getBoolean(GameSettings.CONTROLS, true);
                btnSwipe.setImageResource(isSwipe ? R.mipmap.swipe : R.mipmap.buttons);
//                if (isSwipeOn) {
//                    btnSwipe.setImageResource(R.mipmap.swipe);
//                } else {
//                    btnSwipe.setImageResource(R.mipmap.buttons);
//                }

                btnSwipe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnSwipe.setImageDrawable(null);
                        if (isSwipe) {
                            isSwipe = false;
                            btnSwipe.setImageResource(R.mipmap.buttons);
                        } else {
                            isSwipe = true;
                            btnSwipe.setImageResource(R.mipmap.swipe);
                        }
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(GameSettings.CONTROLS, isSwipe);
                        editor.apply();
                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        btnSwipe.startAnimation(compileAnimation);

    }

    private void initMusicSwitch() {

        btnMusic = (ImageView) findViewById(R.id.music);
        compileAnimation = AnimationUtils.loadAnimation(Settings.this, R.anim.anim_for_button_top_right);
        compileAnimation.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);
        compileAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
                isMusicOn = preferences.getBoolean(GameSettings.PLAY_MUSIC, true);
                btnMusic.setImageResource(isMusicOn ? R.mipmap.music_on : R.mipmap.music_off);
//                if (isMusicOn) {
//                    btnMusic.setImageResource(R.mipmap.music_on);
//                } else {
//                    btnMusic.setImageResource(R.mipmap.music_off);
//                }

                btnMusic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnMusic.setImageDrawable(null);
                        if (isMusicOn) {
                            isMusicOn = false;
                            btnMusic.setImageResource(R.mipmap.music_off);
                        } else {
                            isMusicOn = true;
                            btnMusic.setImageResource(R.mipmap.music_on);
                        }
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(GameSettings.PLAY_MUSIC, isMusicOn);
                        editor.apply();
                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        btnMusic.startAnimation(compileAnimation);

    }

    private void initHomeButton() {

        btnHome = (ImageView) findViewById(R.id.home);
        compileAnimation = AnimationUtils.loadAnimation(Settings.this, R.anim.anim_for_home_button);
        compileAnimation.setDuration(GameSettings.ANIMATION_SHOW_HOME_BUTTON_DURATION);
        compileAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btnHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        Animation animationHide = AnimationUtils.loadAnimation(Settings.this, R.anim.reverse_for_home_button);
                        animationHide.setDuration(GameSettings.ANIMATION_HIDE_HOME_BUTTON_DURATION);
                        btnSwipe.setImageResource(R.mipmap.menu_options);
                        btnMusic.setImageResource(R.mipmap.menu_options);

                        Animation animationSwipe = AnimationUtils.loadAnimation(Settings.this, R.anim.reverse_for_button_top_left);
                        animationSwipe.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);

                        Animation animationMusic = AnimationUtils.loadAnimation(Settings.this, R.anim.reverse_for_button_top_right);
                        animationMusic.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);

                        Animation animationTitleLeft = AnimationUtils.loadAnimation(Settings.this, R.anim.anim_for_title_left);
                        animationTitleLeft.setDuration(GameSettings.ANIMATION_SHOW_HOME_BUTTON_DURATION);
                        Animation animationTitleMiddle = AnimationUtils.loadAnimation(Settings.this, R.anim.anim_for_title_middle);
                        animationTitleMiddle.setDuration(GameSettings.ANIMATION_SHOW_HOME_BUTTON_DURATION);
                        Animation animationTitleRight = AnimationUtils.loadAnimation(Settings.this, R.anim.anim_for_title_right);
                        animationTitleRight.setDuration(GameSettings.ANIMATION_SHOW_HOME_BUTTON_DURATION);

                        btnHome.startAnimation(animationHide);
                        btnSwipe.startAnimation(animationSwipe);
                        btnMusic.startAnimation(animationMusic);
                        titleLeft.startAnimation(animationTitleLeft);
                        titleMiddle.startAnimation(animationTitleMiddle);
                        titleRight.startAnimation(animationTitleRight);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intentMain = new Intent(Settings.this, MainMenu.class);
                                intentMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intentMain);
                            }
                        }, GameSettings.START_NEW_ACTIVITY_DURATION);

                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        btnHome.startAnimation(compileAnimation);

    }

    private void title() {

        titleLeft = (TextView) findViewById(R.id.snake_left);
        titleMiddle = (TextView) findViewById(R.id.snake_middle);
        titleRight = (TextView) findViewById(R.id.snake_right);

        compileAnimation = AnimationUtils.loadAnimation(Settings.this, R.anim.back_anim_for_title_left);
        compileAnimation.setDuration(GameSettings.ANIMATION_HIDE_TITLE_DURATION);
        compileAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        titleLeft.startAnimation(compileAnimation);

        compileAnimation = AnimationUtils.loadAnimation(Settings.this, R.anim.back_anim_for_title_middle);
        compileAnimation.setDuration(GameSettings.ANIMATION_HIDE_TITLE_DURATION);
        compileAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        titleMiddle.startAnimation(compileAnimation);

        compileAnimation = AnimationUtils.loadAnimation(Settings.this, R.anim.back_anim_for_title_right);
        compileAnimation.setDuration(GameSettings.ANIMATION_HIDE_TITLE_DURATION);
        compileAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        titleRight.startAnimation(compileAnimation);

    }

    @Override
    public void onBackPressed() {

    }
}



























