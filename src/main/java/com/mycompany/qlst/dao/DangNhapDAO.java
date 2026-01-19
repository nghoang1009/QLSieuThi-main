package com.mycompany.qlst.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mycompany.qlst.Helpers.DatabaseConnector;

public class DangNhapDAO {
    public static String kiemTraDangNhap(String username, String password) {
        String chucVu;

        String sql = "SELECT maTK, tenTK, chucVu FROM taikhoan WHERE tenTK = ? AND matKhau = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {  
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next())
                chucVu = rs.getString("chucVu");
            else
                return null;
            
            rs.close();
            return chucVu;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
