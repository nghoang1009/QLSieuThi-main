package com.mycompany.qlst.frm;

import com.mycompany.qlst.dao.KhuyenMaiDAO;
import com.mycompany.qlst.model.KhuyenMai;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class frmKhuyenMai extends JFrame {
    private JTable khuyenMaiTable;
    private DefaultTableModel tableModel;
    private JTextField txtMaKhM, txtTenKhM, txtPhanTramGiam, txtNgayHieuLuc, txtNgayKetThuc;
    private JList<String> listTrangThai;
    private DefaultListModel<String> trangThaiListModel;
    
    // DAO
    private KhuyenMaiDAO khuyenMaiDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public frmKhuyenMai() {
        super("Quản lý khuyến mãi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Khởi tạo DAO
        khuyenMaiDAO = new KhuyenMaiDAO();

        JLabel lbTitle = new JLabel("QUẢN LÝ KHUYẾN MÃI", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        //Panel Left
        JPanel pnLeft = new JPanel(new BorderLayout());
        trangThaiListModel = new DefaultListModel<>();
        trangThaiListModel.addElement("Tất cả khuyến mãi");
        trangThaiListModel.addElement("Đang hiệu lực");
        trangThaiListModel.addElement("Đã hết hạn");
        
        listTrangThai = new JList<>(trangThaiListModel);
        JScrollPane scrollPane = new JScrollPane(listTrangThai);
        scrollPane.setBorder(new TitledBorder(border, "Lọc theo trạng thái"));

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

        String[] columns = {"Mã KM", "Tên khuyến mãi", "% Giảm", "Ngày hiệu lực", "Ngày kết thúc"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        khuyenMaiTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(khuyenMaiTable);
        tablePanel.add(tableScrollPane);
        pnRight.add(tablePanel, BorderLayout.PAGE_START);
        tableScrollPane.setPreferredSize(new Dimension(tableScrollPane.getWidth(), 250));

        JPanel pnTT = new JPanel(new GridLayout(5, 2, 5, 3));
        pnTT.setBorder(new TitledBorder(border, "Thông tin khuyến mãi"));
        
        JLabel lbMaKhM = new JLabel("Mã khuyến mãi:");
        JLabel lbTenKhM = new JLabel("Tên khuyến mãi:");
        JLabel lbPhanTramGiam = new JLabel("Phần trăm giảm (%):");
        JLabel lbNgayHieuLuc = new JLabel("Ngày hiệu lực (yyyy-MM-dd):");
        JLabel lbNgayKetThuc = new JLabel("Ngày kết thúc (yyyy-MM-dd):");

        txtMaKhM = new JTextField(30);
        txtMaKhM.setEditable(false);
        txtTenKhM = new JTextField(30);
        txtPhanTramGiam = new JTextField(30);
        txtNgayHieuLuc = new JTextField(30);
        txtNgayKetThuc = new JTextField(30);

        pnTT.add(lbMaKhM);
        pnTT.add(txtMaKhM);
        pnTT.add(lbTenKhM);
        pnTT.add(txtTenKhM);
        pnTT.add(lbPhanTramGiam);
        pnTT.add(txtPhanTramGiam);
        pnTT.add(lbNgayHieuLuc);
        pnTT.add(txtNgayHieuLuc);
        pnTT.add(lbNgayKetThuc);
        pnTT.add(txtNgayKetThuc);

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

        setSize(950, 550);
        setLocationRelativeTo(null);

        // Load dữ liệu ban đầu
        loadAllKhuyenMai();

        // Event handlers
        btnRefresh.addActionListener(e -> loadAllKhuyenMai());
        btnSearch.addActionListener(e -> TimKiem());
        
        btAdd.addActionListener(e -> ThemKhuyenMai());
        btUpdate.addActionListener(e -> SuaKhuyenMai());
        btDelete.addActionListener(e -> XoaKhuyenMai());
        btClear.addActionListener(e -> ClearForm());
        
        // Event khi click vào table
        khuyenMaiTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = khuyenMaiTable.getSelectedRow();
                if (row >= 0) {
                    txtMaKhM.setText(tableModel.getValueAt(row, 0).toString());
                    txtTenKhM.setText(tableModel.getValueAt(row, 1).toString());
                    txtPhanTramGiam.setText(tableModel.getValueAt(row, 2).toString());
                    txtNgayHieuLuc.setText(tableModel.getValueAt(row, 3).toString());
                    txtNgayKetThuc.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });
        
        // Event khi chọn trạng thái
        listTrangThai.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = listTrangThai.getSelectedValue();
                if (selected != null) {
                    if (selected.equals("Tất cả khuyến mãi")) {
                        loadAllKhuyenMai();
                    } else if (selected.equals("Đang hiệu lực")) {
                        loadKhuyenMaiHieuLuc();
                    } else if (selected.equals("Đã hết hạn")) {
                        loadKhuyenMaiHetHan();
                    }
                }
            }
        });
    }

    // Load tất cả khuyến mãi
    private void loadAllKhuyenMai() {
        tableModel.setRowCount(0);
        List<KhuyenMai> listKM = khuyenMaiDAO.getAllKhuyenMai();
        
        for (KhuyenMai km : listKM) {
            Object[] row = {
                km.getMaKhM(),
                km.getTenKhM(),
                km.getPhanTramGiam() + "%",
                km.getNgayHieuLuc(),
                km.getNgayKetThuc()
            };
            tableModel.addRow(row);
        }
    }

    // Load khuyến mãi đang hiệu lực
    private void loadKhuyenMaiHieuLuc() {
        tableModel.setRowCount(0);
        List<KhuyenMai> listKM = khuyenMaiDAO.getKhuyenMaiHieuLuc();
        
        for (KhuyenMai km : listKM) {
            Object[] row = {
                km.getMaKhM(),
                km.getTenKhM(),
                km.getPhanTramGiam() + "%",
                km.getNgayHieuLuc(),
                km.getNgayKetThuc()
            };
            tableModel.addRow(row);
        }
    }

    // Load khuyến mãi đã hết hạn
    private void loadKhuyenMaiHetHan() {
        tableModel.setRowCount(0);
        List<KhuyenMai> listKM = khuyenMaiDAO.getKhuyenMaiHetHan();
        
        for (KhuyenMai km : listKM) {
            Object[] row = {
                km.getMaKhM(),
                km.getTenKhM(),
                km.getPhanTramGiam() + "%",
                km.getNgayHieuLuc(),
                km.getNgayKetThuc()
            };
            tableModel.addRow(row);
        }
    }

    // Tìm kiếm khuyến mãi
    private void TimKiem() {
        String keyword = JOptionPane.showInputDialog(this, "Nhập tên khuyến mãi:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            tableModel.setRowCount(0);
            List<KhuyenMai> listKM = khuyenMaiDAO.timKiemKhuyenMai(keyword);
            
            for (KhuyenMai km : listKM) {
                Object[] row = {
                    km.getMaKhM(),
                    km.getTenKhM(),
                    km.getPhanTramGiam() + "%",
                    km.getNgayHieuLuc(),
                    km.getNgayKetThuc()
                };
                tableModel.addRow(row);
            }
            
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khuyến mãi nào!");
            }
        }
    }

    // ============ CRUD KHUYẾN MÃI ============
    private void ThemKhuyenMai() {
        // Validate input
        if (txtTenKhM.getText().trim().isEmpty() ||
            txtPhanTramGiam.getText().trim().isEmpty() ||
            txtNgayHieuLuc.getText().trim().isEmpty() ||
            txtNgayKetThuc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        try {
            // Validate ngày
            java.util.Date ngayHL = dateFormat.parse(txtNgayHieuLuc.getText().trim());
            java.util.Date ngayKT = dateFormat.parse(txtNgayKetThuc.getText().trim());
            
            if (ngayKT.before(ngayHL)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày hiệu lực!");
                return;
            }
            
            int phanTramGiam = Integer.parseInt(txtPhanTramGiam.getText().trim());
            
            if (phanTramGiam < 0 || phanTramGiam > 100) {
                JOptionPane.showMessageDialog(this, "Phần trăm giảm phải từ 0-100!");
                return;
            }
            
            KhuyenMai km = new KhuyenMai(
                txtTenKhM.getText().trim(),
                phanTramGiam,
                new Date(ngayHL.getTime()),
                new Date(ngayKT.getTime())
            );
            
            if (khuyenMaiDAO.themKhuyenMai(km)) {
                JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!");
                loadAllKhuyenMai();
                ClearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi thêm khuyến mãi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ! (yyyy-MM-dd)", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Phần trăm giảm phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void SuaKhuyenMai() {
        if (txtMaKhM.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần sửa!");
            return;
        }
        
        try {
            int maKhM = Integer.parseInt(txtMaKhM.getText().trim());
            
            // Validate ngày
            java.util.Date ngayHL = dateFormat.parse(txtNgayHieuLuc.getText().trim());
            java.util.Date ngayKT = dateFormat.parse(txtNgayKetThuc.getText().trim());
            
            if (ngayKT.before(ngayHL)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày hiệu lực!");
                return;
            }
            
            int phanTramGiam = Integer.parseInt(txtPhanTramGiam.getText().trim());
            
            if (phanTramGiam < 0 || phanTramGiam > 100) {
                JOptionPane.showMessageDialog(this, "Phần trăm giảm phải từ 0-100!");
                return;
            }
            
            KhuyenMai km = new KhuyenMai(
                maKhM,
                txtTenKhM.getText().trim(),
                phanTramGiam,
                new Date(ngayHL.getTime()),
                new Date(ngayKT.getTime())
            );
            
            if (khuyenMaiDAO.suaKhuyenMai(km)) {
                JOptionPane.showMessageDialog(this, "Sửa khuyến mãi thành công!");
                loadAllKhuyenMai();
                ClearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi sửa khuyến mãi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ! (yyyy-MM-dd)", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void XoaKhuyenMai() {
        if (txtMaKhM.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa khuyến mãi này?", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maKhM = Integer.parseInt(txtMaKhM.getText().trim());
                
                if (khuyenMaiDAO.xoaKhuyenMai(maKhM)) {
                    JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công!");
                    loadAllKhuyenMai();
                    ClearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa khuyến mãi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mã khuyến mãi không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ClearForm() {
        txtMaKhM.setText("");
        txtTenKhM.setText("");
        txtPhanTramGiam.setText("");
        txtNgayHieuLuc.setText("");
        txtNgayKetThuc.setText("");
        khuyenMaiTable.clearSelection();
        txtTenKhM.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmKhuyenMai frm = new frmKhuyenMai();
            frm.setVisible(true);
        });
    }
}