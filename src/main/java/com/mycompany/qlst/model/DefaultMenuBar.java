package com.mycompany.qlst.model;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.mycompany.qlst.frm.frmGiaoHang;
import com.mycompany.qlst.frm.frmGioHang;
import com.mycompany.qlst.frm.frmHoaDon;
import com.mycompany.qlst.frm.frmKhachHang;
import com.mycompany.qlst.frm.frmKhuyenMai;
import com.mycompany.qlst.frm.frmLogin;
import com.mycompany.qlst.frm.frmNhaCungCap;
import com.mycompany.qlst.frm.frmNhanVien;
import com.mycompany.qlst.frm.frmSanPham;
import com.mycompany.qlst.frm.frmThongKe;

public class DefaultMenuBar {
    public static JMenuBar createMenuBar(JFrame parent) {
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Quản lý
        JMenu menuQuanLy = new JMenu("Quản lý");
        
        JMenuItem mSanPham = new JMenuItem("Sản phẩm");
        JMenuItem mKhachHang = new JMenuItem("Khách hàng");
        JMenuItem mNhanVien = new JMenuItem("Nhân viên");
        JMenuItem mNhaCungCap = new JMenuItem("Nhà cung cấp");
        JMenuItem mKhuyenMai = new JMenuItem("Khuyến mãi");
        
        menuQuanLy.add(mSanPham);
        menuQuanLy.add(mNhaCungCap);
        menuQuanLy.add(mKhachHang);
        menuQuanLy.add(mNhanVien);
        menuQuanLy.add(mKhuyenMai);
        
        // Phân quyền: Chỉ Admin mới xem được Nhân viên
        if (!frmLogin.chucVu.equals("1")) {
            mNhanVien.setEnabled(false);
        }
        
        // Menu Bán hàng
        JMenu menuBanHang = new JMenu("Bán hàng");
        
        JMenuItem mHoaDon = new JMenuItem("Hóa đơn");
        JMenuItem mGioHang = new JMenuItem("Giỏ hàng");
        JMenuItem mGiaoHang = new JMenuItem("Giao hàng");
        
        menuBanHang.add(mHoaDon);
        menuBanHang.add(mGioHang);
        menuBanHang.add(mGiaoHang);
        
        // Menu Thống kê
        JMenu menuThongKe = new JMenu("Thống kê");
        
        JMenuItem mThongKe = new JMenuItem("Xem thống kê");
        
        menuThongKe.add(mThongKe);
   
        
        // Phân quyền: Chỉ Admin mới xem được Thống kê
        if (!frmLogin.chucVu.equals("1")) {
            menuThongKe.setEnabled(false);
        }
        
        // Menu Hệ thống
        JMenu menuHeThong = new JMenu("Hệ thống");
        
        JMenuItem mDangXuat = new JMenuItem("Đăng xuất");
        JMenuItem mThoat = new JMenuItem("Thoát");
        
        menuHeThong.add(mDangXuat);
        menuHeThong.add(mThoat);
        
        // Thêm menu vào menubar
        menuBar.add(menuQuanLy);
        menuBar.add(menuBanHang);
        menuBar.add(menuThongKe);
        menuBar.add(menuHeThong);
        
        // Event handlers
        mSanPham.addActionListener(e -> {
            frmSanPham frmSanPham = new frmSanPham();
            frmSanPham.setVisible(true);
        });
        
        mKhachHang.addActionListener(e -> {
            frmKhachHang frmKhachHang = new frmKhachHang();
            frmKhachHang.setVisible(true);
        });
        
        mNhanVien.addActionListener(e -> {
            if (frmLogin.chucVu.equals("1")) {
                frmNhanVien frmNhanVien = new frmNhanVien();
                frmNhanVien.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Bạn không có quyền truy cập!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        mHoaDon.addActionListener(e -> {
            frmHoaDon frmHoaDon = new frmHoaDon();
            frmHoaDon.setVisible(true);
        });
        
        mGioHang.addActionListener(e -> {
            frmGioHang frmGioHang = new frmGioHang();
            frmGioHang.setVisible(true);
        });
        
        mGiaoHang.addActionListener(e -> {
            frmGiaoHang frmGiaoHang = new frmGiaoHang();
            frmGiaoHang.setVisible(true);
        });
        
        mThongKe.addActionListener(e -> {
            frmThongKe frmThongKe = new frmThongKe();
            frmThongKe.setVisible(true);
        });
        
        mKhuyenMai.addActionListener(e -> {
            frmKhuyenMai frmKhuyenMai = new frmKhuyenMai("Tạo khuyến mãi mới!");
            frmKhuyenMai.setVisible(true);
        });

        mNhaCungCap.addActionListener(e ->{
            frmNhaCungCap frmNhaCungCap = new frmNhaCungCap();
            frmNhaCungCap.setVisible(true);
        });
        
        mDangXuat.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, 
                "Bạn có chắc muốn đăng xuất?", 
                "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                parent.dispose();
                frmLogin frmLogin = new frmLogin();
                frmLogin.setVisible(true);
            }
        });
        
        mThoat.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, 
                "Bạn có chắc muốn thoát?", 
                "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        return menuBar;
    }
}
