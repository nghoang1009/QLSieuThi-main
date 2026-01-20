package com.mycompany.qlst.model;


public class ApDungKhuyenMai {
    private int maSP, maKhM;
    private String tenSP, tenKhM;

    // Constructor mặc định
    public ApDungKhuyenMai() {
    }

    // Constructor đầy đủ
    public ApDungKhuyenMai(int maKhM, String tenKhM, int maSP, String tenSP) {
        this.maKhM = maKhM;
        this.tenKhM = tenKhM;
        this.maSP = maSP;
        this.tenSP = tenSP;
    }

    // Constructor không có mã
    public ApDungKhuyenMai(String tenKhM, String tenSP) {
        this.tenKhM = tenKhM;
        this.tenSP = tenSP;
    }

    public Object get(int index) {
        switch (index) {
            case 0: return maKhM;
            case 1: return tenKhM;
            case 2: return maSP;
            case 3: return tenSP;
            default:return null;
        }
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getMaKhM() {
        return maKhM;
    }

    public void setMaKhM(int maKhM) {
        this.maKhM = maKhM;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getTenKhM() {
        return tenKhM;
    }

    public void setTenKhM(String tenKhM) {
        this.tenKhM = tenKhM;
    }

    @Override
    public String toString() {
        return tenKhM + " - " + tenSP;
    }
}
