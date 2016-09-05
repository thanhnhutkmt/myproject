package com.example.nhut.model;

import java.io.Serializable;

/**
 * Created by Nhut on 6/7/2016.
 */
public class SinhVien implements Serializable {
    private int ma;
    private String ten;

    public SinhVien() {

    }

    public SinhVien(int ma, String ten) {
        this.ma = ma;
        this.ten = ten;
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "ma=" + ma +
                ", ten='" + ten + '\'' +
                '}';
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
