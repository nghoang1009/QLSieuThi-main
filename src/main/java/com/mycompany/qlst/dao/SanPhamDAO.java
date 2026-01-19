/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlst.dao;

import com.mycompany.qlst.Helpers.DatabaseConnector;
import com.mycompany.qlst.model.SanPham;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    
    // Lấy tất cả sản phẩm
    public List<SanPham> getAllSanPham() {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT maSP, maDM, tenSP, gia, soLuong FROM sanpham";
        
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                SanPham sp = new SanPham(
                    rs.getInt("maSP"),
                    rs.getInt("maDM"),
                    rs.getString("tenSP"),
                    rs.getInt("gia"),
                    rs.getInt("soLuong")
                );
                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy sản phẩm theo mã
    public SanPham getSanPhamById(int maSP) {
        String sql = "SELECT maSP, maDM, tenSP, gia, soLuong FROM sanpham WHERE maSP = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maSP);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new SanPham(
                    rs.getInt("maSP"),
                    rs.getInt("maDM"),
                    rs.getString("tenSP"),
                    rs.getInt("gia"),
                    rs.getInt("soLuong")
                );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Lấy sản phẩm theo danh mục
    public List<SanPham> getSanPhamByDanhMuc(int maDM) {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT maSP, maDM, tenSP, gia, soLuong FROM sanpham WHERE maDM = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maDM);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                SanPham sp = new SanPham(
                    rs.getInt("maSP"),
                    rs.getInt("maDM"),
                    rs.getString("tenSP"),
                    rs.getInt("gia"),
                    rs.getInt("soLuong")
                );
                list.add(sp);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm sản phẩm
    public boolean themSanPham(SanPham sp) {
        String sql = "INSERT INTO sanpham (maDM, tenSP, gia, soLuong) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, sp.getMaDM());
            pstmt.setString(2, sp.getTenSP());
            pstmt.setInt(3, sp.getGia());
            pstmt.setInt(4, sp.getSoLuong());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Sửa sản phẩm
    public boolean suaSanPham(SanPham sp) {
        String sql = "UPDATE sanpham SET maDM=?, tenSP=?, gia=?, soLuong=? WHERE maSP=?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, sp.getMaDM());
            pstmt.setString(2, sp.getTenSP());
            pstmt.setInt(3, sp.getGia());
            pstmt.setInt(4, sp.getSoLuong());
            pstmt.setInt(5, sp.getMaSP());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Xóa sản phẩm
    public boolean xoaSanPham(int maSP) {
        String sql = "DELETE FROM sanpham WHERE maSP = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maSP);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Tìm kiếm sản phẩm theo tên
    public List<SanPham> timKiemSanPham(String keyword) {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT maSP, maDM, tenSP, gia, soLuong FROM sanpham WHERE tenSP LIKE ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                SanPham sp = new SanPham(
                    rs.getInt("maSP"),
                    rs.getInt("maDM"),
                    rs.getString("tenSP"),
                    rs.getInt("gia"),
                    rs.getInt("soLuong")
                );
                list.add(sp);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}