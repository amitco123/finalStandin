package com.example.finalstandin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterAdminForApproved extends RecyclerView.Adapter<CustomAdapterAdminForApproved.MyViewHolder> {

    private Context context;
    private Activity activity;
    public ArrayList<Order> orders;
    private SelectListener listener;


    CustomAdapterAdminForApproved(Context context, ArrayList<Order> orders, SelectListener listener, Activity activity) { //ArrayList<String> date, ArrayList<String> time, ArrayList<String> address , ArrayList<String> name , ArrayList<String> money , ArrayList<String> tol , ArrayList<String> oot ,ArrayList<String> fos , ArrayList<String> thereason, ArrayList<String> phone , ArrayList<String> gender , ArrayList<String> how_much_time ,ArrayList<String> birth ,ArrayList<Bitmap> imageView){

        this.context = context;
        this.orders = orders;
        this.listener = listener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.date.setText((orders.get(position).getDate()));
        holder.time.setText((orders.get(position).getTime()));

        holder.address.setText((orders.get(position).getAddress()));

        //  holder.imageView.setImageBitmap(orders.get(position).getBitmap());
        Bitmap bitmap = ApprovedAdminFragment.getBitmapFromName("" + orders.get(position).getPhone());
        if (bitmap != null) {
            holder.imageView.setImageBitmap(bitmap);
        }
//        else {
//            Toast.makeText(context, "EE", Toast.LENGTH_SHORT).show();
//        }


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String Date, Time, Address, Name, Price, Tol, Oot, Fos, Adress2, Thereason, Phone, Gender, How_much_time, Birth;
                        Bitmap bitmap;
                        Date = orders.get(holder.getAdapterPosition()).getDate();
                        Time = orders.get(holder.getAdapterPosition()).getTime();
                        Address = orders.get(holder.getAdapterPosition()).getAddress();
                        Name = orders.get(holder.getAdapterPosition()).getName();
                        Price = orders.get(holder.getAdapterPosition()).getPrice();
                        Tol = orders.get(holder.getAdapterPosition()).getTol();
                        Oot = orders.get(holder.getAdapterPosition()).getOot();
                        Fos = orders.get(holder.getAdapterPosition()).getFos();
                        Adress2 = orders.get(holder.getAdapterPosition()).getAdress2();
                        Thereason = orders.get(holder.getAdapterPosition()).getThereason();
                        Phone = orders.get(holder.getAdapterPosition()).getPhone();
                        Gender = orders.get(holder.getAdapterPosition()).getGender();
                        How_much_time = orders.get(holder.getAdapterPosition()).getHow_much_time();
                        Birth = orders.get(holder.getAdapterPosition()).getBirth();
                        bitmap = ApprovedAdminFragment.getBitmapFromName("" + Phone);
                        listener.onClick(Date, Time, Address, Adress2, Name, Price, Tol, Oot, Fos, Thereason, Phone, Gender, How_much_time, Birth, bitmap);
                        //Toast.makeText(context, ", "+Date , Toast.LENGTH_LONG).show();


                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, time, address;
        ImageView imageView;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.book_date);
            time = itemView.findViewById(R.id.book_time_txt);
            address = itemView.findViewById(R.id.Address);
            imageView = itemView.findViewById(R.id.imageView);
            mainLayout = itemView.findViewById(R.id.mainLayout2);
        }
    }
}


