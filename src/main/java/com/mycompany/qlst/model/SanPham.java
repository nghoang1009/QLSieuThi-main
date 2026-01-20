package com.mycompany.qlst.model;

public class SanPham {
    private int maSP;
    private int maDM;
    private String tenSP;
    private int gia;
    private int soLuong;

    // Constructor mặc định
    public SanPham() {
    }

    // Constructor đầy đủ tham số
    public SanPham(int maSP, int maDM, String tenSP, int gia, int soLuong) {
        this.maSP = maSP;
        this.maDM = maDM;
        this.tenSP = tenSP;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    // Constructor không có maSP (dùng khi thêm mới)
    public SanPham(int maDM, String tenSP, int gia, int soLuong) {
        this.maDM = maDM;
        this.tenSP = tenSP;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    // Getters và Setters
    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getMaDM() {
        return maDM;
    }

    public void setMaDM(int maDM) {
        this.maDM = maDM;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return tenSP;
    }
}