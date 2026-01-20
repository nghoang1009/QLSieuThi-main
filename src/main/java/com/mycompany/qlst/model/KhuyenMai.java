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
    public Integer getMaKhM() {
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

    public Integer getPhanTramGiam() {
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

    public Object get(int index) {
        switch (index) {
            case 0: return maKhM;
            case 1: return tenKhM;
            case 2: return phanTramGiam;
            case 3: return ngayHieuLuc;
            case 4: return ngayKetThuc;
        
            default:
                return null;
        }
    }

    public void setAll(KhuyenMai km) {
        maKhM = km.getMaKhM();
        tenKhM = km.getTenKhM();
        phanTramGiam = km.getPhanTramGiam();
        ngayHieuLuc = km.getNgayHieuLuc();
        ngayKetThuc = km.getNgayKetThuc();
    }

    @Override
    public String toString() {
        return tenKhM;
    }
}