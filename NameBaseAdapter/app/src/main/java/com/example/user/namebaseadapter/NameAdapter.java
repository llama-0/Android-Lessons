package com.example.user.namebaseadapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by user on 05.10.2016.
 */

public class NameAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mNames = new ArrayList<>();
    private Random mRandom = new Random();

    public NameAdapter(Context context) {
        mContext = context;
        for (int i = 0; i < 5; i++) {
            mNames.add(getRandomName());
        }
    }

    private String getRandomName() {
        String[] names = new String[] {
                "Alla", "Huyalla", "George", "Elliza", "Egor", "Igor", "Masha", "Glasha", "Zaibas", "Pivas"
        };
        return names[mRandom.nextInt(names.length)];
    }

    @Override
    public int getCount() {
        return mNames.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            //Make a view
            view = LayoutInflater.from(mContext).inflate(R.layout.name_view, parent, false);
        } else {
            view = convertView;
        }
        //Customize it
        String name = mNames.get(position);
        //Capture
        TextView nameTextView = (TextView) view.findViewById(R.id.name_view);

        nameTextView.setText(name);
        TextView positionTextView = (TextView) view.findViewById(R.id.position_view);
        positionTextView.setText(String.format("I'm #%d", (position + 1)));

        return view;
    }

    public void addName() {
        mNames.add(0, getRandomName()); // "0" here is for adding to the head of the list
        //let the adapter know of changes
        notifyDataSetChanged();
    }

    public void removeName(int position) {
        mNames.remove(position);
        notifyDataSetChanged();
    }

}
