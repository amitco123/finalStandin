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


import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    EditText phone, otp;
    Button btngenOTP, btnverify;

    FirebaseAuth mAuth;
    String verificationID;
    ProgressBar bar;
    public static String user1 ="";
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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


                        Intent intent = new Intent(Login.this, MainActivity.class);
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
            if(currentUser.getPhoneNumber().equals("+972532748823"))
            {
                Intent intent = new Intent(Login.this, MainActivity2.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(Login.this, Signup.class);

                intent.putExtra("User",currentUser.getPhoneNumber());
                user1=currentUser.getPhoneNumber();
                startActivity(intent);
            }

      }
    }



}