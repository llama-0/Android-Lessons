package com.example.user.namerecycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.ViewHolder> {

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
    public int getItemCount() {
        return mNames.size();
    }


    //Inflates
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_view, parent, false);
        return new ViewHolder(itemView);
    }

    //pop's with data
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mNames.get(position);
        holder.mNameTextView.setText(name);
        holder.mPositionTextView.setText(String.format("I'm #%d", (position + 1)));

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

    //Captures
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mPositionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            //Now remove is here
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    removeName(getAdapterPosition());
                    return false;
                }
            });

            mNameTextView = (TextView) itemView.findViewById(R.id.name_view);
            mPositionTextView = (TextView) itemView.findViewById(R.id.position_view);
        }
    }
}
