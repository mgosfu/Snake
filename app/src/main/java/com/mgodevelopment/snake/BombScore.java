package com.mgodevelopment.snake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class BombScore extends AppCompatActivity {

    private Animation animation;

    private TextView scoreTextView;
    private TextView highScoreTextView;

    private ImageView playAgainImageView;
    private ImageView mainMenuImageView;

    private TextView gameOverTitleLeftTextView;
    private TextView gameOverTitleMiddleTextView;
    private TextView gameOverTitleRightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bomb_score);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        initPage();

    }

    private void initScore() {

        scoreTextView = (TextView) findViewById(R.id.player_score);
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_for_button_top_left);
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
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_for_button_top_right);
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
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_for_button_bottom_left);
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
                        Intent intentBomb = new Intent(BombScore.this, BombSnake.class);
                        intentBomb.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intentBomb);
                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        playAgainImageView.startAnimation(animation);

    }

    private void initMainMenu() {

        mainMenuImageView = (ImageView) findViewById(R.id.goto_main_menu);
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_for_button_bottom_right);
        animation.setDuration(GameSettings.ANIMATION_OPEN_BUTTON_DURATION);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                mainMenuImageView.setImageResource(R.mipmap.menu);
                mainMenuImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        scoreTextView.setBackgroundResource(R.mipmap.menu_options);
                        scoreTextView.setText("");
                        scoreTextView.setTextColor(Color.BLACK);
                        highScoreTextView.setBackgroundResource(R.mipmap.menu_options);
                        highScoreTextView.setText("");
                        highScoreTextView.setTextColor(Color.BLACK);
                        playAgainImageView.setBackgroundResource(R.mipmap.menu_options);
                        mainMenuImageView.setImageResource(R.mipmap.menu_options);

                        // Reverse animation
                        Animation animationScore = AnimationUtils.loadAnimation(BombScore.this, R.anim.reverse_for_button_top_left);
                        animationScore.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);
                        Animation animationHighScore = AnimationUtils.loadAnimation(BombScore.this, R.anim.reverse_for_button_top_right);
                        animationHighScore.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);
                        Animation animationPlayAgain = AnimationUtils.loadAnimation(BombScore.this, R.anim.reverse_for_button_bottom_left);
                        animationPlayAgain.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);
                        Animation animationMenu = AnimationUtils.loadAnimation(BombScore.this, R.anim.reverse_for_button_bottom_right);
                        animationMenu.setDuration(GameSettings.ANIMATION_CLOSE_BUTTON_DURATION);

                        Animation animationTitleLeft = AnimationUtils.loadAnimation(BombScore.this, R.anim.anim_for_title_left);
                        animationTitleLeft.setDuration(GameSettings.ANIMATION_SHOW_TITLE_DURATION);
                        Animation animationTitleMiddle = AnimationUtils.loadAnimation(BombScore.this, R.anim.anim_for_title_middle);
                        animationTitleMiddle.setDuration(GameSettings.ANIMATION_SHOW_TITLE_DURATION);
                        Animation animationTitleRight = AnimationUtils.loadAnimation(BombScore.this, R.anim.anim_for_title_right);
                        animationTitleRight.setDuration(GameSettings.ANIMATION_SHOW_TITLE_DURATION);

                        scoreTextView.startAnimation(animationScore);
                        highScoreTextView.startAnimation(animationHighScore);
                        playAgainImageView.startAnimation(animationPlayAgain);
                        mainMenuImageView.startAnimation(animationMenu);

                        gameOverTitleLeftTextView.startAnimation(animationTitleLeft);
                        gameOverTitleMiddleTextView.startAnimation(animationTitleMiddle);
                        gameOverTitleRightTextView.startAnimation(animationTitleRight);

                        Handler myHandler = new Handler();
                        myHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intentMain = new Intent(BombScore.this, MainMenu.class);
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
        mainMenuImageView.startAnimation(animation);

    }

    private void setScore() {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
        int playerScore = preferences.getInt("BombScore", 0);
        //scoreTextView.setText("Score: " + String.valueOf(playerScore));
        scoreTextView.setText(getString(R.string.current_score, playerScore, 0));
        scoreTextView.setTextColor(Color.WHITE);
        scoreTextView.setGravity(Gravity.CENTER);
        scoreTextView.setBackgroundResource(R.mipmap.menu_options);

    }

    private void setHighScore() {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int highScore = preferences.getInt("BombHighScore", 0);
        int lastScore = preferences.getInt("BombScore", 0);

        if (lastScore > highScore) {

            editor.putInt("BombHighScore", lastScore);
            editor.apply();
            highScore = lastScore;

        }

        //highScoreTextView.setText("High: " + String.valueOf(highScore));
        highScoreTextView.setText(getString(R.string.high_score, highScore, 0));
        highScoreTextView.setTextColor(Color.WHITE);
        highScoreTextView.setGravity(Gravity.CENTER);
        highScoreTextView.setBackgroundResource(R.mipmap.menu_options);

    }

    private void initTitle() {

        gameOverTitleLeftTextView = (TextView) findViewById(R.id.gameover_left);
        gameOverTitleMiddleTextView = (TextView) findViewById(R.id.gameover_middle);
        gameOverTitleRightTextView = (TextView) findViewById(R.id.gameover_right);

        Animation animationTitleLeft = AnimationUtils.loadAnimation(BombScore.this, R.anim.back_anim_for_title_left);
        animationTitleLeft.setDuration(GameSettings.ANIMATION_HIDE_TITLE_DURATION);
        animationTitleLeft.setAnimationListener(new Animation.AnimationListener() {
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
        gameOverTitleLeftTextView.startAnimation(animationTitleLeft);

        Animation animationTitleMiddle = AnimationUtils.loadAnimation(BombScore.this, R.anim.back_anim_for_title_middle);
        animationTitleMiddle.setDuration(GameSettings.ANIMATION_HIDE_TITLE_DURATION);
        animationTitleMiddle.setAnimationListener(new Animation.AnimationListener() {
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
        gameOverTitleMiddleTextView.startAnimation(animationTitleMiddle);

        Animation animationTitleRight = AnimationUtils.loadAnimation(BombScore.this, R.anim.back_anim_for_title_right);
        animationTitleRight.setDuration(GameSettings.ANIMATION_HIDE_TITLE_DURATION);
        animationTitleRight.setAnimationListener(new Animation.AnimationListener() {
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
        gameOverTitleRightTextView.startAnimation(animationTitleRight);

    }

    private void initPage() {

        initTitle();
        initScore();
        initHighScore();
        initPlayAgain();
        initMainMenu();
    }

    @Override
    public void onBackPressed() {

    }

}







































