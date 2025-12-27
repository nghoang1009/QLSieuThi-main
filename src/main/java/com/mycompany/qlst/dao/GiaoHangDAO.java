package com.mycompany.qlst.dao;

import com.mycompany.qlst.database.DatabaseConnection;
import com.mycompany.qlst.model.GiaoHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiaoHangDAO {
    
    // Lấy tất cả đơn giao hàng
    public List<GiaoHang> getAllGiaoHang() {
        List<GiaoHang> list = new ArrayList<>();
        String sql = "SELECT gh.maGiaoHang, gh.maKH, kh.ten, gh.ngayTao, gh.tinhTrang " +
                     "FROM giaoHang gh " +
                     "INNER JOIN khachHang kh ON gh.maKH = kh.maKH " +
                     "ORDER BY gh.maGiaoHang DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                GiaoHang gh = new GiaoHang(
                    rs.getInt("maGiaoHang"),
                    rs.getInt("maKH"),
                    rs.getDate("ngayTao"),
                    rs.getString("tinhTrang")
                );
                gh.setTenKH(rs.getString("ten"));
                list.add(gh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy giao hàng theo mã
    public GiaoHang getGiaoHangById(int maGiaoHang) {
        String sql = "SELECT gh.maGiaoHang, gh.maKH, kh.ten, gh.ngayTao, gh.tinhTrang " +
                     "FROM giaoHang gh " +
                     "INNER JOIN khachHang kh ON gh.maKH = kh.maKH " +
                     "WHERE gh.maGiaoHang = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maGiaoHang);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                GiaoHang gh = new GiaoHang(
                    rs.getInt("maGiaoHang"),
                    rs.getInt("maKH"),
                    rs.getDate("ngayTao"),
                    rs.getString("tinhTrang")
                );
                gh.setTenKH(rs.getString("ten"));
                return gh;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Lấy giao hàng theo tình trạng
    public List<GiaoHang> getGiaoHangByTinhTrang(String tinhTrang) {
        List<GiaoHang> list = new ArrayList<>();
        String sql = "SELECT gh.maGiaoHang, gh.maKH, kh.ten, gh.ngayTao, gh.tinhTrang " +
                     "FROM giaoHang gh " +
                     "INNER JOIN khachHang kh ON gh.maKH = kh.maKH " +
                     "WHERE gh.tinhTrang = ? " +
                     "ORDER BY gh.maGiaoHang DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tinhTrang);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                GiaoHang gh = new GiaoHang(
                    rs.getInt("maGiaoHang"),
                    rs.getInt("maKH"),
                    rs.getDate("ngayTao"),
                    rs.getString("tinhTrang")
                );
                gh.setTenKH(rs.getString("ten"));
                list.add(gh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm đơn giao hàng
    public boolean themGiaoHang(GiaoHang gh) {
        String sql = "INSERT INTO giaoHang (maKH, ngayTao, tinhTrang) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, gh.getMaKH());
            pstmt.setDate(2, gh.getNgayTao());
            pstmt.setString(3, gh.getTinhTrang());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Cập nhật tình trạng giao hàng
    public boolean capNhatTinhTrang(int maGiaoHang, String tinhTrang) {
        String sql = "UPDATE giaoHang SET tinhTrang = ? WHERE maGiaoHang = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tinhTrang);
            pstmt.setInt(2, maGiaoHang);
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa đơn giao hàng
    public boolean xoaGiaoHang(int maGiaoHang) {
        String sql = "DELETE FROM giaoHang WHERE maGiaoHang = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maGiaoHang);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Tìm kiếm giao hàng theo tên khách hàng
    public List<GiaoHang> timKiemGiaoHang(String keyword) {
        List<GiaoHang> list = new ArrayList<>();
        String sql = "SELECT gh.maGiaoHang, gh.maKH, kh.ten, gh.ngayTao, gh.tinhTrang " +
                     "FROM giaoHang gh " +
                     "INNER JOIN khachHang kh ON gh.maKH = kh.maKH " +
                     "WHERE kh.ten LIKE ? " +
                     "ORDER BY gh.maGiaoHang DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                GiaoHang gh = new GiaoHang(
                    rs.getInt("maGiaoHang"),
                    rs.getInt("maKH"),
                    rs.getDate("ngayTao"),
                    rs.getString("tinhTrang")
                );
                gh.setTenKH(rs.getString("ten"));
                list.add(gh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Tính tổng tiền đơn giao hàng
    public int tinhTongTien(int maGiaoHang) {
        int tongTien = 0;
        String sql = "SELECT SUM(sp.gia * igh.soLuong) as tongTien " +
                     "FROM item_giaoHang igh " +
                     "INNER JOIN sanPham sp ON igh.maSP = sp.maSP " +
                     "WHERE igh.maGiaoHang = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maGiaoHang);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tongTien = rs.getInt("tongTien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tongTien;
    }
}