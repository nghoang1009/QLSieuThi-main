package com.mycompany.qlst.frm;

import com.mycompany.qlst.dao.DanhMucDAO;
import com.mycompany.qlst.dao.SanPhamDAO;
import com.mycompany.qlst.model.DanhMuc;
import com.mycompany.qlst.model.DefaultMenuBar;
import com.mycompany.qlst.model.SanPham;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class frmSanPham extends JFrame {
    private JTable productTable;
    private DefaultListModel<String> categoryListModel;
    private DefaultTableModel tableModel;
    private JTextField txtMaSP, txtTenSP, txtGia, txtSL;
    private JComboBox<String> DanhMucComboBox;
    private DefaultComboBoxModel<String> comboBoxModel;
    private JList<String> list;
    
    // DAO
    private SanPhamDAO sanPhamDAO;
    private DanhMucDAO danhMucDAO;
    
    // Map để lưu maDM tương ứng với tenDM
    private Map<String, Integer> danhMucMap = new HashMap<>();

    public frmSanPham() {
        super("Quản lý sản phẩm");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Tạo MenuBar
        var menuBar = DefaultMenuBar.createMenuBar(this);
        setJMenuBar(menuBar);
        
        // Khởi tạo DAO
        sanPhamDAO = new SanPhamDAO();
        danhMucDAO = new DanhMucDAO();

        JLabel lbTitle = new JLabel("QUẢN LÝ SẢN PHẨM", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        //Panel Left
        JPanel pnLeft = new JPanel(new BorderLayout());
        categoryListModel = new DefaultListModel<>();
        
        list = new JList<>(categoryListModel);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(new TitledBorder(border, "Danh mục sản phẩm"));

        JPanel pnBtn1 = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");

        pnBtn1.add(btnAdd);
        pnBtn1.add(btnUpdate);
        pnBtn1.add(btnDelete);

        pnLeft.add(scrollPane, BorderLayout.CENTER);
        pnLeft.add(pnBtn1, BorderLayout.PAGE_END);

        //Panel Right
        JPanel pnRight = new JPanel(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết"));

        comboBoxModel = new DefaultComboBoxModel<>();
        DanhMucComboBox = new JComboBox<>(comboBoxModel);

        String[] columns = {"Mã SP", "Tên sản phẩm", "Giá", "Số lượng"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        tablePanel.add(tableScrollPane);
        pnRight.add(tablePanel, BorderLayout.PAGE_START);
        tableScrollPane.setPreferredSize(new Dimension(tableScrollPane.getWidth(), 200));

        JPanel pnTT = new JPanel(new GridLayout(5, 2, 5, 3));
        pnTT.setBorder(new TitledBorder(border, "Thông tin sản phẩm"));
        
        JLabel lbDanhMuc = new JLabel("Danh mục:");
        JLabel lbMaSP = new JLabel("Mã sản phẩm:");
        JLabel lbTenSP = new JLabel("Tên sản phẩm:");
        JLabel lbGia = new JLabel("Giá:");
        JLabel lbSL = new JLabel("Số lượng:");

        txtMaSP = new JTextField(30);
        txtMaSP.setEditable(false);
        txtTenSP = new JTextField(30);
        txtGia = new JTextField(30);
        txtSL = new JTextField(30);

        pnTT.add(lbDanhMuc);
        pnTT.add(DanhMucComboBox);
        pnTT.add(lbMaSP);
        pnTT.add(txtMaSP);
        pnTT.add(lbTenSP);
        pnTT.add(txtTenSP);
        pnTT.add(lbGia);
        pnTT.add(txtGia);
        pnTT.add(lbSL);
        pnTT.add(txtSL);

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
        setVisible(true);

        // Load dữ liệu ban đầu
        loadDanhMuc();
        loadAllSanPham();

        // Event handlers cho Danh mục
        btnAdd.addActionListener(e -> ThemDanhMuc());
        btnUpdate.addActionListener(e -> SuaDanhMuc());
        btnDelete.addActionListener(e -> XoaDanhMuc());

        // Event handlers cho Sản phẩm
        btAdd.addActionListener(e -> ThemSP());
        btUpdate.addActionListener(e -> SuaSP());
        btDelete.addActionListener(e -> XoaSP());
        btClear.addActionListener(e -> ClearSP());
        
        // Event khi click vào table
        productTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = productTable.getSelectedRow();
                if (row >= 0) {
                    txtMaSP.setText(tableModel.getValueAt(row, 0).toString());
                    txtTenSP.setText(tableModel.getValueAt(row, 1).toString());
                    txtGia.setText(tableModel.getValueAt(row, 2).toString());
                    txtSL.setText(tableModel.getValueAt(row, 3).toString());
                }
            }
        });
        
        // Event khi chọn danh mục
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCategory = list.getSelectedValue();
                if (selectedCategory != null) {
                    DanhMucComboBox.setSelectedItem(selectedCategory);
                    loadSanPhamByDanhMuc(selectedCategory);
                }
            }
        });
    }

    // Load danh mục từ database
    private void loadDanhMuc() {
        categoryListModel.clear();
        comboBoxModel.removeAllElements();
        danhMucMap.clear();
        
        List<DanhMuc> listDM = danhMucDAO.getAllDanhMuc();
        for (DanhMuc dm : listDM) {
            categoryListModel.addElement(dm.getTenDM());
            comboBoxModel.addElement(dm.getTenDM());
            danhMucMap.put(dm.getTenDM(), dm.getMaDM());
        }
    }

    // Load tất cả sản phẩm
    private void loadAllSanPham() {
        tableModel.setRowCount(0);
        List<SanPham> listSP = sanPhamDAO.getAllSanPham();
        
        for (SanPham sp : listSP) {
            Object[] row = {
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getGia(),
                sp.getSoLuong()
            };
            tableModel.addRow(row);
        }
    }

    // Load sản phẩm theo danh mục
    private void loadSanPhamByDanhMuc(String tenDM) {
        tableModel.setRowCount(0);
        Integer maDM = danhMucMap.get(tenDM);
        if (maDM == null) return;
        
        List<SanPham> listSP = sanPhamDAO.getSanPhamByDanhMuc(maDM);
        for (SanPham sp : listSP) {
            Object[] row = {
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getGia(),
                sp.getSoLuong()
            };
            tableModel.addRow(row);
        }
    }

    // ============ CRUD DANH MỤC ============
    private void ThemDanhMuc() {
        String tenDM = JOptionPane.showInputDialog(this, "Nhập tên danh mục:");
        if (tenDM != null && !tenDM.trim().isEmpty()) {
            DanhMuc dm = new DanhMuc(tenDM);
            if (danhMucDAO.themDanhMuc(dm)) {
                JOptionPane.showMessageDialog(this, "Thêm danh mục thành công!");
                loadDanhMuc();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi thêm danh mục!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void SuaDanhMuc() {
        String selectedDM = list.getSelectedValue();
        if (selectedDM == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục cần sửa!");
            return;
        }
        
        String newName = JOptionPane.showInputDialog(this, "Nhập tên danh mục mới:", selectedDM);
        if (newName != null && !newName.trim().isEmpty()) {
            Integer maDM = danhMucMap.get(selectedDM);
            DanhMuc dm = new DanhMuc(maDM, newName);
            
            if (danhMucDAO.suaDanhMuc(dm)) {
                JOptionPane.showMessageDialog(this, "Sửa danh mục thành công!");
                loadDanhMuc();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi sửa danh mục!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void XoaDanhMuc() {
        String selectedDM = list.getSelectedValue();
        if (selectedDM == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa danh mục này?\n(Sẽ xóa cả các sản phẩm thuộc danh mục)", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            Integer maDM = danhMucMap.get(selectedDM);
            if (danhMucDAO.xoaDanhMuc(maDM)) {
                JOptionPane.showMessageDialog(this, "Xóa danh mục thành công!");
                loadDanhMuc();
                loadAllSanPham();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi xóa danh mục!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ============ CRUD SẢN PHẨM ============
    private void ThemSP() {
        // Validate input
        if (txtTenSP.getText().trim().isEmpty() || 
            txtGia.getText().trim().isEmpty() || 
            txtSL.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        try {
            String tenDM = (String) DanhMucComboBox.getSelectedItem();
            Integer maDM = danhMucMap.get(tenDM);
            String tenSP = txtTenSP.getText().trim();
            int gia = Integer.parseInt(txtGia.getText().trim());
            int soLuong = Integer.parseInt(txtSL.getText().trim());
            
            SanPham sp = new SanPham(maDM, tenSP, gia, soLuong);
            
            if (sanPhamDAO.themSanPham(sp)) {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
                loadAllSanPham();
                ClearSP();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi thêm sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá và số lượng phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void SuaSP() {
        if (txtMaSP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!");
            return;
        }
        
        try {
            int maSP = Integer.parseInt(txtMaSP.getText().trim());
            String tenDM = (String) DanhMucComboBox.getSelectedItem();
            Integer maDM = danhMucMap.get(tenDM);
            String tenSP = txtTenSP.getText().trim();
            int gia = Integer.parseInt(txtGia.getText().trim());
            int soLuong = Integer.parseInt(txtSL.getText().trim());
            
            SanPham sp = new SanPham(maSP, maDM, tenSP, gia, soLuong);
            
            if (sanPhamDAO.suaSanPham(sp)) {
                JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!");
                loadAllSanPham();
                ClearSP();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi sửa sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void XoaSP() {
        if (txtMaSP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa sản phẩm này?", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maSP = Integer.parseInt(txtMaSP.getText().trim());
                if (sanPhamDAO.xoaSanPham(maSP)) {
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
                    loadAllSanPham();
                    ClearSP();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mã sản phẩm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ClearSP() {
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtGia.setText("");
        txtSL.setText("");
        productTable.clearSelection();
        txtTenSP.requestFocus();
    }
    
    // Test chạy form
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmSanPham frm = new frmSanPham();
            frm.setVisible(true);
        });
    }
}