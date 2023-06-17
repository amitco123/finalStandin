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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.telephony.SmsManager;
import android.text.format.DateUtils;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;


public class RequestAdminFragment extends Fragment implements SelectListener {

    View view;
    private DatabaseReference databaseReference;
    public static ArrayList<Order> orderuser;
    FirebaseFirestore firestore;
    StorageReference storageReference;
    String address, address2 = "", name = "", date, tol, oot, fos, thereason, phone, gender, time, how_much_time, birth, price, st;
    Bitmap bitmap, bitmap1;
    ImageView imageView, imageView1;

    public static Node<picUser> node;
    public String date22, time22, address11, address22, name2, money2, tol2, oot2, fos2, thereason2, phone2, gender2, how_much_time2, birth2, tempdate, tempTime, tempaddress, tempthereason, tempdate1;
    public static String currentDate;
    public static CustomAdapterAdmin customAdapterAdmin;
    public static  RecyclerView recyclerView1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_request_admin, container, false);

        imageView=view.findViewById(R.id.imageView6);
        imageView1=view.findViewById(R.id.imageView7);

        firestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");

        orderuser = new ArrayList<>();
        orderuser.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        currentDate = new SimpleDateFormat("dd,MM,yyyy", Locale.getDefault()).format(new Date());
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
                                            name2 = "" + name;
                                            gender = documentSnapshot.getString("gender");
                                            birth = documentSnapshot.getString("birth");
                                            price = documentSnapshot.getString("price");
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
                                                            if (node == null) {
                                                                node = new Node<>(new picUser("" + phone1, bitmap2));
                                                            } else {
                                                                Node<picUser> temp = getLastNode(node);
                                                                temp.setNext(new Node<>(new picUser("" + phone1, bitmap2)));
                                                            }

                                                        }
                                                    }

                                                });
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                            Order order = new Order(address, address2, name2, tempdate, tol, oot, fos, thereason, phone1, gender, time, how_much_time, birth, price, null);
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
                orderuser = sortOrdersByDate(orderuser);

                // Toast.makeText(getContext()," "+  orderuser.toString() , Toast.LENGTH_SHORT).show();
                customAdapterAdmin = new CustomAdapterAdmin(getContext(), orderuser, RequestAdminFragment.this, getActivity());
                recyclerView1.setAdapter(customAdapterAdmin);
                if(orderuser.size()==0)
                {
                    imageView.setVisibility(View.VISIBLE);
                    imageView1.setVisibility(View.VISIBLE);
                }
            }
        };
        mHandler2.postDelayed(mRunnable2, 5 * 1000);//Execute after 10 Seconds


        return view;
    }

    @Override
    public void onItemLongClick(String date, String id) {

    }

    @Override
    public void onClick(String date, String time, String address, String address2, String name, String money, String tol, String oot, String fos, String thereason, String phone, String gender, String how_much_time, String birth, Bitmap image) {
        TextView date2, name1, age1, phone1, address1, address3, time2, money1, therreason, gender1, OoT, FoS, ToL, how_much_time1;
        ImageView imageView1;
        Button yes, no, go;
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");
        Order order1 = new Order(""+address, ""+address2, ""+name, ""+date, ""+tol,""+ oot, ""+fos, ""+thereason,""+ phone,""+ gender, ""+time,""+ how_much_time,""+ birth,""+ price, image);


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
        address3 = tempAd.findViewById(R.id.Address2);
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

        tempdate = "" + date;
        tempTime = "" + time;
        tempaddress = "" + address;
        tempthereason = "" + thereason;
        date22 = date;
        phone2 = phone;
        time22 = time;
        address11 = address;
        money2 = money;
        how_much_time2 = "" + how_much_time;
        date2.setText(date22);
        name1.setText(name);
        age1.setText(birth);
        phone1.setText(phone);
        address1.setText(address);
        address3.setText(address2);
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

                                       } else  // מבקש אישור לשליחת sms
                                           ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.SEND_SMS}, 100);

                                       Intent i = new Intent();

                                       String day = "" + tempdate.substring(0, tempdate.indexOf(","));
                                       tempdate = "" + tempdate.substring(tempdate.indexOf(",") + 1);
                                       String month = "" + tempdate.substring(0, tempdate.indexOf(","));
                                       tempdate = "" + tempdate.substring(tempdate.indexOf(",") + 1);
                                       String year = "" + tempdate;

                                       tempTime = "" + convertTime(tempTime);
                                       String hour = "" + tempTime.substring(0, tempTime.indexOf(":"));
                                       tempTime = "" + tempTime.substring(tempTime.indexOf(":") + 1);
                                       String minutes = "" + tempTime;
                                       //int how_much_timeI=getHowMuchTime(how_much_time2);
                                       // mimeType will popup the chooser any  for any implementing application (e.g. the built in calendar or applications such as "Business calendar"
                                       i.setType("vnd.android.cursor.item/event");
                                       Calendar calendar = Calendar.getInstance();
                                       calendar.set(Calendar.YEAR, Integer.parseInt("" + year)); // Set the year
                                       calendar.set(Calendar.MONTH, getMonth(Integer.parseInt("" + month))); // Set the month (Note: January is 0)
                                       calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt("" + day)); // Set the day of the month
                                       calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour)); // Set the hour (in 24-hour format)
                                       calendar.set(Calendar.MINUTE, Integer.parseInt(minutes)); // Set the minute

                                       // the time the event should start in millis. This example uses now as the start time and ends in 1 hour
                                       i.putExtra("beginTime", calendar.getTimeInMillis());
                                       i.putExtra("endTime", calendar.getTimeInMillis() + DateUtils.HOUR_IN_MILLIS);
                                       String eventTitle = "" + tempthereason;
                                       String eventLocation = "" + tempaddress;
                                       i.putExtra("title", eventTitle);
                                       i.putExtra("eventLocation", eventLocation);

                                       // the action
                                       i.setAction(Intent.ACTION_EDIT);
                                       startActivity(i);


                                       orderuser.remove(order1);
                                       //orderuser.clear();
                                       tempAd.dismiss();
                                       Intent intent = new Intent(getActivity(), MainActivity2.class);
                                       startActivity(intent);


                                   }
                               }

        );
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    sendSMS();
                    //  Toast.makeText(getActivity(), "a", Toast.LENGTH_SHORT).show();
                } else  // מבקש אישור לשליחת sms
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.SEND_SMS}, 100);
                databaseReference.child(date).removeValue();
                firestore.collection("Users")
                        .document(phone).collection("Orders")
                        .document(" " + date).delete();

                tempAd.dismiss();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
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

    private int getMonth(int i) {
        switch (i) {
            case 1:
                return Calendar.JANUARY;
            case 2:
                return Calendar.FEBRUARY;
            case 3:
                return Calendar.MARCH;
            case 4:
                return Calendar.APRIL;
            case 5:
                return Calendar.MAY;
            case 6:
                return Calendar.JUNE;
            case 7:
                return Calendar.JULY;
            case 8:
                return Calendar.AUGUST;
            case 9:
                return Calendar.SEPTEMBER;
            case 10:
                return Calendar.OCTOBER;
            case 11:
                return Calendar.NOVEMBER;
            default:
                return Calendar.DECEMBER;
        }
    }

    public static String convertTime(String time) {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1].split("\\s+")[0]);

        if (time.contains("PM") && hour != 12) {
            hour += 12;
        } else if (time.contains("AM") && hour == 12) {
            hour = 0;
        }

        return String.format("%02d:%02d", hour, minute);
    }

    public static int getHowMuchTime(String st) {
        switch (st) {
            case "one":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            case "four":
                return 4;
            case "five":
                return 5;
            case "six":
                return 6;
            default:
                return 7;
        }
    }

    public static ArrayList<Order> sortOrdersByDate(ArrayList<Order> orders) {
        orders.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd,MM,yyyy");
                try {
                    Date date1 = dateFormat.parse(o1.getDate());
                    Date date2 = dateFormat.parse(o2.getDate());
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return orders;
    }
}



