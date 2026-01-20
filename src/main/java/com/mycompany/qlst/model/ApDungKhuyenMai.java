package com.mycompany.qlst.model;


public class ApDungKhuyenMai {
    private int maSP, maKhM;
    private String tenSP, tenKhM;

    // Constructor mặc định
    public ApDungKhuyenMai() {
    }

    // Constructor đầy đủ
    public ApDungKhuyenMai(int maKhM, String tenKhM, int maSP, String tenSP) {
        this.maKhM = maKhM;
        this.tenKhM = tenKhM;
        this.maSP = maSP;
        this.tenSP = tenSP;
    }

    // Constructor không có mã
    public ApDungKhuyenMai(String tenKhM, String tenSP) {
        this.tenKhM = tenKhM;
        this.tenSP = tenSP;
    }
}
