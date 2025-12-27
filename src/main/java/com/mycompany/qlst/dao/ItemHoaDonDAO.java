package com.mycompany.qlst.dao;

import com.mycompany.qlst.database.DatabaseConnection;
import com.mycompany.qlst.model.ItemHoaDon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemHoaDonDAO {
    
    // Lấy tất cả items trong hóa đơn
    public List<ItemHoaDon> getItemsByHoaDon(int maHoaDon) {
        List<ItemHoaDon> list = new ArrayList<>();
        String sql = "SELECT ihd.maItemHoaDon, ihd.maSP, sp.tenSP, sp.gia, ihd.soLuong " +
                     "FROM item_hoaDon ihd " +
                     "INNER JOIN sanPham sp ON ihd.maSP = sp.maSP " +
                     "WHERE ihd.maHoaDon = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maHoaDon);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ItemHoaDon item = new ItemHoaDon(
                    rs.getInt("maItemHoaDon"),
                    maHoaDon,
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
    
    // Thêm item vào hóa đơn (có cập nhật tồn kho)
    public boolean themItem(ItemHoaDon item) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Thêm item vào hóa đơn
            String sql = "INSERT INTO item_hoaDon (maHoaDon, maSP, soLuong) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, item.getMaHoaDon());
            pstmt.setInt(2, item.getMaSP());
            pstmt.setInt(3, item.getSoLuong());
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                // Trừ số lượng trong kho
                String sqlUpdate = "UPDATE sanPham SET soLuong = soLuong - ? WHERE maSP = ?";
                PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
                pstmtUpdate.setInt(1, item.getSoLuong());
                pstmtUpdate.setInt(2, item.getMaSP());
                pstmtUpdate.executeUpdate();
                
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Cập nhật số lượng item (có cập nhật tồn kho)
    public boolean capNhatSoLuong(int maItemHoaDon, int maSP, int soLuongCu, int soLuongMoi) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Cập nhật số lượng item
            String sql = "UPDATE item_hoaDon SET soLuong = ? WHERE maItemHoaDon = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, soLuongMoi);
            pstmt.setInt(2, maItemHoaDon);
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                // Cập nhật số lượng trong kho (trừ/cộng chênh lệch)
                int chenhLech = soLuongMoi - soLuongCu;
                String sqlUpdate = "UPDATE sanPham SET soLuong = soLuong - ? WHERE maSP = ?";
                PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
                pstmtUpdate.setInt(1, chenhLech);
                pstmtUpdate.setInt(2, maSP);
                pstmtUpdate.executeUpdate();
                
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Xóa item khỏi hóa đơn (có cập nhật tồn kho)
    public boolean xoaItem(int maItemHoaDon, int maSP, int soLuong) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Xóa item
            String sql = "DELETE FROM item_hoaDon WHERE maItemHoaDon = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, maItemHoaDon);
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                // Cộng lại số lượng vào kho
                String sqlUpdate = "UPDATE sanPham SET soLuong = soLuong + ? WHERE maSP = ?";
                PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
                pstmtUpdate.setInt(1, soLuong);
                pstmtUpdate.setInt(2, maSP);
                pstmtUpdate.executeUpdate();
                
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Kiểm tra sản phẩm đã có trong hóa đơn chưa
    public boolean kiemTraTonTai(int maHoaDon, int maSP) {
        String sql = "SELECT COUNT(*) FROM item_hoaDon WHERE maHoaDon = ? AND maSP = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, maHoaDon);
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