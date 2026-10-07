package com.rocketrush.demo;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.view.*;
import android.content.Context;
import java.util.Random;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }

    public static class GameView extends View {

        Paint paint = new Paint();
        Random random = new Random();

        float rocketX = 300;
        float rocketY = 500;
        float velocity = 0;

        float obstacleX = 200;
        float obstacleY = -200;
        float obstacleSpeed = 10;

        int score = 0;
        boolean gameOver = false;

        public GameView(Context context) {
            super(context);
            paint.setAntiAlias(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawColor(Color.BLACK);

            // Stars
            paint.setColor(Color.WHITE);

            for (int i = 0; i < 50; i++) {
                float x = (i * 137) % Math.max(1, getWidth());
                float y = (i * 251 + score * 3) % Math.max(1, getHeight());
                canvas.drawCircle(x, y, 2, paint);
            }

            // Rocket
            paint.setColor(Color.RED);

            Path rocket = new Path();
            rocket.moveTo(rocketX, rocketY - 45);
            rocket.lineTo(rocketX - 25, rocketY + 30);
            rocket.lineTo(rocketX, rocketY + 15);
            rocket.lineTo(rocketX + 25, rocketY + 30);
            rocket.close();

            canvas.drawPath(rocket, paint);

            // Flame
            paint.setColor(Color.YELLOW);
            canvas.drawCircle(rocketX, rocketY + 35, 10, paint);

            // Obstacle
            paint.setColor(Color.GRAY);

            canvas.drawRect(
                    obstacleX,
                    obstacleY,
                    obstacleX + 100,
                    obstacleY + 100,
                    paint
            );

            // Score
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("Score: " + score, 30, 60, paint);

            if (!gameOver) {

                rocketY += velocity;
                velocity += 0.4f;

                obstacleY += obstacleSpeed;

                if (obstacleY > getHeight()) {

                    obstacleY = -150;

                    obstacleX = random.nextInt(
                            Math.max(1, getWidth() - 120)
                    );

                    score++;
                    obstacleSpeed += 0.3f;
                }

                // Collision
                if (rocketX > obstacleX - 25 &&
                        rocketX < obstacleX + 125 &&
                        rocketY > obstacleY - 35 &&
                        rocketY < obstacleY + 125) {

                    gameOver = true;
                }

                // Top limit
                if (rocketY < 50) {
                    rocketY = 50;
                    velocity = 0;
                }

                // Bottom limit
                if (rocketY > getHeight() - 50) {
                    gameOver = true;
                }

                invalidate();

            } else {

                paint.setColor(Color.RED);
                paint.setTextSize(55);

                canvas.drawText(
                        "GAME OVER",
                        getWidth() / 2 - 170,
                        getHeight() / 2,
                        paint
                );

                paint.setColor(Color.WHITE);
                paint.setTextSize(30);

                canvas.drawText(
                        "Tap to Restart",
                        getWidth() / 2 - 110,
                        getHeight() / 2 + 60,
                        paint
                );
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                if (gameOver) {

                    rocketY = 500;
                    velocity = 0;
                    obstacleY = -200;
                    obstacleX = random.nextInt(
                            Math.max(1, getWidth() - 120)
                    );

                    score = 0;
                    obstacleSpeed = 10;
                    gameOver = false;

                    invalidate();
                    return true;
                }

                rocketX = event.getX();
                velocity = -10;

                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_MOVE) {

                rocketX = event.getX();
                velocity = -7;

                return true;
            }

            return true;
        }
    }
}
