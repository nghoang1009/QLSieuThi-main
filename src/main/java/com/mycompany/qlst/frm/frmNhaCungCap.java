package com.mycompany.qlst.frm;

import com.mycompany.qlst.dao.NhaCungCapDAO;
import com.mycompany.qlst.model.DefaultMenuBar;
import com.mycompany.qlst.model.NhaCungCap;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class frmNhaCungCap extends JFrame {
    private JTable nccTable;
    private DefaultTableModel tableModel;
    private JTextField txtMaNCC, txtTenNCC, txtDiaChi, txtThanhPho;
    private JList<String> listThanhPho;
    private DefaultListModel<String> thanhPhoListModel;
    
    // DAO
    private NhaCungCapDAO nccDAO;

    public frmNhaCungCap() {
        super("Quản lý nhà cung cấp");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Tạo MenuBar
        var menuBar = DefaultMenuBar.createMenuBar(this);
        setJMenuBar(menuBar);
        
        // Khởi tạo DAO
        nccDAO = new NhaCungCapDAO();

        JLabel lbTitle = new JLabel("QUẢN LÝ NHÀ CUNG CẤP", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        //Panel Left
        JPanel pnLeft = new JPanel(new BorderLayout());
        thanhPhoListModel = new DefaultListModel<>();
        thanhPhoListModel.addElement("Tất cả nhà cung cấp");
        thanhPhoListModel.addElement("Hà Nội");
        thanhPhoListModel.addElement("TP.HCM");
        thanhPhoListModel.addElement("Đà Nẵng");
        thanhPhoListModel.addElement("Hải Phòng");
        
        listThanhPho = new JList<>(thanhPhoListModel);
        JScrollPane scrollPane = new JScrollPane(listThanhPho);
        scrollPane.setBorder(new TitledBorder(border, "Lọc theo thành phố"));

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

        String[] columns = {"Mã NCC", "Tên nhà cung cấp", "Địa chỉ", "Thành phố", "Số SP"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        nccTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(nccTable);
        tablePanel.add(tableScrollPane);
        pnRight.add(tablePanel, BorderLayout.PAGE_START);
        tableScrollPane.setPreferredSize(new Dimension(tableScrollPane.getWidth(), 250));

        JPanel pnTT = new JPanel(new GridLayout(4, 2, 5, 3));
        pnTT.setBorder(new TitledBorder(border, "Thông tin nhà cung cấp"));
        
        JLabel lbMaNCC = new JLabel("Mã nhà cung cấp:");
        JLabel lbTenNCC = new JLabel("Tên nhà cung cấp:");
        JLabel lbDiaChi = new JLabel("Địa chỉ:");
        JLabel lbThanhPho = new JLabel("Thành phố:");

        txtMaNCC = new JTextField(30);
        txtMaNCC.setEditable(false);
        txtTenNCC = new JTextField(30);
        txtDiaChi = new JTextField(30);
        txtThanhPho = new JTextField(30);

        pnTT.add(lbMaNCC);
        pnTT.add(txtMaNCC);
        pnTT.add(lbTenNCC);
        pnTT.add(txtTenNCC);
        pnTT.add(lbDiaChi);
        pnTT.add(txtDiaChi);
        pnTT.add(lbThanhPho);
        pnTT.add(txtThanhPho);

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
        loadAllNhaCungCap();

        // Event handlers
        btnRefresh.addActionListener(e -> loadAllNhaCungCap());
        btnSearch.addActionListener(e -> TimKiem());
        
        btAdd.addActionListener(e -> ThemNhaCungCap());
        btUpdate.addActionListener(e -> SuaNhaCungCap());
        btDelete.addActionListener(e -> XoaNhaCungCap());
        btClear.addActionListener(e -> ClearForm());
        
        // Event khi click vào table
        nccTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = nccTable.getSelectedRow();
                if (row >= 0) {
                    txtMaNCC.setText(tableModel.getValueAt(row, 0).toString());
                    txtTenNCC.setText(tableModel.getValueAt(row, 1).toString());
                    txtDiaChi.setText(tableModel.getValueAt(row, 2).toString());
                    txtThanhPho.setText(tableModel.getValueAt(row, 3).toString());
                }
            }
        });
        
        // Event khi chọn thành phố
        listThanhPho.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = listThanhPho.getSelectedValue();
                if (selected != null) {
                    if (selected.equals("Tất cả nhà cung cấp")) {
                        loadAllNhaCungCap();
                    } else {
                        loadNhaCungCapByThanhPho(selected);
                    }
                }
            }
        });
    }

    // Load tất cả nhà cung cấp
    private void loadAllNhaCungCap() {
        tableModel.setRowCount(0);
        List<NhaCungCap> listNCC = nccDAO.getAllNhaCungCap();
        
        for (NhaCungCap ncc : listNCC) {
            int soSP = nccDAO.demSanPham(ncc.getMaNCC());
            Object[] row = {
                ncc.getMaNCC(),
                ncc.getTenNCC(),
                ncc.getDiaChi(),
                ncc.getThanhPho(),
                soSP
            };
            tableModel.addRow(row);
        }
    }

    // Load nhà cung cấp theo thành phố
    private void loadNhaCungCapByThanhPho(String thanhPho) {
        tableModel.setRowCount(0);
        List<NhaCungCap> listNCC = nccDAO.getNhaCungCapByThanhPho(thanhPho);
        
        for (NhaCungCap ncc : listNCC) {
            int soSP = nccDAO.demSanPham(ncc.getMaNCC());
            Object[] row = {
                ncc.getMaNCC(),
                ncc.getTenNCC(),
                ncc.getDiaChi(),
                ncc.getThanhPho(),
                soSP
            };
            tableModel.addRow(row);
        }
    }

    // Tìm kiếm nhà cung cấp
    private void TimKiem() {
        String keyword = JOptionPane.showInputDialog(this, "Nhập tên nhà cung cấp:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            tableModel.setRowCount(0);
            List<NhaCungCap> listNCC = nccDAO.timKiemNhaCungCap(keyword);
            
            for (NhaCungCap ncc : listNCC) {
                int soSP = nccDAO.demSanPham(ncc.getMaNCC());
                Object[] row = {
                    ncc.getMaNCC(),
                    ncc.getTenNCC(),
                    ncc.getDiaChi(),
                    ncc.getThanhPho(),
                    soSP
                };
                tableModel.addRow(row);
            }
            
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp nào!");
            }
        }
    }

    // ============ CRUD NHÀ CUNG CẤP ============
    private void ThemNhaCungCap() {
        // Validate input
        if (txtTenNCC.getText().trim().isEmpty() ||
            txtDiaChi.getText().trim().isEmpty() ||
            txtThanhPho.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        try {
            NhaCungCap ncc = new NhaCungCap(
                txtTenNCC.getText().trim(),
                txtDiaChi.getText().trim(),
                txtThanhPho.getText().trim()
            );
            
            if (nccDAO.themNhaCungCap(ncc)) {
                JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công!");
                loadAllNhaCungCap();
                ClearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi thêm nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void SuaNhaCungCap() {
        if (txtMaNCC.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần sửa!");
            return;
        }
        
        try {
            int maNCC = Integer.parseInt(txtMaNCC.getText().trim());
            
            NhaCungCap ncc = new NhaCungCap(
                maNCC,
                txtTenNCC.getText().trim(),
                txtDiaChi.getText().trim(),
                txtThanhPho.getText().trim()
            );
            
            if (nccDAO.suaNhaCungCap(ncc)) {
                JOptionPane.showMessageDialog(this, "Sửa nhà cung cấp thành công!");
                loadAllNhaCungCap();
                ClearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi sửa nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void XoaNhaCungCap() {
        if (txtMaNCC.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa nhà cung cấp này?\n(Sẽ xóa cả các sản phẩm liên quan)", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maNCC = Integer.parseInt(txtMaNCC.getText().trim());
                
                if (nccDAO.xoaNhaCungCap(maNCC)) {
                    JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thành công!");
                    loadAllNhaCungCap();
                    ClearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mã nhà cung cấp không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ClearForm() {
        txtMaNCC.setText("");
        txtTenNCC.setText("");
        txtDiaChi.setText("");
        txtThanhPho.setText("");
        nccTable.clearSelection();
        txtTenNCC.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmNhaCungCap frm = new frmNhaCungCap();
            frm.setVisible(true);
        });
    }
}