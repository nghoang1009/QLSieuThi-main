package com.mycompany.qlst.dao;

import com.mycompany.qlst.Helpers.DatabaseConnector;
import com.mycompany.qlst.model.ItemGiaoHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemGiaoHangDAO {
    
    // Lấy tất cả items trong đơn giao hàng
    public List<ItemGiaoHang> getItemsByGiaoHang(int maGiaoHang) {
        List<ItemGiaoHang> list = new ArrayList<>();
        String sql = "SELECT igh.maItemGiaoHang, igh.maSP, sp.tenSP, sp.gia, igh.soLuong " +
                     "FROM item_giaohang igh " +
                     "INNER JOIN sanpham sp ON igh.maSP = sp.maSP " +
                     "WHERE igh.maGiaoHang = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maGiaoHang);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ItemGiaoHang item = new ItemGiaoHang(
                    rs.getInt("maItemGiaoHang"),
                    maGiaoHang,
                    rs.getInt("maSP"),
                    rs.getInt("soLuong")
                );
                item.setTenSP(rs.getString("tenSP"));
                item.setGia(rs.getInt("gia"));
                list.add(item);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm item vào đơn giao hàng
    public boolean themItem(ItemGiaoHang item) {
        String sql = "INSERT INTO item_giaohang (maGiaoHang, maSP, soLuong) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, item.getMaGiaoHang());
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
    public boolean capNhatSoLuong(int maItemGiaoHang, int soLuongMoi) {
        String sql = "UPDATE item_giaohang SET soLuong = ? WHERE maItemGiaoHang = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, soLuongMoi);
            pstmt.setInt(2, maItemGiaoHang);
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa item khỏi đơn giao hàng
    public boolean xoaItem(int maItemGiaoHang) {
        String sql = "DELETE FROM item_giaohang WHERE maItemGiaoHang = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maItemGiaoHang);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Kiểm tra sản phẩm đã có trong đơn giao hàng chưa
    public boolean kiemTraTonTai(int maGiaoHang, int maSP) {
        String sql = "SELECT COUNT(*) FROM item_giaohang wHERE maGiaoHang = ? AND maSP = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maGiaoHang);
            pstmt.setInt(2, maSP);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
