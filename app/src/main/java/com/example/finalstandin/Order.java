package com.example.finalstandin;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class Order {
    private String address, adress2, name, date, tol, oot, fos, thereason, phone, gender, time, how_much_time, birth, price;
    public Bitmap bitmap;

    public Order(String address, String adress2, String name, String date, String tol, String oot, String fos, String thereason, String phone, String gender, String time, String how_much_time, String birth, String price, Bitmap bitmap) {
        this.address = address;
        this.adress2 = adress2;
        this.name = name;
        this.date = date;
        this.tol = tol;
        this.oot = oot;
        this.fos = fos;
        this.thereason = thereason;
        this.phone = phone;
        this.gender = gender;
        this.time = time;
        this.how_much_time = how_much_time;
        this.birth = birth;
        this.price = price;
        this.bitmap = bitmap;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdress2() {
        return adress2;
    }

    public void setAdress2(String adress2) {
        this.adress2 = adress2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTol() {
        return tol;
    }

    public void setTol(String tol) {
        this.tol = tol;
    }

    public String getOot() {
        return oot;
    }

    public void setOot(String oot) {
        this.oot = oot;
    }

    public String getFos() {
        return fos;
    }

    public void setFos(String fos) {
        this.fos = fos;
    }

    public String getThereason() {
        return thereason;
    }

    public void setThereason(String thereason) {
        this.thereason = thereason;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHow_much_time() {
        return how_much_time;
    }

    public void setHow_much_time(String how_much_time) {
        this.how_much_time = how_much_time;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String toString() {
        return "" + this.address;
    }
}
