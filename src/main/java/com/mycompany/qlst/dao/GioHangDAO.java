package com.mycompany.qlst.dao;

import com.mycompany.qlst.Helpers.DatabaseConnector;
import com.mycompany.qlst.model.GioHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GioHangDAO {
    
    // Lấy tất cả giỏ hàng
    public List<GioHang> getAllGioHang() {
        List<GioHang> list = new ArrayList<>();
        String sql = "SELECT gh.maGioHang, gh.maKH, kh.ten, gh.ngayTao " +
                     "FROM giohang gh " +
                     "INNER JOIN khachhang kh ON gh.maKH = kh.maKH " +
                     "ORDER BY gh.maGioHang DESC";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                GioHang gh = new GioHang(
                    rs.getInt("maGioHang"),
                    rs.getInt("maKH"),
                    rs.getDate("ngayTao")
                );
                gh.setTenKH(rs.getString("ten"));
                list.add(gh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy giỏ hàng theo mã
    public GioHang getGioHangById(int maGioHang) {
        String sql = "SELECT gh.maGioHang, gh.maKH, kh.ten, gh.ngayTao " +
                     "FROM giohang gh " +
                     "INNER JOIN khachhang kh ON gh.maKH = kh.maKH " +
                     "WHERE gh.maGioHang = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maGioHang);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                GioHang gh = new GioHang(
                    rs.getInt("maGioHang"),
                    rs.getInt("maKH"),
                    rs.getDate("ngayTao")
                );
                gh.setTenKH(rs.getString("ten"));
                return gh;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Lấy giỏ hàng theo khách hàng
    public List<GioHang> getGioHangByKhachHang(int maKH) {
        List<GioHang> list = new ArrayList<>();
        String sql = "SELECT gh.maGioHang, gh.maKH, kh.ten, gh.ngayTao " +
                     "FROM giohang gh " +
                     "INNER JOIN khachhang kh ON gh.maKH = kh.maKH " +
                     "WHERE gh.maKH = ? " +
                     "ORDER BY gh.maGioHang DESC";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maKH);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                GioHang gh = new GioHang(
                    rs.getInt("maGioHang"),
                    rs.getInt("maKH"),
                    rs.getDate("ngayTao")
                );
                gh.setTenKH(rs.getString("ten"));
                list.add(gh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm giỏ hàng
    public boolean themGioHang(GioHang gh) {
        String sql = "INSERT INTO giohang (maKH, ngayTao) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, gh.getMaKH());
            pstmt.setDate(2, gh.getNgayTao());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa giỏ hàng
    public boolean xoaGioHang(int maGioHang) {
        String sql = "DELETE FROM giohang WHERE maGioHang = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maGioHang);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Tìm kiếm giỏ hàng theo tên khách hàng
    public List<GioHang> timKiemGioHang(String keyword) {
        List<GioHang> list = new ArrayList<>();
        String sql = "SELECT gh.maGioHang, gh.maKH, kh.ten, gh.ngayTao " +
                     "FROM giohang gh " +
                     "INNER JOIN khachhang kh ON gh.maKH = kh.maKH " +
                     "WHERE kh.ten LIKE ? " +
                     "ORDER BY gh.maGioHang DESC";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                GioHang gh = new GioHang(
                    rs.getInt("maGioHang"),
                    rs.getInt("maKH"),
                    rs.getDate("ngayTao")
                );
                gh.setTenKH(rs.getString("ten"));
                list.add(gh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Tính tổng tiền giỏ hàng
    public int tinhTongTien(int maGioHang) {
        int tongTien = 0;
        String sql = "SELECT SUM(sp.gia * igh.soLuong) as tongTien " +
                     "FROM item_giohang igh " +
                     "INNER JOIN sanpham sp ON igh.maSP = sp.maSP " +
                     "WHERE igh.maGioHang = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maGioHang);
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
