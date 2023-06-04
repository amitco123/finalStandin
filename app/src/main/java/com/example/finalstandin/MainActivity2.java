package com.example.finalstandin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity {

    ApprovedAdminFragment approvedAdminFragment= new ApprovedAdminFragment();
    RequestAdminFragment requestAdminFragment =new RequestAdminFragment();
    SetDayOffAdminFragment setDayOffAdminFragment =new SetDayOffAdminFragment();
    BottomNavigationView BottomNavigationView;
    public  static String user1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
}