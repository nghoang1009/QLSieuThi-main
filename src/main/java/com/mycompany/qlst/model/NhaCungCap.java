package com.mycompany.qlst.model;

public class NhaCungCap {
    private int maNCC;
    private String tenNCC;
    private String diaChi;
    private String thanhPho;

    // Constructor mặc định
    public NhaCungCap() {
    }

    // Constructor đầy đủ
    public NhaCungCap(int maNCC, String tenNCC, String diaChi, String thanhPho) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.thanhPho = thanhPho;
    }

    // Constructor không có maNCC
    public NhaCungCap(String tenNCC, String diaChi, String thanhPho) {
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.thanhPho = thanhPho;
    }

    // Getters và Setters
    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(String thanhPho) {
        this.thanhPho = thanhPho;
    }

    @Override
    public String toString() {
        return "NhaCungCap{" +
                "maNCC=" + maNCC +
                ", tenNCC='" + tenNCC + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", thanhPho='" + thanhPho + '\'' +
                '}';
    }
}