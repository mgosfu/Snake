package com.mgodevelopment.snake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class BombSnake extends AppCompatActivity {

    private boolean playMusic;
    private MediaPlayer musicPlayer;

    private RelativeLayout bombSnakeLayout;
    private boolean isInitialized;

    private GestureDetector gestureDetector;
    private boolean isPaused;

    private boolean isGoingLeft = false;
    private boolean isGoingRight = false;
    private boolean isGoingUp = false;
    private boolean isGoingDown = false;

    private boolean clickLeft;
    private boolean clickRight;
    private boolean clickUp;
    private boolean clickDown;

    private boolean isSwipe;

    private ImageView btnRight, btnLeft, btnDown, btnUp;

    private int playerScore = 0;

    private boolean isGameOver;

    private ArrayList<ImageView> parts;
    private int screenHeight, screenWidth;

    private ArrayList<ImageView> points;
    private boolean isCollide = false;

    private Handler myHandler;

    private ImageView head;

    private TextView textScore;

    private int speedX = 20;
    private int speedY = 20;

    private ImageView poison;
    private ArrayList<ImageView> bombs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bomb_snake);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        musicOnOff();

        bombSnakeLayout = (RelativeLayout) findViewById(R.id.bomb_snake_layout);
        bombSnakeLayout.setBackgroundResource(R.mipmap.background_for_snake);
        bombSnakeLayout.setPaddingRelative(GameSettings.LAYOUT_PADDDING, GameSettings.LAYOUT_PADDDING, GameSettings.LAYOUT_PADDDING, GameSettings.LAYOUT_PADDDING);
        textScore = (TextView) findViewById(R.id.score);

        isInitialized = false;

    }

    private void musicOnOff() {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
        playMusic = preferences.getBoolean(GameSettings.PLAY_MUSIC, true);
        musicPlayer = MediaPlayer.create(BombSnake.this, R.raw.music);

        if (playMusic) {

            musicPlayer.start();

            musicPlayer.setOnErrorListener(new android.media.MediaPlayer.OnErrorListener() {

                public boolean onError(MediaPlayer mediaplayer, int i, int j) {
                    return false;
                }
            });
            musicPlayer.setLooping(true);

        } else {
            musicPlayer.stop();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);

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
        isSwipe = preferences.getBoolean(GameSettings.SELECT_CONTROLS, true);

        if (isSwipe) {

            btnRight.setVisibility(View.INVISIBLE);
            btnLeft.setVisibility(View.INVISIBLE);
            btnUp.setVisibility(View.INVISIBLE);
            btnDown.setVisibility(View.INVISIBLE);

        } else {

            btnRight.setVisibility(View.VISIBLE);
            btnLeft.setVisibility(View.VISIBLE);
            btnUp.setVisibility(View.VISIBLE);
            btnDown.setVisibility(View.VISIBLE);

        }

    }

    private void shake() {

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        shake.setDuration(GameSettings.SHAKE_DURATION);
        bombSnakeLayout = (RelativeLayout) findViewById(R.id.bomb_snake_layout);
        bombSnakeLayout.setBackgroundResource(R.mipmap.background_for_snake);
        bombSnakeLayout.startAnimation(shake);

    }

    private void fadeAnim() {

        if (playerScore % GameSettings.POINTS_ANIMATION == 0) {

            Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            bombSnakeLayout = (RelativeLayout) findViewById(R.id.bomb_snake_layout);
            bombSnakeLayout.setBackgroundResource(R.mipmap.background_for_snake_change);
            bombSnakeLayout.startAnimation(fadeIn);
            fadeIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    Animation fadeOut = AnimationUtils.loadAnimation(BombSnake.this, R.anim.fade_out);
                    bombSnakeLayout = (RelativeLayout) findViewById(R.id.bomb_snake_layout);
                    bombSnakeLayout.setBackgroundResource(R.mipmap.background_for_snake);
                    bombSnakeLayout.startAnimation(fadeOut);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }

    }

    private void checkBitten() {

        ImageView snakeHead = parts.get(0);
        ImageView snakeTile = new ImageView(this);

        for (int i = 1; i < parts.size(); i++) {
            snakeTile = parts.get(i);
            if (snakeHead.getX() == snakeTile.getX() && snakeHead.getY() == snakeTile.getY()) {
                gameOver();
                break;
            }
        }

    }

    private void gameOver() {

        isGameOver = true;
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(GameSettings.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(GameSettings.HIGH_SCORE, playerScore);
        //editor.commit();
        editor.apply();
        Intent intentScore = new Intent(BombSnake.this, BombScore.class);
        intentScore.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intentScore);

    }

    private void addTail() {

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.head);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(((screenWidth * 20) / 450), ((screenHeight * 30) / 450));
        imageView.setLayoutParams(layoutParams);
        bombSnakeLayout.addView(imageView);
        parts.add(imageView);

    }

    private void setNewPoint() {

        Random random = new Random();
        ImageView newPoint = new ImageView(BombSnake.this);
        float x = random.nextFloat() * (screenWidth - newPoint.getWidth());
        float y = random.nextFloat() * (screenHeight - newPoint.getHeight());
        newPoint.setImageResource(R.mipmap.food);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(((screenWidth * 20) / 450), ((screenHeight * 30) / 450));
        newPoint.setLayoutParams(layoutParams);
        newPoint.setX(x);
        newPoint.setY(y);
        isCollide = false;
        bombSnakeLayout.addView(newPoint);
        points.add(points.size(), newPoint);

    }

    private void setFoodPoints() {

        for (int i = 0; i < GameSettings.FOOD_POINTS; i++) {

            Random random = new Random();
            ImageView foodItem = new ImageView(this);
            float x = random.nextFloat() * (screenWidth - foodItem.getWidth());
            float y = random.nextFloat() * (screenHeight - foodItem.getHeight());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(((screenWidth * 20) / 450), ((screenHeight * 30) / 450));
            foodItem.setImageResource(R.mipmap.food);
            foodItem.setLayoutParams(layoutParams);
            foodItem.setX(x);
            foodItem.setY(y);
            bombSnakeLayout.addView(foodItem);
            points.add(i, foodItem);

        }

    }

    private void setBombs() {

        for (int i = 0; i < GameSettings.NUMBER_BOMBS; i++) {

            Random rand = new Random();
            ImageView bomb = new ImageView(this);
            float x = rand.nextFloat() * (screenWidth - bomb.getWidth());
            float y = rand.nextFloat() * (screenHeight - bomb.getHeight());
            bomb.setImageResource(R.mipmap.bomb);
            RelativeLayout.LayoutParams layoutParamBomb = new RelativeLayout.LayoutParams(((screenWidth * 20) / 450), ((screenHeight * 30) / 450));
            bomb.setLayoutParams(layoutParamBomb);
            bomb.setX(x);
            bomb.setY(y);
            bombSnakeLayout.addView(bomb);
            bombs.add(i, bomb);

        }

    }

    private void update() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isGameOver && !isPaused) {
                    try {
                        Thread.sleep(GameSettings.GAME_THREAD);
                        myHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                float left = head.getX() - head.getWidth();
                                float top = head.getY() - head.getHeight();
                                float right = head.getX() + head.getWidth();
                                float bottom = head.getY() + head.getHeight();

                                for (int i = 0; i < points.size(); i++) {
                                    if (!isCollide) {
                                        ImageView p = points.get(i);
                                        float left1 = p.getX() - p.getWidth();
                                        float top1 = p.getY() - p.getHeight();
                                        float right1 = p.getX() + p.getWidth();
                                        float bottom1 = p.getY() + p.getHeight();

                                        // player
                                        Rect rc1 = new Rect();
                                        rc1.set((int) left, (int) top, (int) right, (int) bottom);
                                        // food item
                                        Rect rc2 = new Rect();
                                        rc2.set((int) left1, (int) top1, (int) right1, (int) bottom1);

                                        p.getHitRect(rc2);

                                        if (Rect.intersects(rc1, rc2)) {

                                            bombSnakeLayout.removeView(p);
                                            points.remove(i);
                                            playerScore++;
                                            isCollide = true;
                                            textScore.setText("Score: " + playerScore);
                                            setNewPoint();
                                            addTail();
                                            shake();
                                            fadeAnim();

                                        }

                                        checkBitten();
                                    }
                                }

                                isCollide = false;

                                for (int i = 0; i < bombs.size(); i++) {

                                    ImageView bomb = bombs.get(i);
                                    float left2 = bomb.getX() - bomb.getWidth();
                                    float top2 = bomb.getY() - bomb.getHeight();
                                    float right2 = bomb.getX() + bomb.getWidth();
                                    float bottom2 = bomb.getY() + bomb.getHeight();

                                    // player
                                    Rect rc1 = new Rect();
                                    rc1.set((int) left, (int) top, (int) right, (int) bottom);
                                    head.getHitRect(rc1);
                                    // food item
                                    Rect rc2 = new Rect();
                                    rc2.set((int) left2, (int) top2, (int) right2, (int) bottom2);
                                    bomb.getHitRect(rc2);

                                    if (Rect.intersects(rc1, rc2)) {
                                        if (isCollide == false) {
                                            isCollide = true;
                                            gameOver();
                                        }

                                    }

                                }

                                if (isGoingRight || clickRight) {

                                    for (int i = parts.size() - 1; i >= 0; i--) {
                                        ImageView imageView = parts.get(i);
                                        if (i > 0) {

                                            ImageView imageView2 = parts.get(i - 1);
                                            imageView.setX(imageView2.getX());
                                            imageView.setY(imageView2.getY());

                                        } else {
                                            imageView.setX(imageView.getX() + speedX);
                                            if (imageView.getX() + imageView.getWidth() >= screenWidth) {

                                                //imageView.setX(screenWidth - (imageView.getWidth() / 2));
                                                imageView.setX(0);

                                            }
                                        }
                                    }

                                } else if (isGoingLeft || clickLeft) {

                                    for (int i = parts.size() - 1; i >= 0; i--) {
                                        ImageView imageView = parts.get(i);
                                        if (i > 0) {

                                            ImageView imageView2 = parts.get(i - 1);
                                            imageView.setX(imageView2.getX());
                                            imageView.setY(imageView2.getY());

                                        } else {
                                            imageView.setX(imageView.getX() - speedX);
                                            if (imageView.getX() <= 0) {

                                                //imageView.setX(0);
                                                imageView.setX(screenWidth - imageView.getWidth());

                                            }
                                        }
                                    }

                                } else if (isGoingUp || clickUp) {

                                    for (int i = parts.size() - 1; i >= 0; i--) {
                                        ImageView imageView = parts.get(i);
                                        if (i > 0) {

                                            ImageView imageView2 = parts.get(i - 1);
                                            imageView.setX(imageView2.getX());
                                            imageView.setY(imageView2.getY());

                                        } else {
                                            imageView.setY(imageView.getY() - speedY);
                                            if (imageView.getY() <= 0) {

                                                //imageView.setY(0);
                                                imageView.setY(screenHeight - imageView.getHeight());

                                            }
                                        }
                                    }

                                } else if (isGoingDown || clickDown) {

                                    for (int i = parts.size() - 1; i >= 0; i--) {
                                        ImageView imageView = parts.get(i);
                                        if (i > 0) {

                                            ImageView imageView2 = parts.get(i - 1);
                                            imageView.setX(imageView2.getX());
                                            imageView.setY(imageView2.getY());

                                        } else {
                                            imageView.setY(imageView.getY() + speedY);
                                            if (imageView.getY() + imageView.getHeight() >= screenHeight) {

                                                //imageView.setY(screenHeight - (imageView.getHeight() / 2));
                                                imageView.setY(0);

                                            }
                                        }
                                    }

                                }

                            }
                        });
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }).start();

    }

    public class SwipeGestureDirector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            boolean result = false;

            if (isSwipe) {

                try {

                    float diffX = e2.getX() - e1.getX();
                    float diffY = e2.getY() - e1.getY();

                    if (Math.abs(diffX) > Math.abs(diffY)) {

                        // horizontal swipe
                        if (Math.abs(diffX) > GameSettings.SWIPE_THRESH_HOLD && Math.abs(velocityX) > GameSettings.SWIPE_VELOCITY_THRESH_HOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                        result = true;

                    } else if (Math.abs(diffY) > GameSettings.SWIPE_THRESH_HOLD && Math.abs(velocityY) > GameSettings.SWIPE_VELOCITY_THRESH_HOLD) {

                        // vertical swipe
                        if (diffY > 0) {
                            onSwipeDown();
                        } else {
                            onSwipeUp();
                        }
                        result = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return result;

            }

            return result;

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!isInitialized) {

            isInitialized = true;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
            screenHeight = size.y;
            myHandler = new Handler();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            gestureDetector = new GestureDetector(null, new SwipeGestureDirector());
            head = new ImageView(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(((screenWidth * 20) / 450), ((screenHeight * 30) / 450));
            head.setImageResource(R.mipmap.head);
            head.setLayoutParams(layoutParams);
            head.setX((screenWidth / 2) - head.getWidth());
            head.setY((screenHeight / 2) - head.getY());
            bombSnakeLayout.addView(head);

            parts = new ArrayList<ImageView>();
            points = new ArrayList<ImageView>();
            parts.add(0, head);
            layoutParams.setMargins(GameSettings.LAYOUT_MARGIN,
                    GameSettings.LAYOUT_MARGIN,
                    GameSettings.LAYOUT_MARGIN,
                    GameSettings.LAYOUT_MARGIN);
            setFoodPoints();

            bombs = new ArrayList<>();
            setBombs();

            buttonsDirectionInit();

            if (hasFocus) {
                isPaused = false;
                update();
            }

            super.onWindowFocusChanged(hasFocus);

        }

    }

}