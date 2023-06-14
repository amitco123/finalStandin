package com.example.finalstandin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class OrderFragment extends Fragment {

    View view;

        private CalendarView calendarView;
        private TextView textView;

        private String stringDateSelected;
            private DatabaseReference databaseReference;
        Button button;
        String user1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);

        user1=((MainActivity) getContext()).user;
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

                Intent intent = new Intent(getActivity(), Orderselection.class);
                String date=textView.getText().toString();
                intent.putExtra("date",date);
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
                        textView.setText("taken");


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