package com.mycompany.qlst.model;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
import com.mycompany.qlst.Helpers.GlobalAccessPoint;
import com.mycompany.qlst.frm.frmApplyKM;
import com.mycompany.qlst.frm.frmDangNhap;
import com.mycompany.qlst.frm.frmNhaCungCap;
import com.mycompany.qlst.frm.frmNhanVien;
import com.mycompany.qlst.frm.frmSanPham;
import com.mycompany.qlst.frm.frmThongKe;
import com.mycompany.qlst.frm.frmTrangChu;

public class DefaultMenuBar {
    public static JMenuBar createMenuBar(JFrame parent) {
        var globalVariables = GlobalAccessPoint.getInstance();
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Quản lý
        JMenu menuQuanLy = new JMenu("Quản lý");
        var menuTrangChu = new JMenu("Trang chủ");
        
        var mSanPham = new JMenuItem("Sản phẩm");
        var mKhachHang = new JMenuItem("Khách hàng");
        var mNhanVien = new JMenuItem("Nhân viên");
        var mNhaCungCap = new JMenuItem("Nhà cung cấp");
        var mKhuyenMai = new JMenuItem("Khuyến mãi");
        var mApplyKM = new JMenuItem("Áp dụng khuyến mãi");
        
        menuQuanLy.add(mSanPham);
        menuQuanLy.add(mNhaCungCap);
        menuQuanLy.add(mKhachHang);
        menuQuanLy.add(mNhanVien);
        menuQuanLy.add(mKhuyenMai);
        menuQuanLy.add(mApplyKM);
        
        // Phân quyền: Chỉ Admin mới xem được Nhân viên
        if (!globalVariables.chucVuNguoiDung.equals("admin")) {
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
        if (!globalVariables.chucVuNguoiDung.equals("admin")) {
            menuThongKe.setEnabled(false);
        }
        
        // Menu Hệ thống
        JMenu menuHeThong = new JMenu("Hệ thống");
        
        JMenuItem mDangXuat = new JMenuItem("Đăng xuất");
        JMenuItem mThoat = new JMenuItem("Thoát");
        
        menuHeThong.add(mDangXuat);
        menuHeThong.add(mThoat);
        
        // Thêm menu vào menubar
        menuBar.add(menuTrangChu);
        menuBar.add(menuQuanLy);
        menuBar.add(menuBanHang);
        menuBar.add(menuThongKe);
        menuBar.add(menuHeThong);
        
        // Event handlers
        menuTrangChu.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (parent.getClass() != frmTrangChu.class) {
                    parent.dispose();
                    new frmTrangChu();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
            
        });
                


        mSanPham.addActionListener(e -> {
            parent.dispose();
            new frmSanPham();
        });
        
        mKhachHang.addActionListener(e -> {
            parent.dispose();
            new frmKhachHang();
        });
        
        mNhanVien.addActionListener(e -> {
                parent.dispose();
                new frmNhanVien();
        });
        
        mHoaDon.addActionListener(e -> {
            parent.dispose();
            new frmHoaDon();
        });
        
        mGioHang.addActionListener(e -> {
            parent.dispose();
            new frmGioHang();
        });
        
        mGiaoHang.addActionListener(e -> {
            parent.dispose();
            new frmGiaoHang();
        });
        
        mThongKe.addActionListener(e -> {
            parent.dispose();
            new frmThongKe();
        });
        
        mKhuyenMai.addActionListener(e -> {
            parent.dispose();
            new frmKhuyenMai("Quản lý khuyến mãi");
        });

        mApplyKM.addActionListener(e -> {
            parent.dispose();
            new frmApplyKM("Áp dụng khuyến mãi");
        });

        mNhaCungCap.addActionListener(e ->{
            parent.dispose();
            new frmNhaCungCap();
        });
        
        mDangXuat.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, 
                "Bạn có chắc muốn đăng xuất?", 
                "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                parent.dispose();
                frmDangNhap frmLogin = new frmDangNhap();
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
