package com.mycompany.qlst.frm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mycompany.qlst.Helpers.GlobalAccessPoint;
import com.mycompany.qlst.model.DefaultMenuBar;

public class frmTrangChu extends JFrame {
    private GlobalAccessPoint globalVariables = GlobalAccessPoint.getInstance();
    
    public frmTrangChu() {
        super("Hệ thống quản lý siêu thị");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
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
        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ SIÊU THỊ");

        String chucVuText = GlobalAccessPoint.getInstance().chucVuNguoiDung.equals("admin") ? "Admin" : "Nhân viên";
        JLabel lblUserInfo = new JLabel("Xin chào: " + globalVariables.username + " (" + chucVuText + ")");


        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        
        lblUserInfo.setForeground(Color.WHITE);
        lblUserInfo.setFont(new Font("Arial", Font.PLAIN, 13));
        
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(lblUserInfo, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        add(mainPanel);
        
        // Tạo MenuBar
        var menuBar = DefaultMenuBar.createMenuBar(this);
        setJMenuBar(menuBar);
    }
}