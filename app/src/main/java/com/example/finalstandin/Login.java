package com.example.finalstandin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    EditText phone, otp;
    Button btngenOTP, btnverify;

    FirebaseAuth mAuth;
    String verificationID;
    ProgressBar bar;
    public static String user1 ="";
    private DatabaseReference databaseReference;
    public static String phone11, tempdate1, phone1;
    String st, date;
    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");

        phone = findViewById(R.id.phone1);
        otp = findViewById(R.id.otp);
        btnverify = findViewById(R.id.btnverifyOTP);
        btngenOTP = findViewById(R.id.btngenerateOTP);
        mAuth = FirebaseAuth.getInstance();
        bar = findViewById(R.id.bar);



        btngenOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkphone = phone.getText().toString();
                sendverificationcode(checkphone);
                if (TextUtils.isEmpty(checkphone) || checkphone.length() != 10 || !TextUtils.isDigitsOnly(checkphone))
                    Toast.makeText(Login.this, "Enter Valid Phone", Toast.LENGTH_SHORT).show();
                else {
                    String number = phone.getText().toString();
                    bar.setVisibility(View.VISIBLE);
                    sendverificationcode(number);
                    otp.setVisibility(View.VISIBLE);
                    btnverify.setVisibility(View.VISIBLE);
                }

            }
        });
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(otp.getText().toString()))
                    Toast.makeText(Login.this, "Enter Valid Phone", Toast.LENGTH_SHORT).show();
                else {
                    verifycode(otp.getText().toString());
                }
            }
        });


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    st = dataSnapshot.getValue().toString();
                    st = st.substring(15, 19);
                    //Toast.makeText(getContext(), st, Toast.LENGTH_SHORT).show();


                    if (st.equals("wait")) {


                        date = dataSnapshot.getKey();
                        phone11 = dataSnapshot.getValue().toString();
                        phone11 = "" + phone11.substring(1, 14);
                        String phone1 = "" + phone11;
                        //Toast.makeText(getContext(), phone , Toast.LENGTH_SHORT).show();
                        String tempdate = "" + date;

                        Boolean temp = isDateAfter(tempdate);
                        if (temp == true) {



                                    }

                        } else {
                            databaseReference.child(tempdate1).removeValue();
                            firestore.collection("Users")
                                    .document(phone1).collection("Orders")
                                    .document(" " + tempdate1).delete();
                        }
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }







    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            final String code = credential.getSmsCode();
            if (code != null) {
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(Login.this, "Verifictaion Faild", Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            verificationID = s;
            Toast.makeText(Login.this, "Code sent", Toast.LENGTH_SHORT);
            btnverify.setEnabled(true);
            bar.setVisibility(View.INVISIBLE);
        }
    };


    private void sendverificationcode(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+972" + phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifycode(String Code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, Code);
        signinbyCredentials(credential);

    }

    private void signinbyCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = task.getResult().getUser();
                    long creationTimestamp = user.getMetadata().getCreationTimestamp();
                    long lastSignInTimestamp = user.getMetadata().getLastSignInTimestamp();
                    if (creationTimestamp == lastSignInTimestamp) {
                        //do create new user

                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT);
                        Intent intent = new Intent(Login.this, Signup.class);
                        intent.putExtra("User",user.getPhoneNumber());
                        startActivity(intent);

                    } else {
                        //user is exists, just do login
                        Toast.makeText(Login.this, "already", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(Login.this, MainActivity2.class);
                        intent.putExtra("User",user.getPhoneNumber());
                        user1=user.getPhoneNumber();

                        startActivity(intent);

                    }
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser =  FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {
            if(currentUser.getPhoneNumber().equals("+97258741140"))
            {
                Intent intent = new Intent(Login.this, MainActivity2.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(Login.this, MainActivity2.class);

                intent.putExtra("User",currentUser.getPhoneNumber());
                user1=currentUser.getPhoneNumber();
                startActivity(intent);
            }

      }
    }

    public static boolean isDateAfter(String dateString) {

        try {
            // Get today's date
            Calendar today = Calendar.getInstance();

            // Parse the given date string
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd,MM,yyyy", Locale.getDefault());
            Date date = dateFormat.parse(dateString);

            // Set the parsed date to 00:00:00 time
            Calendar givenDate = Calendar.getInstance();
            givenDate.setTime(date);
            givenDate.set(Calendar.HOUR_OF_DAY, 0);
            givenDate.set(Calendar.MINUTE, 0);
            givenDate.set(Calendar.SECOND, 0);
            givenDate.set(Calendar.MILLISECOND, 0);

            // Compare the given date with today's date
            return givenDate.after(today);
        } catch (ParseException e) {
            // Handle parsing errors
            e.printStackTrace();
            return false;
        }
    }

}