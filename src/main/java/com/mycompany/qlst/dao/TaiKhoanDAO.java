package com.mycompany.qlst.dao;

import com.mycompany.qlst.Helpers.DatabaseConnector;
import com.mycompany.qlst.model.TaiKhoan;
import java.sql.*;

public class TaiKhoanDAO {
    
    // Thêm tài khoản và trả về mã tài khoản vừa tạo
    public int themTaiKhoan(TaiKhoan tk) {
        String sql = "INSERT INTO taikhoan (tenTK, matKhau) VALUES (?, ?)";
        
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    // Lấy tài khoản theo mã
    public TaiKhoan getTaiKhoanById(int maTK) {
        String sql = "SELECT maTK, tenTK, matKhau FROM taikhoan WHERE maTK = ?";
        
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Sửa tài khoản
    public boolean suaTaiKhoan(TaiKhoan tk) {
        String sql = "UPDATE taikhoan SET tenTK = ?, matKhau = ? WHERE maTK = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tk.getTenTK());
            pstmt.setString(2, tk.getMatKhau());
            pstmt.setInt(3, tk.getMaTK());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa tài khoản
    public boolean xoaTaiKhoan(int maTK) {
        String sql = "DELETE FROM taikhoan WHERE maTK = ?";
        
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
