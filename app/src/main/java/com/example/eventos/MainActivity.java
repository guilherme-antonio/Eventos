package com.example.eventos;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<Event> adaptador;
    private EventDao eventDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView eventsView = findViewById(R.id.events);

        eventDao = new EventDao(getApplicationContext());

        ArrayList<Event> events = eventDao.getAllEvents();

        adaptador = new EventsAdapter(getApplicationContext(),
                R.layout.layout_events_list,
                events);

        eventsView.setAdapter(adaptador);
    }

    public void addNewEvent(View view){
        Intent intent = new Intent(this, AddEvent.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                long eventId = data.getLongExtra("id", 0);

                Event newEvent = eventDao.getEventById(eventId);

                adaptador.add(newEvent);

                adaptador.sort(new Comparator<Event>() {
                    @Override
                    public int compare(Event lhs, Event rhs) {
                        return rhs.getDate().compareTo(lhs.getDate());
                    }
                });
            }
        }
    }
}
