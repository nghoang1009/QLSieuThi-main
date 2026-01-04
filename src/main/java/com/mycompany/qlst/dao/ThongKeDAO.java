package com.mycompany.qlst.dao;

import com.mycompany.qlst.database.DatabaseConnection;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ThongKeDAO {
    
    // Thống kê tổng quan
    public Map<String, Integer> thongKeTongQuan() {
        Map<String, Integer> result = new HashMap<>();
        Connection conn = DatabaseConnection.getConnection();
        
        try {
            // Đếm số sản phẩm
            String sqlSP = "SELECT COUNT(*) as total FROM sanpham";
            Statement stmtSP = conn.createStatement();
            ResultSet rsSP = stmtSP.executeQuery(sqlSP);
            if (rsSP.next()) {
                result.put("soSanPham", rsSP.getInt("total"));
            }
            
            // Đếm số khách hàng
            String sqlKH = "SELECT COUNT(*) as total FROM khachhang";
            Statement stmtKH = conn.createStatement();
            ResultSet rsKH = stmtKH.executeQuery(sqlKH);
            if (rsKH.next()) {
                result.put("soKhachHang", rsKH.getInt("total"));
            }
            
            // Đếm số hóa đơn
            String sqlHD = "SELECT COUNT(*) as total FROM hoadon";
            Statement stmtHD = conn.createStatement();
            ResultSet rsHD = stmtHD.executeQuery(sqlHD);
            if (rsHD.next()) {
                result.put("soHoaDon", rsHD.getInt("total"));
            }
            
            // Đếm số nhân viên
            String sqlNV = "SELECT COUNT(*) as total FROM nhanvien";
            Statement stmtNV = conn.createStatement();
            ResultSet rsNV = stmtNV.executeQuery(sqlNV);
            if (rsNV.next()) {
                result.put("soNhanVien", rsNV.getInt("total"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    // Thống kê doanh thu theo tháng
    public Map<String, Long> thongKeDoanhThuTheoThang(int nam) {
        Map<String, Long> result = new HashMap<>();
        String sql = "SELECT MONTH(ngayThanhToan) as thang, SUM(ih.gia * ih.soLuong) as doanhThu " +
                     "FROM hoadon hd " +
                     "INNER JOIN item_hoadon ih ON hd.maHoaDon = ih.maHoaDon " +
                     "WHERE YEAR(ngayThanhToan) = ? " +
                     "GROUP BY MONTH(ngayThanhToan) " +
                     "ORDER BY thang";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, nam);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String thang = "Tháng " + rs.getInt("thang");
                result.put(thang, rs.getLong("doanhThu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    // Thống kê sản phẩm bán chạy
    public Map<String, Integer> thongKeSanPhamBanChay(int limit) {
        Map<String, Integer> result = new HashMap<>();
        String sql = "SELECT ih.tenSP, SUM(ih.soLuong) as tongSoLuong " +
                     "FROM item_hoadon ih " +
                     "GROUP BY ih.tenSP " +
                     "ORDER BY tongSoLuong DESC " +
                     "LIMIT ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                result.put(rs.getString("tenSP"), rs.getInt("tongSoLuong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    // Thống kê sản phẩm tồn kho
    public Map<String, Integer> thongKeTonKho() {
        Map<String, Integer> result = new HashMap<>();
        String sql = "SELECT tenSP, soLuong FROM sanpham ORDER BY soLuong ASC LIMIT 10";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                result.put(rs.getString("tenSP"), rs.getInt("soLuong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    // Thống kê theo danh mục
    public Map<String, Integer> thongKeTheoDanhMuc() {
        Map<String, Integer> result = new HashMap<>();
        String sql = "SELECT dm.tenDM, COUNT(sp.maSP) as soLuong " +
                     "FROM danhmuc dm " +
                     "LEFT JOIN sanpham sp ON dm.maDM = sp.maDM " +
                     "GROUP BY dm.maDM, dm.tenDM " +
                     "ORDER BY soLuong DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                result.put(rs.getString("tenDM"), rs.getInt("soLuong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    // Thống kê doanh thu theo nhân viên
    public Map<String, Long> thongKeDoanhThuNhanVien() {
        Map<String, Long> result = new HashMap<>();
        String sql = "SELECT nv.ten, SUM(ih.gia * ih.soLuong) as doanhThu " +
                     "FROM hoadon hd " +
                     "INNER JOIN nhanvien nv ON hd.maNV = nv.maNV " +
                     "INNER JOIN item_hoadon ih ON hd.maHoaDon = ih.maHoaDon " +
                     "GROUP BY nv.maNV, nv.ten " +
                     "ORDER BY doanhThu DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                result.put(rs.getString("ten"), rs.getLong("doanhThu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    // Tổng doanh thu
    public long tongDoanhThu() {
        String sql = "SELECT SUM(gia * soLuong) as tongDoanhThu FROM item_hoadon";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getLong("tongDoanhThu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    // Doanh thu theo khoảng thời gian
    public long doanhThuTheoKhoangThoiGian(Date tuNgay, Date denNgay) {
        String sql = "SELECT SUM(ih.gia * ih.soLuong) as doanhThu " +
                     "FROM hoadon hd " +
                     "INNER JOIN item_hoadon ih ON hd.maHoaDon = ih.maHoaDon " +
                     "WHERE hd.ngayThanhToan BETWEEN ? AND ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, tuNgay);
            pstmt.setDate(2, denNgay);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getLong("doanhThu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
}