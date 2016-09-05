package com.example.nhut.model;

/**
 * Created by Nhut on 6/8/2016.
 */
public class Contact {
    private String ten;
    private String soPhone;

    public Contact() {

    }

    public Contact(String ten, String soPhone) {
        this.ten = ten;
        this.soPhone = soPhone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "ten='" + ten + '\'' +
                ", soPhone='" + soPhone + '\'' +
                '}';
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSoPhone() {
        return soPhone;
    }

    public void setSoPhone(String soPhone) {
        this.soPhone = soPhone;
    }
}
