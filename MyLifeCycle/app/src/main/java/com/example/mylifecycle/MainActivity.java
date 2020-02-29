package com.example.mylifecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String PLAYER_SCORE = "playerScore";
    public static final String PLAYER_LEVEL = "playerLevel";

    private TextView mLevelText;
    private TextView mScoreText;

    private int mLevel = 0;
    private int mScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("안녕");
        mLevelText = findViewById(R.id.level_text);
        mScoreText = findViewById(R.id.score_text);


        // savedInstanceState 객체가 비어있지 않은경우
        // 이전에 OnCreate로 만둘어 두었다는 뜻
        // savedInstanceState 를 이용해서 이전에 임시로 담아두었던 값들을 다시 TextBox에 담아줌

//        if (savedInstanceState == null) {
//
//        } else {
//

//            mLevel = savedInstanceState.getInt(PLAYER_LEVEL);
//            mScore = savedInstanceState.getInt(PLAYER_SCORE);
//            mLevelText.setText("레벨 :" + mLevel);
//            mScoreText.setText("점수 : " + mScore);
//        }
    }

    public void onLevelUp(View view) {
        mLevel++;
        mLevelText.setText("레벨 :" + mLevel);
    }

    public void onScoreUp(View view) {
        mScore++;
        mScoreText.setText("점수 : " + mScore);
    }

    // 화면전환시 OnStop -> OnCreate를 다시 거치기 떄문에 값이 초기화가 됨
    // 그래서 SaveInstanceState 메소드를 이용해서 이전에 값들을 담아둠
    // [outState] -> 번들에 단순한 데이터를 담아둘 수 있음
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(PLAYER_SCORE, mScore);
        outState.putInt(PLAYER_LEVEL, mLevel);

        super.onSaveInstanceState(outState);
    }

    //onRestoreInstanceState는 복원할 데이터가 있을때만 실행이 된다 -> Null Check 를 할 필요가 없다.
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mLevel = savedInstanceState.getInt(PLAYER_LEVEL);
        mScore = savedInstanceState.getInt(PLAYER_SCORE);
        mLevelText.setText("레벨 :" + mLevel);
        mScoreText.setText("점수 : " + mScore);

        super.onRestoreInstanceState(savedInstanceState);
    }


}



