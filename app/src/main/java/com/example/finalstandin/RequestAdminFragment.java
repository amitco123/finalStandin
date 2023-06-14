package com.example.finalstandin;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class RequestAdminFragment extends Fragment implements SelectListener {

    View view;
    private DatabaseReference databaseReference;
    private ArrayList<Order> orderuser;
    FirebaseFirestore firestore;
    StorageReference storageReference;
    String address, address2 = "", name = "", date, tol, oot, fos, thereason, phone, gender, time, how_much_time, birth, price, st;
    Bitmap bitmap, bitmap1;
   public static Node<picUser> node;
    public String date22, time22, address11, address22, name2, money2, tol2, oot2, fos2, thereason2, phone2, gender2, how_much_time2, birth2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_request_admin, container, false);

        firestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");

        orderuser = new ArrayList<>();
        orderuser.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    st = dataSnapshot.getValue().toString();
                    st = st.substring(15, 19);
                    //Toast.makeText(getContext(), st, Toast.LENGTH_SHORT).show();


                    if (st.equals("wait")) {


                        date = dataSnapshot.getKey();
                        phone = dataSnapshot.getValue().toString();
                        phone = "" + phone.substring(1, 14);
                        String phone1 = "" + phone;

                        //Toast.makeText(getContext(), phone , Toast.LENGTH_SHORT).show();
                        String tempdate = "" + date;


                        DocumentReference documentRef = db.collection("Users")
                                .document("" + phone1).collection("Orders")
                                .document(" " + date);
                        documentRef.get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            thereason = documentSnapshot.getString("st9");
                                            fos = documentSnapshot.getString("RdbFoS");
                                            oot = documentSnapshot.getString("RdbOoT");
                                            tol = documentSnapshot.getString("RdbToL");
                                            address = documentSnapshot.getString("address of the place of the meeting");
                                            address2 = documentSnapshot.getString("address of the place of the Trap");
                                            time = documentSnapshot.getString("Time of meeting");
                                            how_much_time = documentSnapshot.getString("how much time");
                                            name = documentSnapshot.getString("name");
                                            name2= ""+ name;
                                            gender = documentSnapshot.getString("gender");
                                            birth = documentSnapshot.getString("birth");
                                            if (how_much_time.equals("seven"))
                                                price = "400";
                                            else if (how_much_time.equals("six")) {
                                                price = "350";
                                            } else if (how_much_time.equals("five"))
                                                price = "315";
                                            else if (how_much_time.equals("four"))
                                                price = "275";
                                            else if (how_much_time.equals("there"))
                                                price = "225";
                                            else if (how_much_time.equals("two"))
                                                price = "175";
                                            else
                                                price = "100";


                                            // Process the retrieved data
                                            try {
                                                //Toast.makeText(getActivity(), " "+phone1, Toast.LENGTH_SHORT).show();
                                                storageReference = FirebaseStorage.getInstance().getReference("image/" + phone1);
                                                File localFile = File.createTempFile(phone1, "jpeg");


                                                storageReference.getFile(localFile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                                                        if (task.isSuccessful()) {

                                                            Bitmap bitmap2 = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                                            if(node==null)
                                                            {
                                                                node = new Node<>(new picUser(""+phone1,bitmap2));
                                                            }
                                                            else{
                                                                Node <picUser> temp = getLastNode(node);
                                                                temp.setNext(new Node<>(new picUser(""+phone1,bitmap2)));
                                                            }

                                                        }
                                                    }

                                                });
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                            Order order = new Order(address, address2,name2, tempdate, tol, oot, fos, thereason, phone1, gender, time, how_much_time, birth, price, null);
                                            orderuser.add(order);
//                                            Toast.makeText(getContext(), gender  , Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(getContext()," a"+  orderuser.toString() , Toast.LENGTH_SHORT).show();


                                            //Toast.makeText(getContext(),orderuser.toString() , Toast.LENGTH_SHORT).show();
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Runnable mRunnable2;
        Handler mHandler2 = new Handler();
