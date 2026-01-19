package com.mycompany.qlst.dao;

import com.mycompany.qlst.Helpers.DatabaseConnector;
import com.mycompany.qlst.model.TaiKhoan;
import java.sql.*;

public class TaiKhoanDAO {
    
    // Thêm tài khoản và trả về mã tài khoản vừa tạo
    public int themTaiKhoan(TaiKhoan tk) {
        String sql = "INSERT INTO taiKhoan (tenTK, matKhau) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, tk.getTenTK());
            pstmt.setString(2, tk.getMatKhau());
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    // THÊM METHOD MỚI: Thêm tài khoản với connection từ bên ngoài (cho transaction)
    public int themTaiKhoan(TaiKhoan tk, Connection conn) throws SQLException {
        String sql = "INSERT INTO taiKhoan (tenTK, matKhau) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, tk.getTenTK());
            pstmt.setString(2, tk.getMatKhau());
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
                rs.close();
            }
        }
        return -1;
    }
    
    // Lấy tài khoản theo mã
    public TaiKhoan getTaiKhoanById(int maTK) {
        String sql = "SELECT maTK, tenTK, matKhau FROM taiKhoan WHERE maTK = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maTK);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new TaiKhoan(
                    rs.getInt("maTK"),
                    rs.getString("tenTK"),
                    rs.getString("matKhau")
                );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Sửa tài khoản
    public boolean suaTaiKhoan(TaiKhoan tk, Connection conn) throws SQLException {
        String sql = "UPDATE taiKhoan SET tenTK = ?, matKhau = ? WHERE maTK = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tk.getTenTK());
            pstmt.setString(2, tk.getMatKhau());
            pstmt.setInt(3, tk.getMaTK());
            
            return pstmt.executeUpdate() > 0;
        }
    }
    
    // Xóa tài khoản
    public boolean xoaTaiKhoan(int maTK) {
        String sql = "DELETE FROM taiKhoan WHERE maTK = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maTK);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}