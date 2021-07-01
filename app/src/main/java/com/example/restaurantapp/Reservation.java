package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Reservation extends AppCompatActivity {

    EditText date, time, numPeople;
    Button completeRes;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    ImageView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        date = findViewById(R.id.date_input);
        time = findViewById(R.id.time_input);
        completeRes = findViewById(R.id.complete_btn);
        numPeople = findViewById(R.id.num_person);
        goBack=findViewById(R.id.goBack8);
        goBack.setOnClickListener(view -> finish());

        auth = FirebaseAuth.getInstance();


        date.setInputType(InputType.TYPE_NULL);
        time.setInputType(InputType.TYPE_NULL);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
                showDateDialog(date);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(time);
            }
        });

        completeRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date.getText().toString().equals("") ||  time.getText().toString().equals("") ||
                        numPeople.getText().toString().equals("")) {
                    Toast.makeText(Reservation.this, "Please fill all blanks ! ", Toast.LENGTH_SHORT).show();

                }else if(Integer.parseInt(numPeople.getText().toString())>4){
                    Toast.makeText(Reservation.this, "The number of people cannot be more than four", Toast.LENGTH_SHORT).show();

                }else {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").
                            child(auth.getCurrentUser().getUid()).child("Reservation")
                            .child(date.getText().toString());

                    databaseReference.child("Date").setValue(date.getText().toString());
                    databaseReference.child("Time").setValue(time.getText().toString());
                    databaseReference.child("People").setValue(numPeople.getText().toString());

                    Toast.makeText(Reservation.this, "Reservation created", Toast.LENGTH_SHORT).show();


                }
            }
        });
    }

    private void showDateDialog(final EditText date_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(Reservation.this, dateSetListener, calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimeDialog(final EditText time_in) {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(("HH:mm"));
                time_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new TimePickerDialog(Reservation.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE), false).show();
    }

}