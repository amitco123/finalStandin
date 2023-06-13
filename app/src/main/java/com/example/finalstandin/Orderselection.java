package com.example.finalstandin;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Orderselection extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView address, textPlace, tvtimer1, date;
    int t1Hour, t1Minute;
    private final static int REQUEST_CODE = 100;
    RadioGroup rgBtnGroupDescribe1, rgBtnGroupDescribe2, rgBtnGroupDescribe3, rgBtnGroupPlace, rgBtnGroupcheck;
    Spinner spinner;
    TextInputLayout textInputLayout;
    EditText loc1, loc;
    AutoCompleteTextView autoCompleteTextView;
    FirebaseAuth mAuth;
    boolean flag;
    String sendaddress, Time, RdbToL, RdbFoS, RdbOoT, sendloc1, sendloc = "", senddate, st9, sendhowmuchtime, user1, name = " aba", birth, gender ,st;
    private DatabaseReference databaseReference;
    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderselection);

        date = findViewById(R.id.textView1);
        Intent takeit = getIntent();
        String st = takeit.getStringExtra("date");
        user1 = takeit.getStringExtra("user1");
        user1= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        Toast.makeText(Orderselection.this, user1, Toast.LENGTH_SHORT).show();
        date.setText(st);
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");


        mAuth = FirebaseAuth.getInstance();
        rgBtnGroupDescribe1 = findViewById(R.id.rgBtnGroupDescribe1);
        rgBtnGroupDescribe2 = findViewById(R.id.rgBtnGroupDescribe2);
        rgBtnGroupDescribe3 = findViewById(R.id.rgBtnGroupDescribe3);
        rgBtnGroupPlace = findViewById(R.id.rgBtnGroupPlace);
        rgBtnGroupcheck = findViewById(R.id.rgBtnGroupcheck);
        address = findViewById(R.id.address);
        textPlace = findViewById(R.id.textPlace);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Orderselection.this);
        textInputLayout = findViewById(R.id.menu_drop);
        autoCompleteTextView = findViewById(R.id.drop_items);
        tvtimer1 = findViewById(R.id.tv_timer1);
        spinner = findViewById(R.id.spinner1);
        loc1 = findViewById(R.id.loc1);
        loc = findViewById(R.id.loc);


        String[] items = {"Costume competition", "Getting ready for a date", "exhibition", "Doubles tennis"};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Orderselection.this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sendhowmuchtime = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(Orderselection.this, R.layout.item_list, items);
        autoCompleteTextView.setAdapter(itemAdapter);


        tvtimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Orderselection.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t1Hour = hourOfDay;
                                t1Minute = minute;
                                String time = t1Hour + ":" + t1Minute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                                    tvtimer1.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, 12, 0, true

                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t1Hour, t1Minute);
                timePickerDialog.show();

            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                st9 = adapterView.getItemAtPosition(i).toString();
            }
        });


        rgBtnGroupDescribe1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId) {
                    case R.id.rbTalker:
                        RdbToL = "Talker";
                        break;
                    case R.id.rbListener:
                        RdbToL = "Listener";
                        break;
                }
            }
        });
        rgBtnGroupDescribe2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId) {
                    case R.id.rbFunny: {
                        RdbFoS = "Funny";

                    }
                    break;
                    case R.id.rbSerious: {
                        RdbFoS = "Serious";
                    }
                    break;
                }
            }
        });
        rgBtnGroupDescribe3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId) {
                    case R.id.rbOutgoing: {
                        RdbOoT = "Outgoing";
                    }
                    break;
                    case R.id.rbTimid: {
                        RdbOoT = "Timid";
                    }
                    break;
                }
            }
        });
        rgBtnGroupPlace.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                sendaddress = "2";
                switch (checkedId) {
                    case R.id.rbYour: {
                        address.setVisibility(View.VISIBLE);
                        loc.setVisibility(View.INVISIBLE);
                        sendloc = address.getText().toString();
                        getLastLocation();
                    }
                    break;
                    case R.id.rbAnother: {
                        address.setVisibility(View.INVISIBLE);
                        loc.setVisibility(View.VISIBLE);
                        sendloc = loc.getText().toString();
                    }
                    break;
                }
            }
        });

        rgBtnGroupcheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rbYes: {
                        flag = true;
                        rgBtnGroupPlace.setVisibility(View.VISIBLE);
                        textPlace.setVisibility(View.VISIBLE);
                    }
                    break;
                    case R.id.rbNo: {
                        flag = false;
                        rgBtnGroupPlace.setVisibility(View.INVISIBLE);
                        textPlace.setVisibility(View.INVISIBLE);
                        address.setVisibility(View.INVISIBLE);
                        loc.setVisibility(View.INVISIBLE);
                    }
                    break;
                }
            }
        });