//        orderuser.remove(a);
        mRunnable2 = new Runnable() {
            @Override
            public void run() {

                // Toast.makeText(getContext()," "+  orderuser.toString() , Toast.LENGTH_SHORT).show();
                CustomAdapterAdmin customAdapterAdmin = new CustomAdapterAdmin(getContext(), orderuser, RequestAdminFragment.this, getActivity());
                recyclerView1.setAdapter(customAdapterAdmin);
            }
        };
        mHandler2.postDelayed(mRunnable2, 3 * 1000);//Execute after 10 Seconds


        return view;
    }

    @Override
    public void onItemLongClick(String date, String id) {

    }

    @Override
    public void onClick(String date, String time, String address, String address2, String name, String money, String tol, String oot, String fos, String thereason, String phone, String gender, String how_much_time, String birth, Bitmap image) {
        TextView date2, name1, age1, phone1, address1, time2, money1, therreason, gender1, OoT, FoS, ToL, how_much_time1;
        ImageView imageView1;
        Button yes, no ,go;
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");


        AlertDialog.Builder tempBuilder = new AlertDialog.Builder(getContext());
        View tempDialogView = getLayoutInflater().inflate(R.layout.dialog_admin, null, false);
        tempBuilder.setView(tempDialogView);
        AlertDialog tempAd = tempBuilder.create();
        tempAd.show();
        date2 = tempAd.findViewById(R.id.date);
        name1 = tempAd.findViewById(R.id.name1);
        age1 = tempAd.findViewById(R.id.age);
        phone1 = tempAd.findViewById(R.id.phone);
        address1 = tempAd.findViewById(R.id.Address1);
        time2 = tempAd.findViewById(R.id.time);
        money1 = tempAd.findViewById(R.id.money);
        therreason = tempAd.findViewById(R.id.thereason);
        gender1 = tempAd.findViewById(R.id.gender12);
        OoT = tempAd.findViewById(R.id.OoT);
        FoS = tempAd.findViewById(R.id.FoS);
        ToL = tempAd.findViewById(R.id.ToL);
        how_much_time1 = tempAd.findViewById(R.id.how_much_time);
        imageView1 = tempAd.findViewById(R.id.imageView2);
        yes = tempAd.findViewById(R.id.yes);
        no = tempAd.findViewById(R.id.no);


        date22 = date;
        phone2 = phone;
        time22 = time;
        address11 = address;
        money2 = money;
        how_much_time2 = how_much_time;
        date2.setText(date);
        name1.setText(name);
        age1.setText(birth);
        phone1.setText(phone);
        address1.setText(address);
        time2.setText(time);
        money1.setText(money);
        therreason.setText(thereason);
        gender1.setText(gender);
        OoT.setText(oot);
        FoS.setText(fos);
        ToL.setText(tol);
        how_much_time1.setText(how_much_time);
        imageView1.setImageBitmap(image);
        phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone=" + phone;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        address1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Launch Waze to look for Hawaii:
                    String url = "https://waze.com/ul?q=" + address + "&navigate=yes";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // If Waze is not installed, open it in Google Play:
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                    startActivity(intent);
                }

            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       databaseReference.child(date).child(phone).setValue("true");
                                       if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.SEND_SMS)
                                               == PackageManager.PERMISSION_GRANTED) {
                                           sendSMS1();
                                           Intent intent = new Intent(getActivity(), MainActivity2.class);
                                           startActivity(intent);
                                       } else  // מבקש אישור לשליחת sms
                                           ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.SEND_SMS}, 100);
                                   }
                               }

        );
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    sendSMS();
                    Intent intent = new Intent(getActivity(), MainActivity2.class);
                    startActivity(intent);
                    //  Toast.makeText(getActivity(), "a", Toast.LENGTH_SHORT).show();
                } else  // מבקש אישור לשליחת sms
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.SEND_SMS}, 100);
                databaseReference.child(date).removeValue();
                firestore.collection("Users")
                        .document(phone).collection("Orders")
                        .document(" " + date).delete();
            }

        });


    }


    private void sendSMS() {
        String phone = phone2;
        String message = "היי סליחה אך בסוף ממלא המקום אינו יכול בתאריך" + " " + date22 + " ";

        if (!phone.isEmpty() && !message.isEmpty()) // בודק האם השדות ריקים
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message, null, null); // שליחת ה-sms
            Toast.makeText(getActivity(), "SMS sent successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getActivity(), "please enter the details", Toast.LENGTH_LONG).show();
    }

    private void sendSMS1() {
        String phone = phone2;
        String message = "היי הזמנה בתאריך" + " " + date22 + " " + "אושרה, בשעה:" + " " + time22 + " " + "במקום:" + " " + address11;

// " בהמשך  "
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


    public static Bitmap getBitmapFromName(String name, Node<picUser> node) {
        Node<picUser> g = node;
        while (g != null && g.getValue() != null && !g.getValue().getPhone().equals(name))
            g = g.getNext();

        if (g == null || g.getValue() == null) {
            return null;
        }
        return g.getValue().getBitmap();
    }

    public static Bitmap getBitmapFromName(String name) {
        Node<picUser> g = node;
        while (g != null && g.getValue() != null && !g.getValue().getPhone().equals(name))
            g = g.getNext();

        if (g == null || g.getValue() == null) {
            return null;
        }
        return g.getValue().getBitmap();
    }

    public static Node<picUser> getLastNode(Node<picUser> node) {
        Node<picUser> n = node;
        while (n.getNext() != null)
            n = n.getNext();
        return n;
    }
}


