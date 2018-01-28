package com.example.vikkiv.githubrepositories;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LazyAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Repository> data;
    private static LayoutInflater inflater = null;

    public LazyAdapter(Activity activity, ArrayList<Repository> data) {
        this.activity = activity;
        this.data = data;
        inflater = (LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView == null)
            view = inflater.inflate(R.layout.item_listview, null);

        TextView textView = (TextView)view.findViewById(R.id.text);
        ImageView image = (ImageView)view.findViewById(R.id.image_view);

        String text = data.get(position).getName();
        if(data.get(position).getDescription().equals("null"))
            text += "\ndoesn't have a description";
        else
            text += "\n" + data.get(position).getDescription();

        textView.setText(text);
        Picasso.with(activity.getApplicationContext())
                .load(data.get(position).getimageURL())
                .placeholder(R.mipmap.ic_guthub)
                .into(image);
        return view;
    }
}