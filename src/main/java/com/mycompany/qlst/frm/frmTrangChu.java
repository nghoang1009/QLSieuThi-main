package com.mycompany.qlst.frm;

import javax.swing.*;
import java.awt.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class frmTrangChu extends JFrame {
    
    public frmTrangChu() {
        super("Hệ thống quản lý siêu thị");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Thiết lập giao diện
        initComponents();
        
        setSize(900, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ SIÊU THỊ");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        
        String chucVuText = frmLogin.chucVu.equals("1") ? "Admin" : "Nhân viên";
        JLabel lblUserInfo = new JLabel("Xin chào: " + frmLogin.tenTK + " (" + chucVuText + ")");
        lblUserInfo.setForeground(Color.WHITE);
        lblUserInfo.setFont(new Font("Arial", Font.PLAIN, 13));
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(lblUserInfo, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        add(mainPanel);
        
        // Tạo MenuBar
        createMenuBar();
    }
    
    private void createMenuBar() {
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
        
        setJMenuBar(menuBar);
        
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
                JOptionPane.showMessageDialog(this, 
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
            frmKhuyenMai frmKhuyenMai = new frmKhuyenMai();
            frmKhuyenMai.setVisible(true);
        });

        mNhaCungCap.addActionListener(e ->{
            frmNhaCungCap frmNhaCungCap = new frmNhaCungCap();
            frmNhaCungCap.setVisible(true);
        });
        
        mDangXuat.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc muốn đăng xuất?", 
                "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                this.dispose();
                frmLogin frmLogin = new frmLogin();
                frmLogin.setVisible(true);
            }
        });
        
        mThoat.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc muốn thoát?", 
                "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}