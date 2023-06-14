package com.example.finalstandin;

import android.graphics.Bitmap;

public class picUser {
    public String phone;
    public Bitmap bitmap;

    public picUser(String phone, Bitmap bitmap) {
        this.phone = phone;
        this.bitmap = bitmap;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
