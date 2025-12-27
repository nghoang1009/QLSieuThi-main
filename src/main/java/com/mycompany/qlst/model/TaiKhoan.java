package com.mycompany.qlst.model;

public class TaiKhoan {
    private int maTK;
    private String tenTK;
    private String matKhau;

    // Constructor mặc định
    public TaiKhoan() {
    }

    // Constructor đầy đủ
    public TaiKhoan(int maTK, String tenTK, String matKhau) {
        this.maTK = maTK;
        this.tenTK = tenTK;
        this.matKhau = matKhau;
    }

    // Constructor không có maTK (dùng khi thêm mới)
    public TaiKhoan(String tenTK, String matKhau) {
        this.tenTK = tenTK;
        this.matKhau = matKhau;
    }

    // Getters và Setters
    public int getMaTK() {
        return maTK;
    }

    public void setMaTK(int maTK) {
        this.maTK = maTK;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "maTK=" + maTK +
                ", tenTK='" + tenTK + '\'' +
                ", matKhau='" + matKhau + '\'' +
                '}';
    }
}