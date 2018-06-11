package com.mindle.androidtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class DrawingBoardSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static String TAG = "DrawingBoardSurfaceView";

    private int mWidth = 1000;
    private int mHeight = 1000;
    private AtomicLong sleepTime = new AtomicLong(1000);
    private AtomicBoolean mIsPaused = new AtomicBoolean(false);
    private int num = 100;
    private Creatures creatures;
    private TextView aliveHintTextView;


    SurfaceHolder mHolder;

    public DrawingBoardSurfaceView(Context context) {
        super(context);

        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public DrawingBoardSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private int draw(Canvas canvas, Paint paint, int[][] matrix, int n) {
        float f = (float) mWidth / n;

        paint.setColor(Color.BLACK);
        filledSquare(canvas, paint, n * f / 2, n * f / 2, n * f / 2);

        // draw n-by-n grid
        int opended = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (matrix[row][col] == 0) {
                    paint.setColor(Color.BLACK);
                } else if (matrix[row][col] == 1) {
                    paint.setColor(Color.WHITE);
                    opended++;
                } else if (matrix[row][col] == 2) {
                    paint.setColor(Color.CYAN);
                    opended++;
                } else if (matrix[row][col] == 3) {
                    paint.setColor(Color.GREEN);
                    opended++;
                } else {
                    paint.setColor(Color.YELLOW);
                    opended++;
                }
                filledSquare(canvas, paint, (col + 0.5f) * f, (n - row - 0.5f) * f, 0.45f * f);
            }
        }
        return opended;
    }

    private void filledSquare(Canvas canvas, Paint paint, float x, float y, float halfLength) {
        RectF rect = new RectF();
        rect.set(x - halfLength, y + halfLength,
                x + halfLength, y - halfLength);
        canvas.drawRect(rect, paint);
    }

    public void setAliveHintTextView(TextView textView) {
        aliveHintTextView = textView;
    }

    public void startGame() {
        mIsPaused.set(false);
        creatures = new Creatures(num);
        new Thread(new DrawThread()).start();
    }


    public void pauseGame() {
        mIsPaused.set(true);
    }

    public void continueGame() {
        mIsPaused.set(false);
        new Thread(new DrawThread()).start();
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime.getAndSet(sleepTime);
    }


    class DrawThread implements Runnable {
        @Override
        public void run() {

            Canvas canvas = null;

            while (!mIsPaused.get()) {
                try {
                    canvas = mHolder.lockCanvas();
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawColor(Color.WHITE);
                    int aliveNum = draw(canvas, paint, creatures.getLivingStatus(), num);
                    aliveHintTextView.setText("活着的细胞个数："+aliveNum);
                    Thread.sleep(sleepTime.get());
                    creatures.nextTime();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