//        rgBtnGroupPlace.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rbAnother: {
//                        flag = true;
//                        address.setVisibility(View.INVISIBLE);
//                        loc.setVisibility(View.VISIBLE);
//                        sendloc= loc.getText().toString();
//                    }
//                    break;
//                    case R.id.rbYour: {
//                        flag = false;
//
//
//                        address.setVisibility(View.VISIBLE);
//                        loc.setVisibility(View.INVISIBLE);
//                        sendloc= address.getText().toString();
//                    }
//                    break;
//                }
//            }
//        });
    }







    private void getLastLocation() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null)
                    {
                        Geocoder geocoder= new Geocoder(Orderselection.this,Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1 );
                            address.setText(" "+ addresses.get(0).getAddressLine(0));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }
            });

        }
        else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(Orderselection.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if( requestCode==REQUEST_CODE){
            if( grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else
            {
                Toast.makeText(this, "Request Permission", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public void gopay(View view) {

        Time = tvtimer1.getText().toString();
        sendloc1 = loc1.getText().toString();
        senddate = date.getText().toString();
        if (flag == false) {
            if (Time.equals("select time of meeting") || sendloc1.equals("") || senddate.equals("") || st9.equals("")) {
                Toast.makeText(this, "something is missing", Toast.LENGTH_SHORT).show();
            } else {
                sendloc = " ";
                MyDatabaseHelper myDB = new MyDatabaseHelper(Orderselection.this);
                myDB.addBook(Time.trim(),
                        senddate.trim(),
                        st9.trim(), sendloc1.trim());
                //            Time, sendloc1 ,senddate,RdbOoT,RdbFoS,RdbToL,s9,sendhowmuchtime
                databaseReference.child(senddate).child(user1).setValue("wait");
                HashMap<String, Object> hashMap1 = new HashMap<>();
                hashMap1.put("Time of meeting", Time);
                hashMap1.put("st9", st9);
                hashMap1.put("RdbOoT", RdbOoT);
                hashMap1.put("RdbFoS", RdbFoS);
                hashMap1.put("RdbToL", RdbToL);
                hashMap1.put("how much time", sendhowmuchtime);
                hashMap1.put("address of the place of the Trap", "");
                hashMap1.put("address of the place of the meeting", sendloc1);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference documentRef = db.collection("Users")
                        .document(user1);
                documentRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    name = documentSnapshot.getString("name");
                                    gender = documentSnapshot.getString("gender");
                                    birth = documentSnapshot.getString("date");
                                    // Access other fields as needed
//                                            String field2 = documentSnapshot.getString("field2");
//                                            int field3 = documentSnapshot.getLong("field3").intValue();
                                    // Process the retrieved data
                                    //Toast.makeText(getContext(), address, Toast.LENGTH_SHORT).show();
                                    // Log the address
                                    //Toast.makeText(getContext(), address  + " "+ how_much_time  + " "+ time  + " "+thereason, Toast.LENGTH_SHORT).show();
                                    hashMap1.put("name", name);
                                    hashMap1.put("gender", gender);
                                    hashMap1.put("birth", birth);
                                    //Log.d("FirestoreData", "Address: " + address);

                                    FirebaseFirestore.getInstance().collection("Users")
                                            .document("" + user1).collection("Orders").document(" " + senddate)
                                            .set(hashMap1)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(Orderselection.this, "Date saved successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Orderselection.this, "Date failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Log.d("FirestoreData", "Document does not exist");
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("FirestoreData", "Error retrieving document", e);
                            }
                        });


                startActivity(new Intent(Orderselection.this, MainActivity.class));
            }
        }



        else {
            if (Time.equals("select time of meeting") || sendloc1.equals("") || senddate.equals("") || st9.equals("") || sendloc.equals("")) {
                Toast.makeText(this, "something is missing", Toast.LENGTH_SHORT).show();
            } else {
                MyDatabaseHelper myDB = new MyDatabaseHelper(Orderselection.this);
                myDB.addBook(Time.trim(),
                        senddate.trim(),
                        st9.trim(), sendloc1.trim());
                //            Time, sendloc1 ,senddate,RdbOoT,RdbFoS,RdbToL,s9,sendhowmuchtime
                databaseReference.child(senddate).child(user1).setValue("wait");
                HashMap<String, Object> hashMap1 = new HashMap<>();
                hashMap1.put("Time of meeting", Time);
                hashMap1.put("st9", st9);
                hashMap1.put("RdbOoT", RdbOoT);
                hashMap1.put("RdbFoS", RdbFoS);
                hashMap1.put("RdbToL", RdbToL);
                hashMap1.put("how much time", sendhowmuchtime);
                hashMap1.put("address of the place of the Trap", sendloc);
                hashMap1.put("address of the place of the meeting", sendloc1);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference documentRef = db.collection("Users")
                        .document(user1);
                documentRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    name = documentSnapshot.getString("name");
                                    gender = documentSnapshot.getString("gender");
                                    birth = documentSnapshot.getString("date");
                                    // Access other fields as needed
//                                            String field2 = documentSnapshot.getString("field2");
//                                            int field3 = documentSnapshot.getLong("field3").intValue();
                                    // Process the retrieved data
                                    //Toast.makeText(getContext(), address, Toast.LENGTH_SHORT).show();
                                    // Log the address
                                    //Toast.makeText(getContext(), address  + " "+ how_much_time  + " "+ time  + " "+thereason, Toast.LENGTH_SHORT).show();
                                    hashMap1.put("name", name);
                                    hashMap1.put("gender", gender);
                                    hashMap1.put("birth", birth);
                                    //Log.d("FirestoreData", "Address: " + address);

                                    FirebaseFirestore.getInstance().collection("Users")
                                            .document("" + user1).collection("Orders").document(" " + senddate)
                                            .set(hashMap1)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(Orderselection.this, "Date saved successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Orderselection.this, "Date failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Log.d("FirestoreData", "Document does not exist");
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("FirestoreData", "Error retrieving document", e);
                            }
                        });


                startActivity(new Intent(Orderselection.this, MainActivity.class));
            }
        }
    }
}
