package com.mgodevelopment.snake;

import android.content.Intent;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainMenu extends AppCompatActivity {

    private RelativeLayout snakeLayout;
    private Animation compileAnim;
    private AdView adView;
    private ImageView btnClassic, btnNoWalls, btnBomb, btnSettings;
    private TextView titleLeft, titleMiddle, titleRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        snakeLayout = (RelativeLayout) findViewById(R.id.snake_layout);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(GameSettings.MY_AD_UNIT_ID);

        snakeLayout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

        adView.loadAd(adRequest);

        initClassic();
        initBomb();
        initNoWalls();
        initSettings();
        initTitle();

    }

    private void initClassic() {

        btnClassic = (ImageView) findViewById(R.id.classic);
        compileAnim = AnimationUtils.loadAnimation(MainMenu.this, R.anim.anim_for_button_top_left);
        compileAnim.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);

        compileAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btnClassic.setImageResource(R.mipmap.classic);

                btnClassic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intentClassic = new Intent(MainMenu.this, ClassicSnake.class);
                        intentClassic.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intentClassic);

                    }

                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        btnClassic.startAnimation(compileAnim);

    }

    private void initNoWalls() {

        btnNoWalls = (ImageView) findViewById(R.id.no_walls);
        compileAnim = AnimationUtils.loadAnimation(MainMenu.this, R.anim.anim_for_button_top_right);
        compileAnim.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);

        compileAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btnNoWalls.setImageResource(R.mipmap.no_walls);

                btnNoWalls.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intentNoWalls = new Intent(MainMenu.this, NoWallsSnake.class);
                        intentNoWalls.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intentNoWalls);

                    }

                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        btnNoWalls.startAnimation(compileAnim);

    }

    private void initBomb() {

        btnBomb = (ImageView) findViewById(R.id.bomb);
        compileAnim = AnimationUtils.loadAnimation(MainMenu.this, R.anim.anim_for_button_bottom_right);
        compileAnim.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);

        compileAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btnBomb.setImageResource(R.mipmap.bombsnake);

                btnBomb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intentBomb = new Intent(MainMenu.this, BombSnake.class);
                        intentBomb.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intentBomb);

                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        btnBomb.startAnimation(compileAnim);

    }

    private void initTitle() {

        titleLeft = (TextView) findViewById(R.id.snake_left);
        titleMiddle = (TextView) findViewById(R.id.snake_middle);
        titleRight = (TextView) findViewById(R.id.snake_right);

        // set up animation for title left
        compileAnim = AnimationUtils.loadAnimation(MainMenu.this, R.anim.back_anim_for_title_left);
        compileAnim.setDuration(GameSettings.ANIMATION_HIDE_TITLE_DURATION);

        compileAnim.setAnimationListener(new Animation.AnimationListener() {
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

        titleLeft.startAnimation(compileAnim);

        // set up animation for title middle
        compileAnim = AnimationUtils.loadAnimation(MainMenu.this, R.anim.back_anim_for_title_middle);
        compileAnim.setDuration(GameSettings.ANIMATION_HIDE_TITLE_DURATION);

        compileAnim.setAnimationListener(new Animation.AnimationListener() {
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

        titleMiddle.startAnimation(compileAnim);

        // set up animation for title right
        compileAnim = AnimationUtils.loadAnimation(MainMenu.this, R.anim.back_anim_for_title_right);
        compileAnim.setDuration(GameSettings.ANIMATION_HIDE_TITLE_DURATION);

        compileAnim.setAnimationListener(new Animation.AnimationListener() {
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

        titleRight.startAnimation(compileAnim);

    }

    private void initSettings() {

        btnSettings = (ImageView) findViewById(R.id.settings);
        compileAnim = AnimationUtils.loadAnimation(MainMenu.this, R.anim.anim_for_button_bottom_left);
        compileAnim.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);
        compileAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btnSettings.setImageResource(R.mipmap.settings);

                btnSettings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        btnSettings.setImageResource(R.mipmap.menu_options);
                        btnClassic.setImageResource(R.mipmap.menu_options);
                        btnBomb.setImageResource(R.mipmap.menu_options);
                        btnNoWalls.setImageResource(R.mipmap.menu_options);

                        Animation animClassic = AnimationUtils.loadAnimation(MainMenu.this, R.anim.reverse_for_button_top_left);
                        animClassic.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);

                        Animation animNoWalls = AnimationUtils.loadAnimation(MainMenu.this, R.anim.reverse_for_button_top_right);
                        animNoWalls.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);

                        Animation animBomb = AnimationUtils.loadAnimation(MainMenu.this, R.anim.reverse_for_button_bottom_right);
                        animBomb.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);

                        Animation animSettings = AnimationUtils.loadAnimation(MainMenu.this, R.anim.reverse_for_button_bottom_left);
                        animSettings.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);

                        Animation animTitleLeft = AnimationUtils.loadAnimation(MainMenu.this, R.anim.anim_for_title_left);
                        animTitleLeft.setDuration(GameSettings.ANIMATION_SHOW_TITLE_DURATION);

                        Animation animTitleMiddle = AnimationUtils.loadAnimation(MainMenu.this, R.anim.anim_for_title_middle);
                        animTitleMiddle.setDuration(GameSettings.ANIMATION_SHOW_TITLE_DURATION);

                        Animation animTitleRight = AnimationUtils.loadAnimation(MainMenu.this, R.anim.anim_for_title_right);
                        animTitleRight.setDuration(GameSettings.ANIMATION_SHOW_TITLE_DURATION);

                        btnClassic.startAnimation(animClassic);
                        btnNoWalls.startAnimation(animNoWalls);
                        btnBomb.startAnimation(animBomb);
                        btnSettings.startAnimation(animSettings);
                        titleLeft.startAnimation(animTitleLeft);
                        titleMiddle.startAnimation(animTitleMiddle);
                        titleRight.startAnimation(animTitleRight);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent settingsIntent = new Intent(MainMenu.this, Settings.class);
                                settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(settingsIntent);
                            }
                        }, GameSettings.START_NEW_ACTIVITY_DURATION);

                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        btnSettings.setAnimation(compileAnim);

    }

}




































