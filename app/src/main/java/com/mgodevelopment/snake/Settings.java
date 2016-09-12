package com.mgodevelopment.snake;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    private boolean isMusicOn, isSwipeOn;

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
                isSwipeOn = preferences.getBoolean("Controls", true);
                if (isSwipeOn) {
                    btnSwipe.setImageResource(R.mipmap.swipe);
                } else {
                    btnSwipe.setImageResource(R.mipmap.buttons);
                }

                btnSwipe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isSwipeOn) {
                            isSwipeOn = false;
                            btnSwipe.setImageResource(R.mipmap.buttons);
                        } else {
                            isSwipeOn = true;
                            btnSwipe.setImageResource(R.mipmap.swipe);
                        }
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("Controls", isSwipeOn);
                        editor.apply();
                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void initMusicSwitch() {

    }

    private void initHomeButton() {

    }

    private void title() {

    }

}



























