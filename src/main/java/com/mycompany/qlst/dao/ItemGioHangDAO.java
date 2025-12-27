package com.mycompany.qlst.dao;

import com.mycompany.qlst.database.DatabaseConnection;
import com.mycompany.qlst.model.ItemGioHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemGioHangDAO {
    
    // Lấy tất cả items trong giỏ hàng
    public List<ItemGioHang> getItemsByGioHang(int maGioHang) {
        List<ItemGioHang> list = new ArrayList<>();
        String sql = "SELECT igh.maItemGioHang, igh.maSP, sp.tenSP, sp.gia, igh.soLuong " +
                     "FROM item_gioHang igh " +
                     "INNER JOIN sanPham sp ON igh.maSP = sp.maSP " +
                     "WHERE igh.maGioHang = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maGioHang);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ItemGioHang item = new ItemGioHang(
                    rs.getInt("maItemGioHang"),
                    maGioHang,
                    rs.getInt("maSP"),
                    rs.getInt("soLuong")
                );
                item.setTenSP(rs.getString("tenSP"));
                item.setGia(rs.getInt("gia"));
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm item vào giỏ hàng
    public boolean themItem(ItemGioHang item) {
        String sql = "INSERT INTO item_gioHang (maGioHang, maSP, soLuong) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, item.getMaGioHang());
            pstmt.setInt(2, item.getMaSP());
            pstmt.setInt(3, item.getSoLuong());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Cập nhật số lượng item
    public boolean capNhatSoLuong(int maItemGioHang, int soLuongMoi) {
        String sql = "UPDATE item_gioHang SET soLuong = ? WHERE maItemGioHang = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, soLuongMoi);
            pstmt.setInt(2, maItemGioHang);
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa item khỏi giỏ hàng
    public boolean xoaItem(int maItemGioHang) {
        String sql = "DELETE FROM item_gioHang WHERE maItemGioHang = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maItemGioHang);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
    public boolean kiemTraTonTai(int maGioHang, int maSP) {
        String sql = "SELECT COUNT(*) FROM item_gioHang WHERE maGioHang = ? AND maSP = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maGioHang);
            pstmt.setInt(2, maSP);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}