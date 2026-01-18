package com.mycompany.qlst.dao;

import com.mycompany.qlst.Helpers.DatabaseConnector;
import com.mycompany.qlst.model.KhuyenMai;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiDAO {
    
    // Lấy tất cả khuyến mãi
    public List<KhuyenMai> getAllKhuyenMai() {
        List<KhuyenMai> list = new ArrayList<>();
        String sql = "SELECT maKhM, tenKhM, phanTramGiam, ngayHieuLuc, ngayKetThuc FROM khuyenmai";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai(
                    rs.getInt("maKhM"),
                    rs.getString("tenKhM"),
                    rs.getInt("phanTramGiam"),
                    rs.getDate("ngayHieuLuc"),
                    rs.getDate("ngayKetThuc")
                );
                list.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy khuyến mãi theo mã
    public KhuyenMai getKhuyenMaiById(int maKhM) {
        String sql = "SELECT maKhM, tenKhM, phanTramGiam, ngayHieuLuc, ngayKetThuc FROM khuyenmai WHERE maKhM = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maKhM);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new KhuyenMai(
                    rs.getInt("maKhM"),
                    rs.getString("tenKhM"),
                    rs.getInt("phanTramGiam"),
                    rs.getDate("ngayHieuLuc"),
                    rs.getDate("ngayKetThuc")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Lấy khuyến mãi đang hiệu lực
    public List<KhuyenMai> getKhuyenMaiHieuLuc() {
        List<KhuyenMai> list = new ArrayList<>();
        String sql = "SELECT maKhM, tenKhM, phanTramGiam, ngayHieuLuc, ngayKetThuc " +
                     "FROM khuyenmai " +
                     "WHERE CURDATE() BETWEEN ngayHieuLuc AND ngayKetThuc " +
                     "ORDER BY maKhM DESC";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai(
                    rs.getInt("maKhM"),
                    rs.getString("tenKhM"),
                    rs.getInt("phanTramGiam"),
                    rs.getDate("ngayHieuLuc"),
                    rs.getDate("ngayKetThuc")
                );
                list.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy khuyến mãi đã hết hạn
    public List<KhuyenMai> getKhuyenMaiHetHan() {
        List<KhuyenMai> list = new ArrayList<>();
        String sql = "SELECT maKhM, tenKhM, phanTramGiam, ngayHieuLuc, ngayKetThuc " +
                     "FROM khuyenmai " +
                     "WHERE CURDATE() > ngayKetThuc " +
                     "ORDER BY maKhM DESC";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai(
                    rs.getInt("maKhM"),
                    rs.getString("tenKhM"),
                    rs.getInt("phanTramGiam"),
                    rs.getDate("ngayHieuLuc"),
                    rs.getDate("ngayKetThuc")
                );
                list.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm khuyến mãi
    public int themKhuyenMai(KhuyenMai km) {
        String sql = "INSERT INTO khuyenmai (tenKhM, phanTramGiam, ngayHieuLuc, ngayKetThuc) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, km.getTenKhM());
            pstmt.setInt(2, km.getPhanTramGiam());
            pstmt.setDate(3, km.getNgayHieuLuc());
            pstmt.setDate(4, km.getNgayKetThuc());
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            int key = -1;
            while (rs.next()) {
                key = rs.getInt(1);
                System.out.println(String.format("Key = %s", key));
            }
            rs.close();
            return key;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    // Sửa khuyến mãi
    public boolean suaKhuyenMai(KhuyenMai km) {
        String sql = "UPDATE khuyenmai SET tenKhM=?, phanTramGiam=?, ngayHieuLuc=?, ngayKetThuc=? WHERE maKhM=?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, km.getTenKhM());
            pstmt.setInt(2, km.getPhanTramGiam());
            pstmt.setDate(3, km.getNgayHieuLuc());
            pstmt.setDate(4, km.getNgayKetThuc());
            pstmt.setInt(5, km.getMaKhM());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa khuyến mãi
    public boolean xoaKhuyenMai(int maKhM) {
        String sql = "DELETE FROM khuyenmai WHERE maKhM = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maKhM);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Tìm kiếm khuyến mãi theo tên
    public List<KhuyenMai> timKiemKhuyenMai(String keyword) {
        List<KhuyenMai> list = new ArrayList<>();
        String sql = "SELECT maKhM, tenKhM, phanTramGiam, ngayHieuLuc, ngayKetThuc " +
                     "FROM khuyenmai WHERE tenKhM LIKE ? ORDER BY maKhM DESC";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai(
                    rs.getInt("maKhM"),
                    rs.getString("tenKhM"),
                    rs.getInt("phanTramGiam"),
                    rs.getDate("ngayHieuLuc"),
                    rs.getDate("ngayKetThuc")
                );
                list.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}