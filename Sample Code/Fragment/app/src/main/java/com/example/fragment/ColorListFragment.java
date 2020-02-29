package com.example.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import java.util.Arrays;
import java.util.List;

public class ColorListFragment extends ListFragment {

    private OnColorSelectedListener mListener;

    interface OnColorSelectedListener {
        void onColorSelected(int color);
    }

    //액티비티에 프래그먼트가 붙을때 실행된다
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnColorSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName() + " 는 OnColorSelectedListener 를 구현해야함");
        }
    }

    // onCreateView 는 이미 ListFragment 에서 완성이 되었고
    // onCreateView 다음에 onViewCreated 가 실행이 된다.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> colorList = Arrays.asList("Red", "Green", "Blue");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, colorList);

        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        String colorString = adapter.getItem(position);

        int color = Color.RED;

        switch (colorString) {
            case "Red":
                color = Color.RED;
                break;

            case "Blue":
                color = Color.BLUE;
                break;

            case "Green":
                color = Color.GREEN;
                break;

        }

        if(mListener != null){
            mListener.onColorSelected(color);
        }


    }
}
