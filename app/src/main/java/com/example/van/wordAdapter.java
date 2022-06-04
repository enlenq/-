package com.example.van;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class wordAdapter extends ArrayAdapter<Word> {
    private int resourceId;

    public wordAdapter(Context context, int textViewResourceId, List<Word> objects) {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Word word = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView Name = (TextView) view.findViewById(R.id.word);
        TextView mean = (TextView) view.findViewById(R.id.mean);

        Name.setText(word.getName());
        mean.setText(word.getmean());

        return view;
    }

}