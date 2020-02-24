package com.example.dynamicui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class HeadlinesFragment extends ListFragment {

    interface OnHeadlineSelectedListener {
        void onHeadlineSelected(int position);
    }

    private OnHeadlineSelectedListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,Articles.Headline));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnHeadlineSelectedListener");
        }


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if(mListener!=null){
            mListener.onHeadlineSelected(position);
        }
    }
}
