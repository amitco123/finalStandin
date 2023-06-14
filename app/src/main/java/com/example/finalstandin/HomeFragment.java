package com.example.finalstandin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements SelectListener {
    View view;

    private DatabaseReference databaseReference;
    private ArrayList<Order> orderuser;
    FirebaseFirestore firestore;
    StorageReference storageReference;
    String address, address2 = "", name = "", date, tol, oot, fos, thereason, phone, gender, time, how_much_time, birth, price, st;
    public String date2="";

    ImageView empty_imageview;
    TextView no_data;
    String user1;
    MyDatabaseHelper myDB;
    CustomAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);


        firestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");

        orderuser = new ArrayList<>();
        orderuser.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView12 = view.findViewById(R.id.recyclerView);
        recyclerView12.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    st = dataSnapshot.getValue().toString();
                    st = st.substring(15, 19);


                    if (st.equals("wait")) {


                        date = dataSnapshot.getKey();
                        phone = dataSnapshot.getValue().toString();
                        phone = phone.substring(1, 14);
                        String phone1 = "" + phone;
                        String tempdate = "" + date;
                        if (phone1.equals(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())) {
                            DocumentReference documentRef = db.collection("Users")
                                    .document(phone1).collection("Orders")
                                    .document(" " + date);
                            documentRef.get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot.exists()) {
                                                thereason = documentSnapshot.getString("st9");
                                                address = documentSnapshot.getString("address of the place of the meeting");
                                                //address2 = documentSnapshot.getString("address of the place of the Trap");
                                                time = documentSnapshot.getString("Time of meeting");

                                                // Process the retrieved data
                                                Order order = new Order(address, null, null, tempdate, null, null, null, thereason, null, null, time, how_much_time, null, null, null);
                                                orderuser.add(order);

                                                // Log the address
                                                Log.d("FirestoreData", "Address: " + address);
                                            } else {
                                                //Toast.makeText(getContext(), "dos", Toast.LENGTH_SHORT).show();
                                                Log.d("FirestoreData", "Document does not exist");
                                            }
                                        }
                                    });
                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Runnable mRunnable2;
        Handler mHandler2 = new Handler();
        mRunnable2 = new Runnable() {
            @Override
            public void run() {

                CustomAdapterAdmin2 customAdapterAdmin = new CustomAdapterAdmin2(getContext(), orderuser, HomeFragment.this, getActivity());
                recyclerView12.setAdapter(customAdapterAdmin);
            }
        };
        mHandler2.postDelayed(mRunnable2, 3 * 1000);//Execute after 10 Seconds

        return view;
    }

    @Override
    public void onItemLongClick(String date, String id) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
            date2=date;
        builder.setTitle("Delete " + date + " ?");
        builder.setMessage("Are you sure you want to cancel the request of the stand in order" + date + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                databaseReference.child(date).removeValue();

                FirebaseFirestore.getInstance().collection("Users")
                        .document("" + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).collection("Orders").document(" " + date).delete();

                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    sendSMS();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else  // מבקש אישור לשליחת sms
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.SEND_SMS}, 100);

            }

        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        builder.create().show();
    }

    @Override
    public void onClick(String date, String time, String address, String address2, String name, String money, String tol, String oot, String fos, String thereason, String phone, String gender, String how_much_time, String birth, Bitmap image) {

    }

    private void sendSMS() {
        String phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        String message =  "היי הזמנתך בתאריך" + " "+ date2+ " "+"בוטלה בהצלחה";

        if (!phone.isEmpty() && !message.isEmpty()) // בודק האם השדות ריקים
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message, null, null); // שליחת ה-sms
            Toast.makeText(getActivity(), "SMS sent successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getActivity(), "please enter the details", Toast.LENGTH_LONG).show();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) // בודק האם ניתן אישור לשליחה
            sendSMS();
        else
            Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show(); // מודיע שלא ניתן אישור
    }
}