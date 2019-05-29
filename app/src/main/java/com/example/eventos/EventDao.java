package com.example.eventos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventDao {
    private SQLiteDatabase bd;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public EventDao(Context context){
        bd = context.openOrCreateDatabase("banco", Context.MODE_PRIVATE, null);

        bd.execSQL("CREATE TABLE IF NOT EXISTS eventos ( id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, date smalldatetime )");
    }

    public Event getEventById(long id){
        Cursor cursor = bd.rawQuery("SELECT name, date FROM eventos WHERE id = ?", new String[] {String.valueOf(id)});

        cursor.moveToFirst();

        if (!cursor.isAfterLast()){
            String name = cursor.getString( cursor.getColumnIndex("name"));
            String dateString = cursor.getString( cursor.getColumnIndex("date"));

            Date date = new Date();

            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Event event = new Event(name, date);

            return event;
        }

        return null;
    }

    public ArrayList<Event> getAllEvents(){
        Cursor cursor = bd.rawQuery("SELECT name, date FROM eventos ORDER BY date DESC", null);

        cursor.moveToFirst();

        ArrayList<Event> events = new ArrayList<>();

        if (!cursor.isAfterLast()){
            do
            {
                String name = cursor.getString( cursor.getColumnIndex("name"));
                String dateString = cursor.getString( cursor.getColumnIndex("date"));

                Date date = new Date();

                try {
                    date = dateFormat.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Event event = new Event(name, date);

                events.add(event);
            }
            while (cursor.moveToNext());
        }

        return events;
    }

    public long insertEvent(Event event){
        ContentValues values = new ContentValues();
        values.put("name", event.getName());
        values.put("date", dateFormat.format(event.getDate()));
        return bd.insert("eventos", null, values);
    }
}
