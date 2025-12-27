package com.mycompany.qlst.model;

import java.sql.Date;

public class HoaDon {
    private int maHoaDon;
    private int maNV;
    private Date ngayThanhToan;
    private String tenNV; // Để hiển thị

    // Constructor mặc định
    public HoaDon() {
    }

    // Constructor đầy đủ
    public HoaDon(int maHoaDon, int maNV, Date ngayThanhToan) {
        this.maHoaDon = maHoaDon;
        this.maNV = maNV;
        this.ngayThanhToan = ngayThanhToan;
    }

    // Constructor không có maHoaDon
    public HoaDon(int maNV, Date ngayThanhToan) {
        this.maNV = maNV;
        this.ngayThanhToan = ngayThanhToan;
    }

    // Getters và Setters
    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon=" + maHoaDon +
                ", maNV=" + maNV +
                ", ngayThanhToan=" + ngayThanhToan +
                '}';
    }
}