package com.mycompany.qlst.dao;

import com.mycompany.qlst.database.DatabaseConnection;
import com.mycompany.qlst.model.DanhMuc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO {
    
    // Lấy tất cả danh mục
    public List<DanhMuc> getAllDanhMuc() {
        List<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT maDM, tenDM FROM danhMuc";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                DanhMuc dm = new DanhMuc(
                    rs.getInt("maDM"),
                    rs.getString("tenDM")
                );
                list.add(dm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy danh mục theo mã
    public DanhMuc getDanhMucById(int maDM) {
        String sql = "SELECT maDM, tenDM FROM danhMuc WHERE maDM = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maDM);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new DanhMuc(
                    rs.getInt("maDM"),
                    rs.getString("tenDM")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Thêm danh mục
    public boolean themDanhMuc(DanhMuc dm) {
        String sql = "INSERT INTO danhMuc (tenDM) VALUES (?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dm.getTenDM());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Sửa danh mục
    public boolean suaDanhMuc(DanhMuc dm) {
        String sql = "UPDATE danhMuc SET tenDM = ? WHERE maDM = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dm.getTenDM());
            pstmt.setInt(2, dm.getMaDM());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa danh mục
    public boolean xoaDanhMuc(int maDM) {
        String sql = "DELETE FROM danhMuc WHERE maDM = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maDM);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Lấy mã danh mục theo tên
    public int getMaDMByTen(String tenDM) {
        String sql = "SELECT maDM FROM danhMuc WHERE tenDM = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tenDM);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("maDM");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Không tìm thấy
    }
}
