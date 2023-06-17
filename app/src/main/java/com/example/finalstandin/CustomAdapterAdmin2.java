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

public class CustomAdapterAdmin2 extends RecyclerView.Adapter<CustomAdapterAdmin2.MyViewHolder> {

    private Context context;
    private Activity activity;
    public ArrayList<Order> orders;
    private SelectListener listener;


    CustomAdapterAdmin2(Context context, ArrayList<Order> orders, SelectListener listener, Activity activity) { //ArrayList<String> date, ArrayList<String> time, ArrayList<String> address , ArrayList<String> name , ArrayList<String> money , ArrayList<String> tol , ArrayList<String> oot ,ArrayList<String> fos , ArrayList<String> thereason, ArrayList<String> phone , ArrayList<String> gender , ArrayList<String> how_much_time ,ArrayList<String> birth ,ArrayList<Bitmap> imageView){

        this.context = context;
        this.orders = orders;
        this.listener = listener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row1, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.date1.setText((orders.get(position).getDate()));
        holder.time1.setText((orders.get(position).getTime()));

        holder.address1.setText((orders.get(position).getAddress()));

        holder.thereason1.setText((orders.get(position).getThereason()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String Date = orders.get(holder.getAdapterPosition()).getDate().toString();
                listener.onItemLongClick("" + Date, "0");
                return true;
            }
        });
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }

        });
    }

    @Override
    public int getItemCount() {

        return orders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date1, time1, address1, thereason1;
        ImageView imageView;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time1 = itemView.findViewById(R.id.time_txt);
            date1 = itemView.findViewById(R.id.date_txt);
            thereason1 = itemView.findViewById(R.id.the_reason_txt);
            address1 = itemView.findViewById(R.id.address_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout1);
        }
    }
}







