package com.example.finalstandin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterAdmin extends RecyclerView.Adapter<CustomAdapterAdmin.MyViewHolder> {

    private Context context;
    private ArrayList<String> date, time, address , name, money,  tol,oot,fos,thereason, phone,gender,how_much_time, birth ;
    private ArrayList<Bitmap> imageView;
    private ArrayList<Order> orders;
    private SelectListener listener;


    CustomAdapterAdmin (Context context,ArrayList<Order> orders  ){ //ArrayList<String> date, ArrayList<String> time, ArrayList<String> address , ArrayList<String> name , ArrayList<String> money , ArrayList<String> tol , ArrayList<String> oot ,ArrayList<String> fos , ArrayList<String> thereason, ArrayList<String> phone , ArrayList<String> gender , ArrayList<String> how_much_time ,ArrayList<String> birth ,ArrayList<Bitmap> imageView){

        this.context = context;
        this.orders= orders;
//        this.date = date;
//        this.time = time;
//        this.address = address;
//        this.name = name;
//        this.money = money;
//        this.tol = tol;
//        this.oot = oot;
//        this.fos = fos;
//        this.thereason = thereason;
//        this.phone = phone;
//        this.gender = gender;
//        this.how_much_time = how_much_time;
//        this.birth = birth;
//         this.imageView = imageView;
    }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.my_row_admin, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder,final   int position) {
            holder.date.setText(String.valueOf(date.get(position)));
            holder.time.setText(String.valueOf(time.get(position)));
            holder.address.setText(String.valueOf(address.get(position)));
//            holder.name.setText(String.valueOf(name.get(position)));
//            holder.money.setText(String.valueOf(money.get(position)));
//            holder.tol.setText(String.valueOf(tol.get(position)));
//            holder.oot.setText(String.valueOf(oot.get(position)));
//            holder.fos.setText(String.valueOf(fos.get(position)));
//            holder.thereason.setText(String.valueOf(thereason.get(position)));
//            holder.phone.setText(String.valueOf(phone.get(position)));
//            holder.gender.setText(String.valueOf(gender.get(position)));
//            holder.how_much_time.setText(String.valueOf(how_much_time.get(position)));
//            holder.birth.setText(String.valueOf(birth.get(position)));
            holder.imageView.setImageBitmap((imageView.get(position)));

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listener.onClick(

                                    String.valueOf(date.get(holder.getAdapterPosition())),  String.valueOf(time.get(holder.getAdapterPosition())),
                                    String.valueOf(address.get(holder.getAdapterPosition())), String.valueOf(name.get(holder.getAdapterPosition())),
                                    String.valueOf(money.get(holder.getAdapterPosition())), String.valueOf(tol.get(holder.getAdapterPosition())),
                                    String.valueOf(oot.get(holder.getAdapterPosition())), String.valueOf(fos.get(holder.getAdapterPosition())),
                                    String.valueOf(thereason.get(holder.getAdapterPosition())), String.valueOf(phone.get(holder.getAdapterPosition())),
                                    String.valueOf(gender.get(holder.getAdapterPosition())), String.valueOf(how_much_time.get(holder.getAdapterPosition())),
                                    String.valueOf(birth.get(holder.getAdapterPosition())), imageView.get(holder.getAdapterPosition()));
                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return date.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView date, time, address,name, money, tol,oot, fos, thereason,phone, gender, how_much_time,birth;
            ImageView imageView;
            LinearLayout mainLayout;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                date = itemView.findViewById(R.id.book_date);
                time = itemView.findViewById(R.id.book_time_txt);
                address = itemView.findViewById(R.id.Address);
//                name = itemView.findViewById(R.id.name);
//                money = itemView.findViewById(R.id.money);
//                tol = itemView.findViewById(R.id.ToL);
//                oot = itemView.findViewById(R.id.OoT);
//                fos = itemView.findViewById(R.id.FoS);
//                thereason = itemView.findViewById(R.id.thereason);
//                phone = itemView.findViewById(R.id.phone);
//                gender = itemView.findViewById(R.id.gender);
//                how_much_time = itemView.findViewById(R.id.how_much_time);
//                birth = itemView.findViewById(R.id.birth);
                imageView = itemView.findViewById(R.id.imageView);
                mainLayout = itemView.findViewById(R.id.mainLayout);
            }
        }
}


