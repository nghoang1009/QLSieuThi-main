package com.mycompany.qlst.model;

public class KhachHang {
    private int maKH;
    private String ten;
    private String sdt;
    private String diaChi;
    private TaiKhoan taiKhoan;

    // Constructor mặc định
    public KhachHang() {
    }

    // Constructor đầy đủ
    public KhachHang(int maKH, String ten, String sdt, String diaChi) {
        this.maKH = maKH;
        this.ten = ten;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    // Constructor không có maKH (dùng khi thêm mới)
    public KhachHang(String ten, String sdt, String diaChi) {
        this.ten = ten;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    // Constructor với tài khoản
    public KhachHang(int maKH, String ten, String sdt, String diaChi, TaiKhoan taiKhoan) {
        this.maKH = maKH;
        this.ten = ten;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.taiKhoan = taiKhoan;
    }

    // Getters và Setters
    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKH=" + maKH +
                ", ten='" + ten + '\'' +
                ", sdt='" + sdt + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}