package com.mycompany.qlst.model;

public class DanhMuc {
    private int maDM;
    private String tenDM;

    // Constructor mặc định
    public DanhMuc() {
    }

    // Constructor đầy đủ
    public DanhMuc(int maDM, String tenDM) {
        this.maDM = maDM;
        this.tenDM = tenDM;
    }

    // Constructor không có maDM
    public DanhMuc(String tenDM) {
        this.tenDM = tenDM;
    }

    // Getters và Setters
    public int getMaDM() {
        return maDM;
    }

    public void setMaDM(int maDM) {
        this.maDM = maDM;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    @Override
    public String toString() {
        return "DanhMuc{" +
                "maDM=" + maDM +
                ", tenDM='" + tenDM + '\'' +
                '}';
    }
}