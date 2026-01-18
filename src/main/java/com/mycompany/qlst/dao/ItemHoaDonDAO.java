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
    
    // JOIN với bảng sanPham để lấy maSP
    String sql = "SELECT ihd.maItemHoaDon, ihd.maHoaDon, ihd.tenSP, ihd.gia, ihd.soLuong, " +
                 "sp.maSP " +
                 "FROM item_hoadon ihd " +
                 "LEFT JOIN sanpham sp ON ihd.tenSP = sp.tenSP " +
                 "WHERE ihd.maHoaDon = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, maHoaDon);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            ItemHoaDon item = new ItemHoaDon();
            item.setMaItemHoaDon(rs.getInt("maItemHoaDon"));
            item.setMaHoaDon(rs.getInt("maHoaDon"));
            item.setTenSP(rs.getString("tenSP"));
            item.setGia(rs.getInt("gia"));
            item.setSoLuong(rs.getInt("soLuong"));
            
            // Lấy maSP từ JOIN (có thể null nếu sản phẩm đã bị xóa)
            int maSP = rs.getInt("maSP");
            if (!rs.wasNull()) {
                item.setMaSP(maSP);
            } else {
                item.setMaSP(0); // Giá trị mặc định nếu không tìm thấy
            }
            
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
            
            // Lấy thông tin sản phẩm từ database
            String sqlGetSP = "SELECT tenSP, gia FROM sanpham WHERE maSP = ?";
            PreparedStatement pstmtGetSP = conn.prepareStatement(sqlGetSP);
            pstmtGetSP.setInt(1, item.getMaSP());
            ResultSet rs = pstmtGetSP.executeQuery();
            
            if (!rs.next()) {
                conn.rollback();
                return false;
            }
            
            String tenSP = rs.getString("tenSP");
            int gia = rs.getInt("gia");
            
            // Thêm item vào hóa đơn với tenSP và gia
            String sql = "INSERT INTO item_hoadon (maHoaDon, tenSP, gia, soLuong, phanTramGiam) " +
                        "VALUES (?, ?, ?, ?, 0)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, item.getMaHoaDon());
            pstmt.setString(2, tenSP);
            pstmt.setInt(3, gia);
            pstmt.setInt(4, item.getSoLuong());
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                // Trừ số lượng trong kho
                String sqlUpdate = "UPDATE sanpham SET soLuong = soLuong - ? WHERE maSP = ?";
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
            String sql = "UPDATE item_hoadon SET soLuong = ? WHERE maItemHoaDon = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, soLuongMoi);
            pstmt.setInt(2, maItemHoaDon);
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                // Cập nhật số lượng trong kho (trừ/cộng chênh lệch)
                int chenhLech = soLuongMoi - soLuongCu;
                String sqlUpdate = "UPDATE sanpham SET soLuong = soLuong - ? WHERE maSP = ?";
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
            String sql = "DELETE FROM item_hoadon WHERE maItemHoaDon = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, maItemHoaDon);
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                // Cộng lại số lượng vào kho
                String sqlUpdate = "UPDATE sanpham SET soLuong = soLuong + ? WHERE maSP = ?";
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
    
    // Kiểm tra sản phẩm đã có trong hóa đơn chưa (dựa vào tenSP)
    public boolean kiemTraTonTai(int maHoaDon, int maSP) {
        String sql = "SELECT COUNT(*) FROM item_hoadon ihd " +
                    "INNER JOIN sanpham sp ON ihd.tenSP = sp.tenSP " +
                    "WHERE ihd.maHoaDon = ? AND sp.maSP = ?";
        
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
