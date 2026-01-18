package com.mycompany.qlst.dao;

import com.mycompany.qlst.Helpers.DatabaseConnector;
import com.mycompany.qlst.model.NhanVien;
import com.mycompany.qlst.model.TaiKhoan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    private TaiKhoanDAO taiKhoanDAO;
    
    public NhanVienDAO() {
        taiKhoanDAO = new TaiKhoanDAO();
    }
    
    // Lấy tất cả nhân viên
    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT maNV, ten, ngaySinh, gioiTinh, sdt, diaChi FROM nhanvien";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                    rs.getInt("maNV"),
                    rs.getString("ten"),
                    rs.getDate("ngaySinh"),
                    rs.getString("gioiTinh"),
                    rs.getString("sdt"),
                    rs.getString("diaChi")
                );
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy nhân viên theo mã (bao gồm thông tin tài khoản)
    public NhanVien getNhanVienById(int maNV) {
        String sql = "SELECT maNV, ten, ngaySinh, gioiTinh, sdt, diaChi FROM nhanvien WHERE maNV = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maNV);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                NhanVien nv = new NhanVien(
                    rs.getInt("maNV"),
                    rs.getString("ten"),
                    rs.getDate("ngaySinh"),
                    rs.getString("gioiTinh"),
                    rs.getString("sdt"),
                    rs.getString("diaChi")
                );
                
                // Lấy thông tin tài khoản
                TaiKhoan tk = taiKhoanDAO.getTaiKhoanById(maNV);
                nv.setTaiKhoan(tk);
                
                return nv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Lấy nhân viên theo giới tính
    public List<NhanVien> getNhanVienByGioiTinh(String gioiTinh) {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT maNV, ten, ngaySinh, gioiTinh, sdt, diaChi FROM nhanvien WHERE gioiTinh = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, gioiTinh);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                    rs.getInt("maNV"),
                    rs.getString("ten"),
                    rs.getDate("ngaySinh"),
                    rs.getString("gioiTinh"),
                    rs.getString("sdt"),
                    rs.getString("diaChi")
                );
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm nhân viên (bao gồm tạo tài khoản)
    public boolean themNhanVien(NhanVien nv, TaiKhoan tk) {
        Connection conn = null;
        try {
            conn = DatabaseConnector.getConnection();
            conn.setAutoCommit(false);
            
            // Thêm tài khoản trước
            int maTK = taiKhoanDAO.themTaiKhoan(tk);
            if (maTK == -1) {
                conn.rollback();
                return false;
            }
            
            // Thêm nhân viên với maTK vừa tạo
            String sql = "INSERT INTO nhanvien (maNV, ten, ngaySinh, gioiTinh, sdt, diaChi) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, maTK);
                pstmt.setString(2, nv.getTen());
                pstmt.setDate(3, nv.getNgaySinh());
                pstmt.setString(4, nv.getGioiTinh());
                pstmt.setString(5, nv.getSdt());
                pstmt.setString(6, nv.getDiaChi());
                
                int result = pstmt.executeUpdate();
                
                if (result > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Sửa nhân viên (bao gồm cập nhật tài khoản)
    public boolean suaNhanVien(NhanVien nv, TaiKhoan tk) {
        Connection conn = null;
        try {
            conn = DatabaseConnector.getConnection();
            conn.setAutoCommit(false);
            
            // Update tài khoản
            tk.setMaTK(nv.getMaNV());
            if (!taiKhoanDAO.suaTaiKhoan(tk)) {
                conn.rollback();
                return false;
            }
            
            // Update nhân viên
            String sql = "UPDATE nhanvien SET ten=?, ngaySinh=?, gioiTinh=?, sdt=?, diaChi=? WHERE maNV=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nv.getTen());
                pstmt.setDate(2, nv.getNgaySinh());
                pstmt.setString(3, nv.getGioiTinh());
                pstmt.setString(4, nv.getSdt());
                pstmt.setString(5, nv.getDiaChi());
                pstmt.setInt(6, nv.getMaNV());
                
                int result = pstmt.executeUpdate();
                
                if (result > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Xóa nhân viên (sẽ tự động xóa tài khoản do CASCADE)
    public boolean xoaNhanVien(int maNV) {
        return taiKhoanDAO.xoaTaiKhoan(maNV);
    }
    
    // Tìm kiếm nhân viên theo tên
    public List<NhanVien> timKiemNhanVien(String keyword) {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT maNV, ten, ngaySinh, gioiTinh, sdt, diaChi FROM nhanvien WHERE ten LIKE ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                    rs.getInt("maNV"),
                    rs.getString("ten"),
                    rs.getDate("ngaySinh"),
                    rs.getString("gioiTinh"),
                    rs.getString("sdt"),
                    rs.getString("diaChi")
                );
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}