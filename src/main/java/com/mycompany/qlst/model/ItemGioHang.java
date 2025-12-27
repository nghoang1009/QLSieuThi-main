package com.mycompany.qlst.model;

public class ItemGioHang {
    private int maItemGioHang;
    private int maGioHang;
    private int maSP;
    private int soLuong;
    private String tenSP; // Để hiển thị
    private int gia; // Để hiển thị

    // Constructor mặc định
    public ItemGioHang() {
    }

    // Constructor đầy đủ
    public ItemGioHang(int maItemGioHang, int maGioHang, int maSP, int soLuong) {
        this.maItemGioHang = maItemGioHang;
        this.maGioHang = maGioHang;
        this.maSP = maSP;
        this.soLuong = soLuong;
    }

    // Constructor không có maItemGioHang
    public ItemGioHang(int maGioHang, int maSP, int soLuong) {
        this.maGioHang = maGioHang;
        this.maSP = maSP;
        this.soLuong = soLuong;
    }

    // Getters và Setters
    public int getMaItemGioHang() {
        return maItemGioHang;
    }

    public void setMaItemGioHang(int maItemGioHang) {
        this.maItemGioHang = maItemGioHang;
    }

    public int getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(int maGioHang) {
        this.maGioHang = maGioHang;
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
        return "ItemGioHang{" +
                "maItemGioHang=" + maItemGioHang +
                ", maGioHang=" + maGioHang +
                ", maSP=" + maSP +
                ", soLuong=" + soLuong +
                '}';
    }
}