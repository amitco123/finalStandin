package com.example.finalstandin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity implements ShakeDetector.OnShakeListener {

    ApprovedAdminFragment approvedAdminFragment = new ApprovedAdminFragment();
    RequestAdminFragment requestAdminFragment = new RequestAdminFragment();
    SetDayOffAdminFragment setDayOffAdminFragment = new SetDayOffAdminFragment();
    BottomNavigationView BottomNavigationView;
    public static String user1;
    ShakeDetector shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        shakeDetector = new ShakeDetector(this);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(shakeDetector, acc, SensorManager.SENSOR_DELAY_NORMAL);

        user1 = "+972532748823";
        BottomNavigationView = findViewById(R.id.bottom_navigation1);
        getSupportFragmentManager().beginTransaction().replace(R.id.cont1, requestAdminFragment).commit();
        BottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.requests1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.cont1, requestAdminFragment).commit();
                        return true;
                    case R.id.set_day_off1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.cont1, setDayOffAdminFragment).commit();
                        return true;
                    case R.id.approved1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.cont1, approvedAdminFragment).commit();

                        return true;

                }


                return false;
            }
        });
    }

    @Override
    public void onShakeDetected() {
        Toast.makeText(this, "shake", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity2.this, Login.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}