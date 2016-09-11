package com.mgodevelopment.snake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class ClassicScore extends AppCompatActivity {

    private TextView scoreTextView;
    private Animation animation;
    private TextView highScoreTextView;
    private SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
    private ImageView playAgainImageView;
    private ImageView mainMenuImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_score);

    }

    private void initPage() {
        initScore();
        initHighScore();
        initPlayAgain();
    }

    private void initScore() {

        scoreTextView = (TextView) findViewById(R.id.player_score);
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_for_classic_button);
        animation.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setScore();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        scoreTextView.startAnimation(animation);

    }

    private void initHighScore() {

        highScoreTextView = (TextView) findViewById(R.id.mode_high_score);
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_for_no_walls_button);
        animation.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setHighScore();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        highScoreTextView.startAnimation(animation);

    }

    private void initPlayAgain() {

        playAgainImageView = (ImageView) findViewById(R.id.play_again);
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_for_settings_button);
        animation.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                playAgainImageView.setImageResource(R.mipmap.again);
                playAgainImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentClassic = new Intent(ClassicScore.this, ClassicSnake.class);
                        intentClassic.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intentClassic);
                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        playAgainImageView.startAnimation(animation);

    }

    private void setScore() {

        //SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
        int playerScore = preferences.getInt("Score", 0);
        scoreTextView.setText("Score: " + String.valueOf(playerScore));
        scoreTextView.setTextColor(Color.WHITE);
        scoreTextView.setGravity(Gravity.CENTER);
        scoreTextView.setBackgroundResource(R.mipmap.menu_options);

    }

    private void setHighScore() {

        //SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int highScore = preferences.getInt("HighScoreClassic", 0);
        int lastScore = preferences.getInt("Score", 0);

        if (lastScore > highScore) {

            editor.putInt("HighScoreClassic", lastScore);
            editor.apply();
            highScore = lastScore;

        }

        highScoreTextView.setText(String.valueOf(highScore));
        highScoreTextView.setTextColor(Color.WHITE);
        highScoreTextView.setGravity(Gravity.CENTER);
        highScoreTextView.setBackgroundResource(R.mipmap.menu_options);

    }

}







































