package com.example.finalstandin;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;

public class Signup extends AppCompatActivity {

    EditText phone, otp, A, fullname;
    Button btngenOTP, btnverify, signupbtn;
    RadioGroup radiogender;
    RadioButton female, male;
    FirebaseAuth mAuth;
    String verificationID;
    ProgressBar bar;
    TextView birth, signup, text, gender;


    private DatePickerDialog datePickerDialog;
    private Button dateButton;


    Button selectImageBtn, uploadImageBtn;
    private ImageView image;

    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent takeit= getIntent();
        String user=takeit.getStringExtra("User");
        mAuth = FirebaseAuth.getInstance();


        radiogender = findViewById(R.id.radiogender);

        fullname = findViewById(R.id.name);
        signupbtn = findViewById(R.id.signupbtn);
        initDatePicker();
        dateButton = findViewById(R.id.birth);
        dateButton.setText(getTodayDate());

        signup = findViewById(R.id.Signup);
        birth = findViewById(R.id.textbirth);
        gender = findViewById(R.id.gender);
        female = findViewById(R.id.female);
        male = findViewById(R.id.male);
        selectImageBtn = findViewById(R.id.selectImageButton);
        image = findViewById(R.id.firebaseImage);



        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String thegender ="";
                if(male.isChecked())
                    thegender="male";
                else
                    thegender="female";
                String name = fullname.getText().toString();
                String date = getTodayDate();


                progressDialog = new ProgressDialog(Signup.this);
                progressDialog.setTitle("uploading file...");
                progressDialog.show();
                storageReference = FirebaseStorage.getInstance().getReference("image/" + user);
                storageReference.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                image.setImageURI(null);
                                Toast.makeText(Signup.this, "successfully uploaded", Toast.LENGTH_SHORT).show();
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Signup.this, "Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", name);
                hashMap.put("date", date);
                hashMap.put("gender", thegender);
                FirebaseFirestore.getInstance().collection("Users")
                        .document("" + user)
                        .set(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Signup.this, "Date saved successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Signup.this, "fa" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                startActivity(new Intent(Signup.this, MainActivity.class));

            }
        });


        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 100);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && data != null && data.getData() != null)
        {
            imageUri = data.getData();
            image.setImageURI(imageUri);

        }
    }





    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "PEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}
