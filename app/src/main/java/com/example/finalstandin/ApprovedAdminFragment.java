package com.example.finalstandin;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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


public class ApprovedAdminFragment extends Fragment  implements SelectListener {

    View view;
    private DatabaseReference databaseReference;
    private ArrayList<Order> orderuser;
    FirebaseFirestore firestore;
    StorageReference storageReference;
    String address,address2="", name = "a", date, tol, oot, fos, thereason, phone, gender, time, how_much_time, birth, price;
    Bitmap bitmap, bitmap1;
    //    String address2, age, money;
    int count = 0;
    Order a;
    String st;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_approved_admin, container, false);
        firestore= FirebaseFirestore.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");

        orderuser = new ArrayList<>();
        orderuser.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView1= view.findViewById(R.id.recyclerView1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    st = dataSnapshot.getValue().toString();
                    st = st.substring(15, 19);
                    //Toast.makeText(getContext(), st, Toast.LENGTH_SHORT).show();


                    if (st.equals("true")) {


                        date = dataSnapshot.getKey();
                        phone = dataSnapshot.getValue().toString();
                        phone = phone.substring(1, 14);
                        //Toast.makeText(getContext(), phone , Toast.LENGTH_SHORT).show();
                        String tempdate =""+ date;
                        storageReference= FirebaseStorage.getInstance().getReference("image/" + phone);

                        DocumentReference documentRef = db.collection("Users")
                                .document(phone).collection("Orders")
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
                                            gender = documentSnapshot.getString("gender");
                                            birth = documentSnapshot.getString("birth");
                                            if (how_much_time.equals("seven"))
                                                price="400";
                                            else if (how_much_time.equals("six")) {
                                                price="350";
                                            }
                                            else if(how_much_time.equals("five"))
                                                price="315";
                                            else if(how_much_time.equals("four"))
                                                price="275";
                                            else if(how_much_time.equals("there"))
                                                price="225";
                                            else if(how_much_time.equals("two"))
                                                price="175";
                                            else
                                                price="100";

                                            // Process the retrieved data
                                            try {
                                                File localFile = File.createTempFile("" + phone, "jpeg");
                                                storageReference.getFile(localFile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                                            bitmap1 =bitmap;
                                                            //Toast.makeText(getContext(), "succ", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                });
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
//                                            Toast.makeText(getContext(), gender  , Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(getContext()," a"+  orderuser.toString() , Toast.LENGTH_SHORT).show();
                                            Order order=new Order(address,address2, name, tempdate, tol, oot, fos, thereason, phone, gender, time, how_much_time, birth,price, bitmap1);
                                            orderuser.add(order);

                                            //Toast.makeText(getContext(),orderuser.toString() , Toast.LENGTH_SHORT).show();
                                            // Log the address
                                            Log.d("FirestoreData", "Address: " + address);
                                        }
                                        else {
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
        Order a=new Order("a", "a","a","a" , "a", "a", "a", "a", "a", "a", "a", "a", "a","a", null);
        orderuser.add(a);
        Runnable mRunnable2;
        Handler mHandler2 = new Handler();
        orderuser.remove(a);
        mRunnable2 = new Runnable() {
            @Override
            public void run() {

                // Toast.makeText(getContext()," "+  orderuser.toString() , Toast.LENGTH_SHORT).show();
                CustomAdapterAdmin customAdapterAdmin = new CustomAdapterAdmin(getActivity(),orderuser,ApprovedAdminFragment.this);
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
        TextView date2, name1, age1, phone1, address1,time2, money1,therreason,gender1,OoT,FoS,ToL,how_much_time1;
        ImageView imageView1;

        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");

        FirebaseFirestore.getInstance().collection("Users")
                .orderBy("Order" , Query.Direction.ASCENDING);


        AlertDialog.Builder tempBuilder = new AlertDialog.Builder(getContext());
        View tempDialogView = getLayoutInflater().inflate(R.layout.dialog_admin, null, false);
        tempBuilder.setView(tempDialogView);
        AlertDialog tempAd = tempBuilder.create();
        date2 = tempAd.findViewById(R.id.date);
        name1 = tempAd.findViewById(R.id.name1);
        age1 = tempAd.findViewById(R.id.age);
        phone1 = tempAd.findViewById(R.id.phone);
        address1 = tempAd.findViewById(R.id.address);
        time2 = tempAd.findViewById(R.id.time);
        money1 = tempAd.findViewById(R.id.money);
        therreason = tempAd.findViewById(R.id.thereason);
        gender1 = tempAd.findViewById(R.id.gender);
        OoT = tempAd.findViewById(R.id.OoT);
        FoS = tempAd.findViewById(R.id.FoS);
        ToL = tempAd.findViewById(R.id.ToL);
        how_much_time1= tempAd.findViewById(R.id.how_much_time);
        imageView1= tempAd.findViewById(R.id.imageView2);


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
    }
}