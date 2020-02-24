package com.example.dynamicui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ArticleFragment extends Fragment {

    public static final String ARG_POSITION = "position";
    private int mCurrentPosition = -1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if(savedInstanceState!=null){
           mCurrentPosition=savedInstanceState.getInt(ARG_POSITION);
       }

        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args= getArguments();
        if(args!= null){
            updateArticleView(args.getInt(ARG_POSITION));
        }else if(mCurrentPosition!=-1){
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {
        TextView article = getView().findViewById(R.id.article_text);
        article.setText(Articles.Articles[position]);
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}
