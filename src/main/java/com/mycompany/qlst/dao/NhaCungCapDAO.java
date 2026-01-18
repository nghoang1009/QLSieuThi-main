package com.mycompany.qlst.dao;

import com.mycompany.qlst.Helpers.DatabaseConnector;
import com.mycompany.qlst.model.NhaCungCap;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO {
    
    // Lấy tất cả nhà cung cấp
    public List<NhaCungCap> getAllNhaCungCap() {
        List<NhaCungCap> list = new ArrayList<>();
        String sql = "SELECT maNCC, tenNCC, diaChi, thanhPho FROM nhacungcap ORDER BY maNCC";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap(
                    rs.getInt("maNCC"),
                    rs.getString("tenNCC"),
                    rs.getString("diaChi"),
                    rs.getString("thanhPho")
                );
                list.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy nhà cung cấp theo mã
    public NhaCungCap getNhaCungCapById(int maNCC) {
        String sql = "SELECT maNCC, tenNCC, diaChi, thanhPho FROM nhacungcap WHERE maNCC = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maNCC);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new NhaCungCap(
                    rs.getInt("maNCC"),
                    rs.getString("tenNCC"),
                    rs.getString("diaChi"),
                    rs.getString("thanhPho")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Lấy nhà cung cấp theo thành phố
    public List<NhaCungCap> getNhaCungCapByThanhPho(String thanhPho) {
        List<NhaCungCap> list = new ArrayList<>();
        String sql = "SELECT maNCC, tenNCC, diaChi, thanhPho FROM nhacungcap WHERE thanhPho LIKE ? ORDER BY maNCC";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + thanhPho + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap(
                    rs.getInt("maNCC"),
                    rs.getString("tenNCC"),
                    rs.getString("diaChi"),
                    rs.getString("thanhPho")
                );
                list.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm nhà cung cấp
    public boolean themNhaCungCap(NhaCungCap ncc) {
        String sql = "INSERT INTO nhacungcap (tenNCC, diaChi, thanhPho) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ncc.getTenNCC());
            pstmt.setString(2, ncc.getDiaChi());
            pstmt.setString(3, ncc.getThanhPho());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Sửa nhà cung cấp
    public boolean suaNhaCungCap(NhaCungCap ncc) {
        String sql = "UPDATE nhacungcap SET tenNCC=?, diaChi=?, thanhPho=? WHERE maNCC=?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ncc.getTenNCC());
            pstmt.setString(2, ncc.getDiaChi());
            pstmt.setString(3, ncc.getThanhPho());
            pstmt.setInt(4, ncc.getMaNCC());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa nhà cung cấp
    public boolean xoaNhaCungCap(int maNCC) {
        String sql = "DELETE FROM nhacungcap WHERE maNCC = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maNCC);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Tìm kiếm nhà cung cấp theo tên
    public List<NhaCungCap> timKiemNhaCungCap(String keyword) {
        List<NhaCungCap> list = new ArrayList<>();
        String sql = "SELECT maNCC, tenNCC, diaChi, thanhPho FROM nhacungcap WHERE tenNCC LIKE ? ORDER BY maNCC";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap(
                    rs.getInt("maNCC"),
                    rs.getString("tenNCC"),
                    rs.getString("diaChi"),
                    rs.getString("thanhPho")
                );
                list.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Đếm số sản phẩm của nhà cung cấp
    public int demSanPham(int maNCC) {
        String sql = "SELECT COUNT(*) as soLuong FROM sanpham WHERE maNCC = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maNCC);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("soLuong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}