package com.mycompany.qlst.frm;

import javax.swing.*;
import java.awt.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class frmTrangChu extends JFrame {
    public frmTrangChu()
    {
        super("Trang chu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lbTitle = new JLabel("TRANG CHỦ", JLabel.CENTER);
        lbTitle.setForeground(Color.RED);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Menu");

        JMenuItem mSanPham = new JMenuItem("Sản phẩm");
        JMenuItem mNhanVien = new JMenuItem("Nhân viên");
        JMenuItem mHoaDon = new JMenuItem("Hóa đơn");
        JMenuItem mGioHang = new JMenuItem("Giỏ hàng");
        JMenuItem mGiaoHang = new JMenuItem("Giao hàng");
        JMenuItem mKhachHang = new JMenuItem("Khách hàng");
        JMenuItem mThongKe = new JMenuItem("Thống kê");
        JMenuItem mExit = new JMenuItem("Thoat");

        menuFile.add(mSanPham);
        menuFile.add(mGiaoHang);
        menuFile.add(mNhanVien);
        menuFile.add(mKhachHang);
        menuFile.add(mHoaDon);
        menuFile.add(mThongKe);
        menuFile.add(mGioHang);
        menuFile.addSeparator();
        menuFile.add(mExit);

        menuBar.add(menuFile);
        setJMenuBar(menuBar);

        pack();
        setSize(900, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        
        mKhachHang.addActionListener(e ->
        {
            frmKhachHang frmKhachHang = new frmKhachHang();
            frmKhachHang.setVisible(true);
        });

        mNhanVien.addActionListener(e ->
        {
            frmNhanVien frmNhanVien = new frmNhanVien();
            frmNhanVien.setVisible(true);
        });

        mHoaDon.addActionListener(e ->
        {
            frmHoaDon frmHoaDon = new frmHoaDon();
//            frmHoaDon.setVisible(true);
        });

        mGioHang.addActionListener(e ->
        {
            frmGioHang frmGioHang = new frmGioHang();
            frmGioHang.setVisible(true);
        });

        mGiaoHang.addActionListener(e ->
        {
            frmGiaoHang frmGiaoHang = new frmGiaoHang();
            frmGiaoHang.setVisible(true);
        });

        mSanPham.addActionListener(e ->
        {
            frmSanPham frmSanPham = new frmSanPham();
            frmSanPham.setVisible(true);
        });
        
        mExit.addActionListener(e ->
        {
            frmLogin frmLogin = new frmLogin();
            frmLogin.setVisible(true);
            this.dispose();
        });
    }
}
