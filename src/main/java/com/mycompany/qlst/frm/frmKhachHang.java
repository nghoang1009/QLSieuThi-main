package com.mycompany.qlst.frm;

import com.mycompany.qlst.dao.KhachHangDAO;
import com.mycompany.qlst.model.KhachHang;
import com.mycompany.qlst.model.TaiKhoan;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class frmKhachHang extends JFrame {
    private JTable khachHangTable;
    private DefaultTableModel tableModel;
    private JTextField txtMaKH, txtTenKH, txtSDT, txtDiaChi;
    private JTextField txtTenTK, txtMatKhau;
    private JList<String> listKhuVuc;
    private DefaultListModel<String> khuVucListModel;
    
    // DAO
    private KhachHangDAO khachHangDAO;

    public frmKhachHang() {
        super("Quản lý khách hàng");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Khởi tạo DAO
        khachHangDAO = new KhachHangDAO();

        JLabel lbTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        //Panel Left
        JPanel pnLeft = new JPanel(new BorderLayout());
        khuVucListModel = new DefaultListModel<>();
        khuVucListModel.addElement("Tất cả khách hàng");
        khuVucListModel.addElement("Hai Bà Trưng");
        khuVucListModel.addElement("Đống Đa");
        khuVucListModel.addElement("Thanh Xuân");
        khuVucListModel.addElement("Hoàng Mai");
        
        listKhuVuc = new JList<>(khuVucListModel);
        JScrollPane scrollPane = new JScrollPane(listKhuVuc);
        scrollPane.setBorder(new TitledBorder(border, "Lọc theo khu vực"));

        JPanel pnBtn1 = new JPanel();
        JButton btnRefresh = new JButton("Làm mới");
        JButton btnSearch = new JButton("Tìm kiếm");
        
        pnBtn1.add(btnRefresh);
        pnBtn1.add(btnSearch);

        pnLeft.add(scrollPane, BorderLayout.CENTER);
        pnLeft.add(pnBtn1, BorderLayout.PAGE_END);

        //Panel Right
        JPanel pnRight = new JPanel(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết"));

        String[] columns = {"Mã KH", "Tên khách hàng", "SĐT", "Địa chỉ"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        khachHangTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(khachHangTable);
        tablePanel.add(tableScrollPane);
        pnRight.add(tablePanel, BorderLayout.PAGE_START);
        tableScrollPane.setPreferredSize(new Dimension(tableScrollPane.getWidth(), 200));

        JPanel pnTT = new JPanel(new GridLayout(6, 2, 5, 3));
        pnTT.setBorder(new TitledBorder(border, "Thông tin khách hàng"));
        
        JLabel lbMaKH = new JLabel("Mã khách hàng:");
        JLabel lbTenTK = new JLabel("Tên tài khoản:");
        JLabel lbMatKhau = new JLabel("Mật khẩu:");
        JLabel lbTenKH = new JLabel("Tên khách hàng:");
        JLabel lbSDT = new JLabel("Số điện thoại:");
        JLabel lbDiaChi = new JLabel("Địa chỉ:");

        txtMaKH = new JTextField(30);
        txtMaKH.setEditable(false);
        txtTenTK = new JTextField(30);
        txtMatKhau = new JPasswordField(30);
        txtTenKH = new JTextField(30);
        txtSDT = new JTextField(30);
        txtDiaChi = new JTextField(30);

        pnTT.add(lbMaKH);
        pnTT.add(txtMaKH);
        pnTT.add(lbTenTK);
        pnTT.add(txtTenTK);
        pnTT.add(lbMatKhau);
        pnTT.add(txtMatKhau);
        pnTT.add(lbTenKH);
        pnTT.add(txtTenKH);
        pnTT.add(lbSDT);
        pnTT.add(txtSDT);
        pnTT.add(lbDiaChi);
        pnTT.add(txtDiaChi);

        pnRight.add(pnTT, BorderLayout.CENTER);

        //Panel Btn2
        JPanel pnBtn2 = new JPanel();
        JButton btAdd = new JButton("Thêm");
        JButton btUpdate = new JButton("Sửa");
        JButton btDelete = new JButton("Xóa");
        JButton btClear = new JButton("Xóa trắng");

        pnBtn2.add(btAdd);
        pnBtn2.add(btUpdate);
        pnBtn2.add(btDelete);
        pnBtn2.add(btClear);

        pnRight.add(pnBtn2, BorderLayout.PAGE_END);

        //JSplitPane
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRight);
        add(jSplitPane);

        setSize(900, 500);
        setLocationRelativeTo(null);

        // Load dữ liệu ban đầu
        loadAllKhachHang();

        // Event handlers
        btnRefresh.addActionListener(e -> loadAllKhachHang());
        btnSearch.addActionListener(e -> TimKiem());
        
        btAdd.addActionListener(e -> ThemKhachHang());
        btUpdate.addActionListener(e -> SuaKhachHang());
        btDelete.addActionListener(e -> XoaKhachHang());
        btClear.addActionListener(e -> ClearForm());
        
        // Event khi click vào table
        khachHangTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = khachHangTable.getSelectedRow();
                if (row >= 0) {
                    int maKH = (int) tableModel.getValueAt(row, 0);
                    txtMaKH.setText(String.valueOf(maKH));
                    txtTenKH.setText(tableModel.getValueAt(row, 1).toString());
                    txtSDT.setText(tableModel.getValueAt(row, 2).toString());
                    txtDiaChi.setText(tableModel.getValueAt(row, 3).toString());
                    
                    // Load thông tin tài khoản
                    loadThongTinTaiKhoan(maKH);
                }
            }
        });
        
        // Event khi chọn filter
        listKhuVuc.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = listKhuVuc.getSelectedValue();
                if (selected != null) {
                    if (selected.equals("Tất cả khách hàng")) {
                        loadAllKhachHang();
                    } else {
                        loadKhachHangByKhuVuc(selected);
                    }
                }
            }
        });
    }

    // Load tất cả khách hàng
    private void loadAllKhachHang() {
        tableModel.setRowCount(0);
        List<KhachHang> listKH = khachHangDAO.getAllKhachHang();
        
        for (KhachHang kh : listKH) {
            Object[] row = {
                kh.getMaKH(),
                kh.getTen(),
                kh.getSdt(),
                kh.getDiaChi()
            };
            tableModel.addRow(row);
        }
    }

    // Load khách hàng theo khu vực
    private void loadKhachHangByKhuVuc(String khuVuc) {
        tableModel.setRowCount(0);
        List<KhachHang> listKH = khachHangDAO.getKhachHangByKhuVuc(khuVuc);
        
        for (KhachHang kh : listKH) {
            Object[] row = {
                kh.getMaKH(),
                kh.getTen(),
                kh.getSdt(),
                kh.getDiaChi()
            };
            tableModel.addRow(row);
        }
    }

    // Load thông tin tài khoản
    private void loadThongTinTaiKhoan(int maKH) {
        KhachHang kh = khachHangDAO.getKhachHangById(maKH);
        if (kh != null && kh.getTaiKhoan() != null) {
            txtTenTK.setText(kh.getTaiKhoan().getTenTK());
            txtMatKhau.setText(kh.getTaiKhoan().getMatKhau());
        }
    }

    // Tìm kiếm khách hàng
    private void TimKiem() {
        String keyword = JOptionPane.showInputDialog(this, "Nhập tên hoặc SĐT khách hàng:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            tableModel.setRowCount(0);
            List<KhachHang> listKH = khachHangDAO.timKiemKhachHang(keyword);
            
            for (KhachHang kh : listKH) {
                Object[] row = {
                    kh.getMaKH(),
                    kh.getTen(),
                    kh.getSdt(),
                    kh.getDiaChi()
                };
                tableModel.addRow(row);
            }
            
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng nào!");
            }
        }
    }

    // ============ CRUD KHÁCH HÀNG ============
    private void ThemKhachHang() {
        // Validate input
        if (txtTenTK.getText().trim().isEmpty() ||
            txtMatKhau.getText().trim().isEmpty() ||
            txtTenKH.getText().trim().isEmpty() || 
            txtSDT.getText().trim().isEmpty() ||
            txtDiaChi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        try {
            // Tạo tài khoản
            TaiKhoan tk = new TaiKhoan(
                txtTenTK.getText().trim(),
                txtMatKhau.getText().trim()
            );
            
            // Tạo khách hàng
            KhachHang kh = new KhachHang(
                txtTenKH.getText().trim(),
                txtSDT.getText().trim(),
                txtDiaChi.getText().trim()
            );
            
            if (khachHangDAO.themKhachHang(kh, tk)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
                loadAllKhachHang();
                ClearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi thêm khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void SuaKhachHang() {
        if (txtMaKH.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa!");
            return;
        }
        
        try {
            int maKH = Integer.parseInt(txtMaKH.getText().trim());
            
            // Tạo tài khoản với thông tin mới
            TaiKhoan tk = new TaiKhoan(
                maKH,
                txtTenTK.getText().trim(),
                txtMatKhau.getText().trim()
            );
            
            // Tạo khách hàng với thông tin mới
            KhachHang kh = new KhachHang(
                maKH,
                txtTenKH.getText().trim(),
                txtSDT.getText().trim(),
                txtDiaChi.getText().trim()
            );
            
            if (khachHangDAO.suaKhachHang(kh, tk)) {
                JOptionPane.showMessageDialog(this, "Sửa khách hàng thành công!");
                loadAllKhachHang();
                ClearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi sửa khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void XoaKhachHang() {
        if (txtMaKH.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa khách hàng này?\n(Sẽ xóa cả tài khoản và giỏ hàng liên quan)", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maKH = Integer.parseInt(txtMaKH.getText().trim());
                
                if (khachHangDAO.xoaKhachHang(maKH)) {
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                    loadAllKhachHang();
                    ClearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ClearForm() {
        txtMaKH.setText("");
        txtTenTK.setText("");
        txtMatKhau.setText("");
        txtTenKH.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        khachHangTable.clearSelection();
        txtTenTK.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmKhachHang frm = new frmKhachHang();
            frm.setVisible(true);
        });
    }
}