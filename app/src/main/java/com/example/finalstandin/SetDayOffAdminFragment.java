package com.example.finalstandin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SetDayOffAdminFragment extends Fragment {

    View view;

    private CalendarView calendarView;
    private TextView textView;

    private String stringDateSelected;
    private DatabaseReference databaseReference;
    Button button;
    String user1 = "+972532748823";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


       view= inflater.inflate(R.layout.fragment_set_day_off_admin, container, false);

        button=view.findViewById(R.id.button1);
        calendarView =view.findViewById(R.id.calendarView);
        textView =view.findViewById(R.id.textView);
        calendarView.setMinDate(System.currentTimeMillis() - 1000);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int day, int month, int year) {
                stringDateSelected = Integer.toString(year) +","+ ""+ Integer.toString(month+1) +","+ ""+Integer.toString(day);
                calendarClicked();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date=textView.getText().toString();
                databaseReference.child(date).child(user1).setValue("Dayoff");
                Intent intent = new Intent(getActivity(), MainActivity2.class);

                startActivity(intent);
            }
        });
        return  view;
    }
    private void calendarClicked(){
        databaseReference.child(stringDateSelected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    if(snapshot.getValue().equals("taken")){
                        textView.setText("taken");
                    }
                    else{
                        textView.setText("vacation");
                    }
                    button.setVisibility(View.INVISIBLE);


                }else {

                    textView.setText(stringDateSelected);
                    button.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


}