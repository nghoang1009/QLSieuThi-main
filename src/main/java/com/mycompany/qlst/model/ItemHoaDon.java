package com.mycompany.qlst.model;

public class ItemHoaDon {
    private int maItemHoaDon;
    private int maHoaDon;
    private int maSP;
    private int soLuong;
    private String tenSP; // Để hiển thị
    private int gia; // Để hiển thị

    // Constructor mặc định
    public ItemHoaDon() {
    }

    // Constructor đầy đủ
    public ItemHoaDon(int maItemHoaDon, int maHoaDon, int maSP, int soLuong) {
        this.maItemHoaDon = maItemHoaDon;
        this.maHoaDon = maHoaDon;
        this.maSP = maSP;
        this.soLuong = soLuong;
    }

    // Constructor không có maItemHoaDon
    public ItemHoaDon(int maHoaDon, int maSP, int soLuong) {
        this.maHoaDon = maHoaDon;
        this.maSP = maSP;
        this.soLuong = soLuong;
    }

    // Getters và Setters
    public int getMaItemHoaDon() {
        return maItemHoaDon;
    }

    public void setMaItemHoaDon(int maItemHoaDon) {
        this.maItemHoaDon = maItemHoaDon;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
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

    @Override
    public String toString() {
        return "ItemHoaDon{" +
                "maItemHoaDon=" + maItemHoaDon +
                ", maHoaDon=" + maHoaDon +
                ", maSP=" + maSP +
                ", soLuong=" + soLuong +
                '}';
    }
}