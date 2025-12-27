package com.mycompany.qlst.model;

public class ItemGiaoHang {
    private int maItemGiaoHang;
    private int maGiaoHang;
    private int maSP;
    private int soLuong;
    private String tenSP; // Để hiển thị
    private int gia; // Để hiển thị

    // Constructor mặc định
    public ItemGiaoHang() {
    }

    // Constructor đầy đủ
    public ItemGiaoHang(int maItemGiaoHang, int maGiaoHang, int maSP, int soLuong) {
        this.maItemGiaoHang = maItemGiaoHang;
        this.maGiaoHang = maGiaoHang;
        this.maSP = maSP;
        this.soLuong = soLuong;
    }

    // Constructor không có maItemGiaoHang
    public ItemGiaoHang(int maGiaoHang, int maSP, int soLuong) {
        this.maGiaoHang = maGiaoHang;
        this.maSP = maSP;
        this.soLuong = soLuong;
    }

    // Getters và Setters
    public int getMaItemGiaoHang() {
        return maItemGiaoHang;
    }

    public void setMaItemGiaoHang(int maItemGiaoHang) {
        this.maItemGiaoHang = maItemGiaoHang;
    }

    public int getMaGiaoHang() {
        return maGiaoHang;
    }

    public void setMaGiaoHang(int maGiaoHang) {
        this.maGiaoHang = maGiaoHang;
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
        return "ItemGiaoHang{" +
                "maItemGiaoHang=" + maItemGiaoHang +
                ", maGiaoHang=" + maGiaoHang +
                ", maSP=" + maSP +
                ", soLuong=" + soLuong +
                '}';
    }
}