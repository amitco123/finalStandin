package com.example.finalstandin;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface SelectListener {
    void onItemLongClick(String date,String id);
    void onClick(String date, String time, String address, String address2,String name, String money, String tol, String oot, String fos, String thereason, String phone, String gender, String how_much_time, String birth , Bitmap image);
}
