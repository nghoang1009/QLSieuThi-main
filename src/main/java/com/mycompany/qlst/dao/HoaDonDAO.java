package com.mycompany.qlst.dao;

import com.mycompany.qlst.Helpers.DatabaseConnector;
import com.mycompany.qlst.model.HoaDon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    
    // Lấy tất cả hóa đơn
    public List<HoaDon> getAllHoaDon() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.maHoaDon, hd.maNV, nv.ten, hd.ngayThanhToan " +
                     "FROM hoadon hd " +
                     "INNER JOIN nhanvien nv ON hd.maNV = nv.maNV " +
                     "ORDER BY hd.maHoaDon DESC";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("maHoaDon"),
                    rs.getInt("maNV"),
                    rs.getDate("ngayThanhToan")
                );
                hd.setTenNV(rs.getString("ten"));
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy hóa đơn theo mã
    public HoaDon getHoaDonById(int maHoaDon) {
        String sql = "SELECT hd.maHoaDon, hd.maNV, nv.ten, hd.ngayThanhToan " +
                     "FROM hoadon hd " +
                     "INNER JOIN nhanvien nv ON hd.maNV = nv.maNV " +
                     "WHERE hd.maHoaDon = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maHoaDon);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("maHoaDon"),
                    rs.getInt("maNV"),
                    rs.getDate("ngayThanhToan")
                );
                hd.setTenNV(rs.getString("ten"));
                return hd;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Lấy hóa đơn theo nhân viên
    public List<HoaDon> getHoaDonByNhanVien(int maNV) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.maHoaDon, hd.maNV, nv.ten, hd.ngayThanhToan " +
                     "FROM hoadon hd " +
                     "INNER JOIN nhanvien nv ON hd.maNV = nv.maNV " +
                     "WHERE hd.maNV = ? " +
                     "ORDER BY hd.maHoaDon DESC";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maNV);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("maHoaDon"),
                    rs.getInt("maNV"),
                    rs.getDate("ngayThanhToan")
                );
                hd.setTenNV(rs.getString("ten"));
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm hóa đơn
    public boolean themHoaDon(HoaDon hd) {
        String sql = "INSERT INTO hoadon (maNV, ngayThanhToan) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, hd.getMaNV());
            pstmt.setDate(2, hd.getNgayThanhToan());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa hóa đơn
    public boolean xoaHoaDon(int maHoaDon) {
        String sql = "DELETE FROM hoadon WHERE maHoaDon = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maHoaDon);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Tìm kiếm hóa đơn theo mã hoặc tên nhân viên
    public List<HoaDon> timKiemHoaDon(String keyword) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.maHoaDon, hd.maNV, nv.ten, hd.ngayThanhToan " +
                     "FROM hoadon hd " +
                     "INNER JOIN nhanvien nv ON hd.maNV = nv.maNV " +
                     "WHERE hd.maHoaDon LIKE ? OR nv.ten LIKE ? " +
                     "ORDER BY hd.maHoaDon DESC";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("maHoaDon"),
                    rs.getInt("maNV"),
                    rs.getDate("ngayThanhToan")
                );
                hd.setTenNV(rs.getString("ten"));
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Tính tổng tiền hóa đơn
    public int tinhTongTien(int maHoaDon) {
        int tongTien = 0;
        String sql = "SELECT SUM(gia * soLuong) as tongTien " +
             "FROM item_hoadon " +
             "WHERE maHoaDon = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maHoaDon);
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