package com.example.eventos;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventsAdapter extends ArrayAdapter {
    int mResource;
    Context mContext;

    public EventsAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);

        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        convertView = inflater.inflate(mResource, parent, false);

        TextView textName = convertView.findViewById(R.id.eventName);
        TextView textDate = convertView.findViewById(R.id.eventDate);

        Event event = (Event) getItem(position);

        String eventName = event.getName();
        Date eventDate = event.getDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        textName.setText(eventName);
        textDate.setText(dateFormat.format(eventDate));

        Date today = new Date();
        int dateDiff = eventDate.compareTo(today);
        int color;

        if (dateDiff < 0){
            color = Color.RED;
        }
        else if (dateDiff > 0){
            color = Color.GREEN;
        }
        else{
            color = Color.YELLOW;
        }

        textName.setTextColor(color);
        textDate.setTextColor(color);

        return convertView;
    }
}
