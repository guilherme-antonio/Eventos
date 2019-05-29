package com.example.eventos;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class AddEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }

    public void createItem(View view){
        EditText editName = findViewById(R.id.editName);
        DatePicker editDate = findViewById(R.id.editDate);

        String name = editName.getText().toString();

        Calendar calendar = Calendar.getInstance();
        calendar.set(editDate.getYear(), editDate.getMonth(), editDate.getDayOfMonth());

        Date date = calendar.getTime();
        Event newEvent = new Event(name, date);

        Intent returnIntent = new Intent();

        EventDao eventDao = new EventDao(getApplicationContext());

        long id = eventDao.insertEvent(newEvent);

        returnIntent.putExtra("id", id);

        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
