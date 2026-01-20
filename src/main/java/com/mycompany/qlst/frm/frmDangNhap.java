package com.mycompany.qlst.frm;

import javax.swing.*;

import com.mycompany.qlst.Helpers.GlobalAccessPoint;
import com.mycompany.qlst.dao.DangNhapDAO;

import java.awt.*;

public class frmDangNhap extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnExit;


    public frmDangNhap() {
        super("Đăng nhập hệ thống");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initComponents();
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Title
        JLabel lblTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(41, 128, 185));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 20, 5);
        panel.add(lblTitle, gbc);
        
        // Username
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        panel.add(lblUsername, gbc);
        
        gbc.gridx = 1;
        txtUsername = new JTextField(20);
        panel.add(txtUsername, gbc);
        
        // Password
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel lblPassword = new JLabel("Mật khẩu:");
        panel.add(lblPassword, gbc);
        
        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        panel.add(txtPassword, gbc);
        
        // Buttons panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        btnLogin = new JButton("Đăng nhập");
        btnLogin.setPreferredSize(new Dimension(120, 35));
        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        
        btnExit = new JButton("Thoát");
        btnExit.setPreferredSize(new Dimension(120, 35));
        btnExit.setBackground(new Color(149, 165, 166));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFocusPainted(false);
        
        btnPanel.add(btnLogin);
        btnPanel.add(btnExit);
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        panel.add(btnPanel, gbc);
        
        add(panel);

        btnLogin.addActionListener(e -> Login());
        btnExit.addActionListener(e -> System.exit(0));

        txtPassword.addActionListener(e -> Login());
        txtUsername.addActionListener(e -> txtPassword.requestFocus());
    }
    

    private void Login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String chucVu = DangNhapDAO.kiemTraDangNhap(username, password);
        
        if (chucVu != null && !chucVu.isEmpty()) {
            var globalVariables = GlobalAccessPoint.getInstance();
            globalVariables.chucVuNguoiDung = chucVu;
            globalVariables.username = username;

            String chucVuText = chucVu.equals("admin") ? "Admin" : "Nhân viên";
            
            JOptionPane.showMessageDialog(this,
                "Đăng nhập thành công!\nXin chào " + username + " (" + chucVuText + ")",
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose();
            openMainForm();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Đăng nhập thất bại!\n\nTên đăng nhập hoặc mật khẩu không đúng.\nVui lòng kiểm tra lại thông tin.", 
                "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }

    private void openMainForm() {
        new frmTrangChu();
    }
    
    public static void main(String[] args) {
        new frmDangNhap();
    }
}