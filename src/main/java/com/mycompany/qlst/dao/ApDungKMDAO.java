package com.mycompany.qlst.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.qlst.Helpers.DatabaseConnector;
import com.mycompany.qlst.model.ApDungKhuyenMai;

public class ApDungKMDAO {
    public static List<ApDungKhuyenMai> getAll() {
        List<ApDungKhuyenMai> list = new ArrayList<>();

        String sql = "Select km.maKhM, km.tenKhM, sp.maSP, sp.tenSP " +
                     "From spkhuyenmai spkm " +
                     "Left Join khuyenmai km On spkm.maKhM = km.maKhM " +
                     "Left Join sanpham sp On spkm.maSP = sp.maSP";
        
        try (Connection con = DatabaseConnector.getConnection();
             Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            
            while (rs.next()) {
                var apDung = new ApDungKhuyenMai(rs.getInt("maKhM"),
                                                 rs.getString("tenKhM"),
                                                 rs.getInt("maSP"),
                                                 rs.getString("tenSP"));
                list.add(apDung);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return list;
    }


    public static boolean themApDung(ApDungKhuyenMai apDung) {
        String sql = "Insert into spkhuyenmai(maSP, maKhM) Values (?, ?)";

        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            
            statement.setInt(1, apDung.getMaSP());
            statement.setInt(2, apDung.getMaKhM());
            statement.executeUpdate();
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static boolean xoaApDung(ApDungKhuyenMai apDung) {
        String sql = "Delete from spkhuyenmai Where maSP = ? AND maKhM = ?";

        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            
            statement.setInt(1, apDung.getMaSP());
            statement.setInt(2, apDung.getMaKhM());
            statement.executeUpdate();
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
