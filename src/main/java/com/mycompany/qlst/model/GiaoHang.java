package com.mycompany.qlst.model;

import java.sql.Date;

public class GiaoHang {
    private int maGiaoHang;
    private int maKH;
    private Date ngayTao;
    private String tinhTrang;
    private String tenKH; // Để hiển thị

    // Constructor mặc định
    public GiaoHang() {
    }

    // Constructor đầy đủ
    public GiaoHang(int maGiaoHang, int maKH, Date ngayTao, String tinhTrang) {
        this.maGiaoHang = maGiaoHang;
        this.maKH = maKH;
        this.ngayTao = ngayTao;
        this.tinhTrang = tinhTrang;
    }

    // Constructor không có maGiaoHang
    public GiaoHang(int maKH, Date ngayTao, String tinhTrang) {
        this.maKH = maKH;
        this.ngayTao = ngayTao;
        this.tinhTrang = tinhTrang;
    }

    // Getters và Setters
    public int getMaGiaoHang() {
        return maGiaoHang;
    }

    public void setMaGiaoHang(int maGiaoHang) {
        this.maGiaoHang = maGiaoHang;
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

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    @Override
    public String toString() {
        return "GiaoHang{" +
                "maGiaoHang=" + maGiaoHang +
                ", maKH=" + maKH +
                ", ngayTao=" + ngayTao +
                ", tinhTrang='" + tinhTrang + '\'' +
                '}';
    }
}