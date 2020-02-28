package com.example.dynamicui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements HeadlinesFragment.OnHeadlineSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null){
            HeadlinesFragment headlinesFragment=new HeadlinesFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container,headlinesFragment)
                    .commit();
        }
    }

    @Override
    public void onHeadlineSelected(int position) {
        ArticleFragment art
    }
}
