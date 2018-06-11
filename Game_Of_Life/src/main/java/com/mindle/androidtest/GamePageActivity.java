package com.mindle.androidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class GamePageActivity extends AppCompatActivity {

    private static String TAG = "GamePageActivity";

    private DrawingBoardSurfaceView surfaceView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        surfaceView = findViewById(R.id.surfaceView);
        RadioGroup speedGroup = findViewById(R.id.speedRadioGroup);
        Button startBtn = findViewById(R.id.btn_start);
        Button pauseContinueBtn = findViewById(R.id.btn_pause_continue);
        TextView aliveHintTextView = findViewById(R.id.aliveHintTextView);

        surfaceView.setSleepTime(1000);
        speedGroup.check(R.id.radioButton1);
        surfaceView.setAliveHintTextView(aliveHintTextView);

        speedGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButton1:
                    surfaceView.setSleepTime(1000);
                    break;
                case R.id.radioButton2:
                    surfaceView.setSleepTime(200);
                    break;
                case R.id.radioButton3:
                    surfaceView.setSleepTime(100);
                    break;
                case R.id.radioButton4:
                    surfaceView.setSleepTime(50);
                    break;
            }
        });
        startBtn.setOnClickListener((v -> {
            surfaceView.startGame();
            pauseContinueBtn.setText("暂停");
            pauseContinueBtn.setEnabled(true);
        }));
        pauseContinueBtn.setOnClickListener(v -> {
            Button button = ((Button) v);
            if ("暂停".equals(button.getText())) {
                surfaceView.pauseGame();
                button.setText("继续");
            } else {
                surfaceView.continueGame();
                button.setText("暂停");
            }
        });
    }


}
