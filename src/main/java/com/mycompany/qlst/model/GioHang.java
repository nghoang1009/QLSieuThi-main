package com.mycompany.qlst.model;

import java.sql.Date;

public class GioHang {
    private int maGioHang;
    private int maKH;
    private Date ngayTao;
    private String tenKH; // Để hiển thị

    // Constructor mặc định
    public GioHang() {
    }

    // Constructor đầy đủ
    public GioHang(int maGioHang, int maKH, Date ngayTao) {
        this.maGioHang = maGioHang;
        this.maKH = maKH;
        this.ngayTao = ngayTao;
    }

    // Constructor không có maGioHang
    public GioHang(int maKH, Date ngayTao) {
        this.maKH = maKH;
        this.ngayTao = ngayTao;
    }

    // Getters và Setters
    public int getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(int maGioHang) {
        this.maGioHang = maGioHang;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    @Override
    public String toString() {
        return "GioHang{" +
                "maGioHang=" + maGioHang +
                ", maKH=" + maKH +
                ", ngayTao=" + ngayTao +
                '}';
    }
}