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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterAdmin extends RecyclerView.Adapter<CustomAdapterAdmin.MyViewHolder> {

    private Context context;
 //   private ArrayList<String> date, time, address , name, money,  tol,oot,fos,thereason, phone,gender,how_much_time, birth ;
//    private ArrayList<Bitmap> imageView;
    private  ArrayList<Order> orders;
    private  ArrayList<Order> orders1;
    private SelectListener listener;


    CustomAdapterAdmin (Context context,ArrayList<Order> orders  ){ //ArrayList<String> date, ArrayList<String> time, ArrayList<String> address , ArrayList<String> name , ArrayList<String> money , ArrayList<String> tol , ArrayList<String> oot ,ArrayList<String> fos , ArrayList<String> thereason, ArrayList<String> phone , ArrayList<String> gender , ArrayList<String> how_much_time ,ArrayList<String> birth ,ArrayList<Bitmap> imageView){

        this.context = context;
        this.orders= orders;
        orders1=new ArrayList<>();
        for (Order order:orders)
        {
            orders1.add(order);
        }
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
            holder.date.setText((orders.get(position).getDate()));
            holder.time.setText((orders.get(position).getTime()));
            holder.address.setText((orders.get(position).getAddress()));

            holder.imageView.setImageBitmap(orders.get(position).getBitmap());
            //            holder.name.setText((name.get(position)));
//            holder.money.setText((money.get(position)));
//            holder.tol.setText((tol.get(position)));
//            holder.oot.setText((oot.get(position)));
//            holder.fos.setText((fos.get(position)));
//            holder.thereason.setText((thereason.get(position)));
//            holder.phone.setText((phone.get(position)));
//            holder.gender.setText((gender.get(position)));
//            holder.how_much_time.setText((how_much_time.get(position)));
//            holder.birth.setText((birth.get(position)));

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(orders1.isEmpty())
                            {
                                listener.onClick(

                                        orders1.get(holder.getAdapterPosition()).getDate(),  orders1.get(holder.getAdapterPosition()).getTime(),
                                        orders1.get(holder.getAdapterPosition()).getAddress(),orders1.get(holder.getAdapterPosition()).getAdress2(),
                                        orders1.get(holder.getAdapterPosition()).getName(), orders1.get(holder.getAdapterPosition()).getPrice(),
                                        orders1.get(holder.getAdapterPosition()).getTol(), orders1.get(holder.getAdapterPosition()).getOot(),
                                        orders1.get(holder.getAdapterPosition()).getFos(), orders1.get(holder.getAdapterPosition()).getThereason(),
                                        orders1.get(holder.getAdapterPosition()).getPhone(), orders1.get(holder.getAdapterPosition()).getGender(),
                                        orders1.get(holder.getAdapterPosition()).getHow_much_time(), orders1.get(holder.getAdapterPosition()).getBirth(),
                                        orders1.get(holder.getAdapterPosition()).getBitmap());
                            }
                            else
                                Toast.makeText(context, "a", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }



        @Override
        public int getItemCount() {

            return orders.size();
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


