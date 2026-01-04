package com.mycompany.qlst.model;

import java.sql.Date;

public class KhuyenMai {
    private int maKhM;
    private String tenKhM;
    private int phanTramGiam;
    private Date ngayHieuLuc;
    private Date ngayKetThuc;

    // Constructor mặc định
    public KhuyenMai() {
    }

    // Constructor đầy đủ
    public KhuyenMai(int maKhM, String tenKhM, int phanTramGiam, Date ngayHieuLuc, Date ngayKetThuc) {
        this.maKhM = maKhM;
        this.tenKhM = tenKhM;
        this.phanTramGiam = phanTramGiam;
        this.ngayHieuLuc = ngayHieuLuc;
        this.ngayKetThuc = ngayKetThuc;
    }

    // Constructor không có maKhM
    public KhuyenMai(String tenKhM, int phanTramGiam, Date ngayHieuLuc, Date ngayKetThuc) {
        this.tenKhM = tenKhM;
        this.phanTramGiam = phanTramGiam;
        this.ngayHieuLuc = ngayHieuLuc;
        this.ngayKetThuc = ngayKetThuc;
    }

    // Getters và Setters
    public int getMaKhM() {
        return maKhM;
    }

    public void setMaKhM(int maKhM) {
        this.maKhM = maKhM;
    }

    public String getTenKhM() {
        return tenKhM;
    }

    public void setTenKhM(String tenKhM) {
        this.tenKhM = tenKhM;
    }

    public int getPhanTramGiam() {
        return phanTramGiam;
    }

    public void setPhanTramGiam(int phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }

    public Date getNgayHieuLuc() {
        return ngayHieuLuc;
    }

    public void setNgayHieuLuc(Date ngayHieuLuc) {
        this.ngayHieuLuc = ngayHieuLuc;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    @Override
    public String toString() {
        return "KhuyenMai{" +
                "maKhM=" + maKhM +
                ", tenKhM='" + tenKhM + '\'' +
                ", phanTramGiam=" + phanTramGiam +
                ", ngayHieuLuc=" + ngayHieuLuc +
                ", ngayKetThuc=" + ngayKetThuc +
                '}';
    }
}