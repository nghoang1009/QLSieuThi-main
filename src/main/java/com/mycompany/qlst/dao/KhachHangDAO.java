package com.mycompany.qlst.dao;

import com.mycompany.qlst.database.DatabaseConnection;
import com.mycompany.qlst.model.KhachHang;
import com.mycompany.qlst.model.TaiKhoan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    private TaiKhoanDAO taiKhoanDAO;
    
    public KhachHangDAO() {
        taiKhoanDAO = new TaiKhoanDAO();
    }
    
    // Lấy tất cả khách hàng
    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT maKH, ten, sdt, diachi FROM khachhang";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                KhachHang kh = new KhachHang(
                    rs.getInt("maKH"),
                    rs.getString("ten"),
                    rs.getString("sdt"),
                    rs.getString("diachi")
                );
                list.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy khách hàng theo mã (bao gồm thông tin tài khoản)
    public KhachHang getKhachHangById(int maKH) {
        String sql = "SELECT maKH, ten, sdt, diachi FROM khachhang WHERE maKH = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maKH);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                KhachHang kh = new KhachHang(
                    rs.getInt("maKH"),
                    rs.getString("ten"),
                    rs.getString("sdt"),
                    rs.getString("diachi")
                );
                
                // Lấy thông tin tài khoản
                TaiKhoan tk = taiKhoanDAO.getTaiKhoanById(maKH);
                kh.setTaiKhoan(tk);
                
                return kh;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Lấy khách hàng theo khu vực
    public List<KhachHang> getKhachHangByKhuVuc(String khuVuc) {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT maKH, ten, sdt, diachi FROM khachhang WHERE diachi LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + khuVuc + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                KhachHang kh = new KhachHang(
                    rs.getInt("maKH"),
                    rs.getString("ten"),
                    rs.getString("sdt"),
                    rs.getString("diachi")
                );
                list.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm khách hàng (bao gồm tạo tài khoản)
    public boolean themKhachHang(KhachHang kh, TaiKhoan tk) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Thêm tài khoản trước
            int maTK = taiKhoanDAO.themTaiKhoan(tk);
            if (maTK == -1) {
                conn.rollback();
                return false;
            }
            
            // Thêm khách hàng với maTK vừa tạo
            String sql = "INSERT INTO khachhang (maKH, ten, sdt, diachi) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, maTK);
                pstmt.setString(2, kh.getTen());
                pstmt.setString(3, kh.getSdt());
                pstmt.setString(4, kh.getDiaChi());
                
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
    
    // Sửa khách hàng (bao gồm cập nhật tài khoản)
    public boolean suaKhachHang(KhachHang kh, TaiKhoan tk) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Update tài khoản
            tk.setMaTK(kh.getMaKH());
            if (!taiKhoanDAO.suaTaiKhoan(tk)) {
                conn.rollback();
                return false;
            }
            
            // Update khách hàng
            String sql = "UPDATE khachhang SET ten=?, sdt=?, diachi=? WHERE maKH=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, kh.getTen());
                pstmt.setString(2, kh.getSdt());
                pstmt.setString(3, kh.getDiaChi());
                pstmt.setInt(4, kh.getMaKH());
                
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
    
    // Xóa khách hàng (sẽ tự động xóa tài khoản do CASCADE)
    public boolean xoaKhachHang(int maKH) {
        return taiKhoanDAO.xoaTaiKhoan(maKH);
    }
    
    // Tìm kiếm khách hàng theo tên hoặc SĐT
    public List<KhachHang> timKiemKhachHang(String keyword) {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT maKH, ten, sdt, diachi FROM khachhang WHERE ten LIKE ? OR sdt LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                KhachHang kh = new KhachHang(
                    rs.getInt("maKH"),
                    rs.getString("ten"),
                    rs.getString("sdt"),
                    rs.getString("diachi")
                );
                list.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
