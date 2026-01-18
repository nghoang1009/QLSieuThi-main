package com.mycompany.qlst.frm;

import com.mycompany.qlst.dao.NhanVienDAO;
import com.mycompany.qlst.model.DefaultMenuBar;
import com.mycompany.qlst.model.NhanVien;
import com.mycompany.qlst.model.TaiKhoan;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class frmNhanVien extends JFrame {
    private JTable nhanVienTable;
    private DefaultTableModel tableModel;
    private JTextField txtMaNV, txtTenNV, txtSDT, txtDiaChi, txtNgaySinh;
    private JComboBox<String> cboGioiTinh;
    private JTextField txtTenTK, txtMatKhau;
    private JList<String> listChucVu;
    private DefaultListModel<String> chucVuListModel;
    
    // DAO
    private NhanVienDAO nhanVienDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public frmNhanVien() {
        super("Quản lý nhân viên");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Tạo MenuBar
        var menuBar = DefaultMenuBar.createMenuBar(this);
        setJMenuBar(menuBar);
        
        // Khởi tạo DAO
        nhanVienDAO = new NhanVienDAO();

        JLabel lbTitle = new JLabel("QUẢN LÝ NHÂN VIÊN", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        //Panel Left
        JPanel pnLeft = new JPanel(new BorderLayout());
        chucVuListModel = new DefaultListModel<>();
        chucVuListModel.addElement("Tất cả nhân viên");
        chucVuListModel.addElement("Nam");
        chucVuListModel.addElement("Nữ");
        
        listChucVu = new JList<>(chucVuListModel);
        JScrollPane scrollPane = new JScrollPane(listChucVu);
        scrollPane.setBorder(new TitledBorder(border, "Lọc theo giới tính"));

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

        String[] columns = {"Mã NV", "Tên nhân viên", "Ngày sinh", "Giới tính", "SĐT", "Địa chỉ"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        nhanVienTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(nhanVienTable);
        tablePanel.add(tableScrollPane);
        pnRight.add(tablePanel, BorderLayout.PAGE_START);
        tableScrollPane.setPreferredSize(new Dimension(tableScrollPane.getWidth(), 200));

        JPanel pnTT = new JPanel(new GridLayout(8, 2, 5, 3));
        pnTT.setBorder(new TitledBorder(border, "Thông tin nhân viên"));
        
        JLabel lbMaNV = new JLabel("Mã nhân viên:");
        JLabel lbTenTK = new JLabel("Tên tài khoản:");
        JLabel lbMatKhau = new JLabel("Mật khẩu:");
        JLabel lbTenNV = new JLabel("Tên nhân viên:");
        JLabel lbNgaySinh = new JLabel("Ngày sinh (yyyy-MM-dd):");
        JLabel lbGioiTinh = new JLabel("Giới tính:");
        JLabel lbSDT = new JLabel("Số điện thoại:");
        JLabel lbDiaChi = new JLabel("Địa chỉ:");

        txtMaNV = new JTextField(30);
        txtMaNV.setEditable(false);
        txtTenTK = new JTextField(30);
        txtMatKhau = new JPasswordField(30);
        txtTenNV = new JTextField(30);
        txtNgaySinh = new JTextField(30);
        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        txtSDT = new JTextField(30);
        txtDiaChi = new JTextField(30);

        pnTT.add(lbMaNV);
        pnTT.add(txtMaNV);
        pnTT.add(lbTenTK);
        pnTT.add(txtTenTK);
        pnTT.add(lbMatKhau);
        pnTT.add(txtMatKhau);
        pnTT.add(lbTenNV);
        pnTT.add(txtTenNV);
        pnTT.add(lbNgaySinh);
        pnTT.add(txtNgaySinh);
        pnTT.add(lbGioiTinh);
        pnTT.add(cboGioiTinh);
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

        setSize(900, 550);
        setLocationRelativeTo(null);

        // Load dữ liệu ban đầu
        loadAllNhanVien();

        // Event handlers
        btnRefresh.addActionListener(e -> loadAllNhanVien());
        btnSearch.addActionListener(e -> TimKiem());
        
        btAdd.addActionListener(e -> ThemNhanVien());
        btUpdate.addActionListener(e -> SuaNhanVien());
        btDelete.addActionListener(e -> XoaNhanVien());
        btClear.addActionListener(e -> ClearForm());
        
        // Event khi click vào table
        nhanVienTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = nhanVienTable.getSelectedRow();
                if (row >= 0) {
                    int maNV = (int) tableModel.getValueAt(row, 0);
                    txtMaNV.setText(String.valueOf(maNV));
                    txtTenNV.setText(tableModel.getValueAt(row, 1).toString());
                    txtNgaySinh.setText(tableModel.getValueAt(row, 2).toString());
                    cboGioiTinh.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                    txtSDT.setText(tableModel.getValueAt(row, 4).toString());
                    txtDiaChi.setText(tableModel.getValueAt(row, 5).toString());
                    
                    // Load thông tin tài khoản
                    loadThongTinTaiKhoan(maNV);
                }
            }
        });
        
        // Event khi chọn filter
        listChucVu.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = listChucVu.getSelectedValue();
                if (selected != null) {
                    if (selected.equals("Tất cả nhân viên")) {
                        loadAllNhanVien();
                    } else {
                        loadNhanVienByGioiTinh(selected);
                    }
                }
            }
        });
    }

    // Load tất cả nhân viên
    private void loadAllNhanVien() {
        tableModel.setRowCount(0);
        List<NhanVien> listNV = nhanVienDAO.getAllNhanVien();
        
        for (NhanVien nv : listNV) {
            Object[] row = {
                nv.getMaNV(),
                nv.getTen(),
                nv.getNgaySinh(),
                nv.getGioiTinh(),
                nv.getSdt(),
                nv.getDiaChi()
            };
            tableModel.addRow(row);
        }
    }

    // Load nhân viên theo giới tính
    private void loadNhanVienByGioiTinh(String gioiTinh) {
        tableModel.setRowCount(0);
        List<NhanVien> listNV = nhanVienDAO.getNhanVienByGioiTinh(gioiTinh);
        
        for (NhanVien nv : listNV) {
            Object[] row = {
                nv.getMaNV(),
                nv.getTen(),
                nv.getNgaySinh(),
                nv.getGioiTinh(),
                nv.getSdt(),
                nv.getDiaChi()
            };
            tableModel.addRow(row);
        }
    }

    // Load thông tin tài khoản
    private void loadThongTinTaiKhoan(int maNV) {
        NhanVien nv = nhanVienDAO.getNhanVienById(maNV);
        if (nv != null && nv.getTaiKhoan() != null) {
            txtTenTK.setText(nv.getTaiKhoan().getTenTK());
            txtMatKhau.setText(nv.getTaiKhoan().getMatKhau());
        }
    }

    // Tìm kiếm nhân viên
    private void TimKiem() {
        String keyword = JOptionPane.showInputDialog(this, "Nhập tên nhân viên cần tìm:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            tableModel.setRowCount(0);
            List<NhanVien> listNV = nhanVienDAO.timKiemNhanVien(keyword);
            
            for (NhanVien nv : listNV) {
                Object[] row = {
                    nv.getMaNV(),
                    nv.getTen(),
                    nv.getNgaySinh(),
                    nv.getGioiTinh(),
                    nv.getSdt(),
                    nv.getDiaChi()
                };
                tableModel.addRow(row);
            }
            
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên nào!");
            }
        }
    }

    // ============ CRUD NHÂN VIÊN ============
    private void ThemNhanVien() {
        // Validate input
        if (txtTenTK.getText().trim().isEmpty() ||
            txtMatKhau.getText().trim().isEmpty() ||
            txtTenNV.getText().trim().isEmpty() || 
            txtNgaySinh.getText().trim().isEmpty() ||
            txtSDT.getText().trim().isEmpty() ||
            txtDiaChi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        try {
            // Validate ngày sinh
            java.util.Date ngaySinh = dateFormat.parse(txtNgaySinh.getText().trim());
            Date sqlDate = new Date(ngaySinh.getTime());
            
            // Tạo tài khoản
            TaiKhoan tk = new TaiKhoan(
                txtTenTK.getText().trim(),
                txtMatKhau.getText().trim()
            );
            
            // Tạo nhân viên
            NhanVien nv = new NhanVien(
                txtTenNV.getText().trim(),
                sqlDate,
                (String) cboGioiTinh.getSelectedItem(),
                txtSDT.getText().trim(),
                txtDiaChi.getText().trim()
            );
            
            if (nhanVienDAO.themNhanVien(nv, tk)) {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
                loadAllNhanVien();
                ClearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi thêm nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày sinh không hợp lệ! (yyyy-MM-dd)", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void SuaNhanVien() {
        if (txtMaNV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!");
            return;
        }
        
        try {
            int maNV = Integer.parseInt(txtMaNV.getText().trim());
            
            // Validate ngày sinh
            java.util.Date ngaySinh = dateFormat.parse(txtNgaySinh.getText().trim());
            Date sqlDate = new Date(ngaySinh.getTime());
            
            // Tạo tài khoản với thông tin mới
            TaiKhoan tk = new TaiKhoan(
                maNV,
                txtTenTK.getText().trim(),
                txtMatKhau.getText().trim()
            );
            
            // Tạo nhân viên với thông tin mới
            NhanVien nv = new NhanVien(
                maNV,
                txtTenNV.getText().trim(),
                sqlDate,
                (String) cboGioiTinh.getSelectedItem(),
                txtSDT.getText().trim(),
                txtDiaChi.getText().trim()
            );
            
            if (nhanVienDAO.suaNhanVien(nv, tk)) {
                JOptionPane.showMessageDialog(this, "Sửa nhân viên thành công!");
                loadAllNhanVien();
                ClearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi sửa nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày sinh không hợp lệ! (yyyy-MM-dd)", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void XoaNhanVien() {
        if (txtMaNV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa nhân viên này?\n(Sẽ xóa cả tài khoản liên quan)", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maNV = Integer.parseInt(txtMaNV.getText().trim());
                
                if (nhanVienDAO.xoaNhanVien(maNV)) {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
                    loadAllNhanVien();
                    ClearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ClearForm() {
        txtMaNV.setText("");
        txtTenTK.setText("");
        txtMatKhau.setText("");
        txtTenNV.setText("");
        txtNgaySinh.setText("");
        cboGioiTinh.setSelectedIndex(0);
        txtSDT.setText("");
        txtDiaChi.setText("");
        nhanVienTable.clearSelection();
        txtTenTK.requestFocus();
    }
    
    // Test chạy form
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmNhanVien frm = new frmNhanVien();
            frm.setVisible(true);
        });
    }
}