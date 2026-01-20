package com.mycompany.qlst.frm;

import com.mycompany.qlst.dao.GioHangDAO;
import com.mycompany.qlst.dao.ItemGioHangDAO;
import com.mycompany.qlst.dao.KhachHangDAO;
import com.mycompany.qlst.dao.SanPhamDAO;
import com.mycompany.qlst.model.DefaultMenuBar;
import com.mycompany.qlst.model.GioHang;
import com.mycompany.qlst.model.ItemGioHang;
import com.mycompany.qlst.model.KhachHang;
import com.mycompany.qlst.model.SanPham;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class frmGioHang extends JFrame {
    private JTable gioHangTable, itemTable;
    private DefaultTableModel gioHangTableModel, itemTableModel;
    private JTextField txtMaGioHang, txtMaKH, txtTenKH, txtNgayTao;
    private JTextField txtMaSP, txtTenSP, txtGia, txtSoLuong;
    private JLabel lblTongTien;
    private JList<String> listKhachHang;
    private DefaultListModel<String> khachHangListModel;
    
    // DAO
    private GioHangDAO gioHangDAO;
    private ItemGioHangDAO itemGioHangDAO;
    private KhachHangDAO khachHangDAO;
    
    // Map để lưu khách hàng
    private Map<String, Integer> khachHangMap = new HashMap<>();

    public frmGioHang() {
        super("Quản lý giỏ hàng");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Tạo MenuBar
        var menuBar = DefaultMenuBar.createMenuBar(this);
        setJMenuBar(menuBar);
        
        // Khởi tạo DAO
        gioHangDAO = new GioHangDAO();
        itemGioHangDAO = new ItemGioHangDAO();
        khachHangDAO = new KhachHangDAO();
        

        JLabel lbTitle = new JLabel("QUẢN LÝ GIỎ HÀNG", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        //Panel Left
        JPanel pnLeft = new JPanel(new BorderLayout());
        khachHangListModel = new DefaultListModel<>();
        
        listKhachHang = new JList<>(khachHangListModel);
        JScrollPane scrollPane = new JScrollPane(listKhachHang);
        scrollPane.setBorder(new TitledBorder(border, "Danh sách khách hàng"));

        JPanel pnBtn1 = new JPanel();
        JButton btnRefresh = new JButton("Làm mới");
        JButton btnSearch = new JButton("Tìm kiếm");
        
        pnBtn1.add(btnRefresh);
        pnBtn1.add(btnSearch);

        pnLeft.add(scrollPane, BorderLayout.CENTER);
        pnLeft.add(pnBtn1, BorderLayout.PAGE_END);

        //Panel Right
        JPanel pnRight = new JPanel(new BorderLayout());

        // Panel giỏ hàng
        JPanel gioHangPanel = new JPanel(new BorderLayout());
        gioHangPanel.setBorder(BorderFactory.createTitledBorder("Giỏ hàng"));

        String[] gioHangColumns = {"Mã giỏ hàng", "Mã KH", "Tên KH", "Ngày tạo", "Tổng tiền"};
        gioHangTableModel = new DefaultTableModel(gioHangColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        gioHangTable = new JTable(gioHangTableModel);
        JScrollPane gioHangScrollPane = new JScrollPane(gioHangTable);
        gioHangPanel.add(gioHangScrollPane);
        gioHangScrollPane.setPreferredSize(new Dimension(gioHangScrollPane.getWidth(), 120));

        pnRight.add(gioHangPanel, BorderLayout.PAGE_START);

        // Panel items trong giỏ hàng
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết sản phẩm trong giỏ"));

        String[] itemColumns = {"Mã item", "Mã SP", "Tên SP", "Giá", "Số lượng", "Thành tiền"};
        itemTableModel = new DefaultTableModel(itemColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        itemTable = new JTable(itemTableModel);
        JScrollPane itemScrollPane = new JScrollPane(itemTable);
        itemPanel.add(itemScrollPane, BorderLayout.CENTER);
        itemScrollPane.setPreferredSize(new Dimension(itemScrollPane.getWidth(), 100));

        // Panel thông tin
        JPanel pnInfo = new JPanel(new BorderLayout());
        
        JPanel pnTT = new JPanel(new GridLayout(4, 2, 5, 3));
        pnTT.setBorder(new TitledBorder(border, "Thông tin giỏ hàng"));
        
        JLabel lbMaGioHang = new JLabel("Mã giỏ hàng:");
        JLabel lbMaKH = new JLabel("Mã khách hàng:");
        JLabel lbTenKH = new JLabel("Tên khách hàng:");
        JLabel lbNgayTao = new JLabel("Ngày tạo:");

        txtMaGioHang = new JTextField(30);
        txtMaGioHang.setEditable(false);
        txtMaKH = new JTextField(30);
        txtMaKH.setEditable(false);
        txtTenKH = new JTextField(30);
        txtTenKH.setEditable(false);
        txtNgayTao = new JTextField(30);
        txtNgayTao.setEditable(false);

        pnTT.add(lbMaGioHang);
        pnTT.add(txtMaGioHang);
        pnTT.add(lbMaKH);
        pnTT.add(txtMaKH);
        pnTT.add(lbTenKH);
        pnTT.add(txtTenKH);
        pnTT.add(lbNgayTao);
        pnTT.add(txtNgayTao);

        // Panel item form
        JPanel pnItemForm = new JPanel(new GridLayout(4, 2, 5, 3));
        pnItemForm.setBorder(new TitledBorder(border, "Thêm/Sửa sản phẩm"));
        
        JLabel lbMaSP = new JLabel("Mã sản phẩm:");
        JLabel lbTenSP = new JLabel("Tên sản phẩm:");
        JLabel lbGia = new JLabel("Giá:");
        JLabel lbSoLuong = new JLabel("Số lượng:");

        txtMaSP = new JTextField(30);
        txtTenSP = new JTextField(30);
        txtTenSP.setEditable(false);
        txtGia = new JTextField(30);
        txtGia.setEditable(false);
        txtSoLuong = new JTextField(30);

        pnItemForm.add(lbMaSP);
        pnItemForm.add(txtMaSP);
        pnItemForm.add(lbTenSP);
        pnItemForm.add(txtTenSP);
        pnItemForm.add(lbGia);
        pnItemForm.add(txtGia);
        pnItemForm.add(lbSoLuong);
        pnItemForm.add(txtSoLuong);

        pnInfo.add(pnTT, BorderLayout.NORTH);
        pnInfo.add(pnItemForm, BorderLayout.CENTER);
        
        // Panel tổng tiền
        JPanel pnTongTien = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTongTien = new JLabel("Tổng tiền: 0 VNĐ");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 16));
        lblTongTien.setForeground(Color.RED);
        pnTongTien.add(lblTongTien);
        pnInfo.add(pnTongTien, BorderLayout.SOUTH);

        itemPanel.add(pnInfo, BorderLayout.SOUTH);
        pnRight.add(itemPanel, BorderLayout.CENTER);

        //Panel Buttons
        JPanel pnBtn2 = new JPanel();
        JButton btTaoGioHang = new JButton("Tạo giỏ hàng");
        JButton btXoaGioHang = new JButton("Xóa giỏ hàng");
        JButton btThemSP = new JButton("Thêm SP");
        JButton btXoaSP = new JButton("Xóa SP");
        JButton btCapNhatSL = new JButton("Cập nhật SL");
        JButton btClear = new JButton("Xóa trắng");

        pnBtn2.add(btTaoGioHang);
        pnBtn2.add(btXoaGioHang);
        pnBtn2.add(btThemSP);
        pnBtn2.add(btXoaSP);
        pnBtn2.add(btCapNhatSL);
        pnBtn2.add(btClear);

        pnRight.add(pnBtn2, BorderLayout.PAGE_END);

        //JSplitPane
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRight);
        add(jSplitPane);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Load dữ liệu ban đầu
        loadKhachHang();
        loadAllGioHang();

        btnRefresh.addActionListener(e -> {
            loadKhachHang();
            loadAllGioHang();
        });
        btnSearch.addActionListener(e -> TimKiem());
        
        btTaoGioHang.addActionListener(e -> TaoGioHang());
        btXoaGioHang.addActionListener(e -> XoaGioHang());
        btThemSP.addActionListener(e -> ThemSanPham());
        btXoaSP.addActionListener(e -> XoaSanPham());
        btCapNhatSL.addActionListener(e -> CapNhatSoLuong());
        btClear.addActionListener(e -> ClearForm());
        
        // Event khi click vào table giỏ hàng
        gioHangTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = gioHangTable.getSelectedRow();
                if (row >= 0) {
                    txtMaGioHang.setText(gioHangTableModel.getValueAt(row, 0).toString());
                    txtMaKH.setText(gioHangTableModel.getValueAt(row, 1).toString());
                    txtTenKH.setText(gioHangTableModel.getValueAt(row, 2).toString());
                    txtNgayTao.setText(gioHangTableModel.getValueAt(row, 3).toString());
                    
                    loadItemsGioHang(Integer.parseInt(txtMaGioHang.getText()));
                }
            }
        });
        
        // Event khi click vào table items
        itemTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = itemTable.getSelectedRow();
                if (row >= 0) {
                    txtMaSP.setText(itemTableModel.getValueAt(row, 1).toString());
                    txtTenSP.setText(itemTableModel.getValueAt(row, 2).toString());
                    String giaStr = itemTableModel.getValueAt(row, 3).toString().replace(" VNĐ", "");
                    txtGia.setText(giaStr);
                    txtSoLuong.setText(itemTableModel.getValueAt(row, 4).toString());
                }
            }
        });
        
        // Event khi chọn khách hàng
        listKhachHang.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = listKhachHang.getSelectedValue();
                if (selected != null) {
                    Integer maKH = khachHangMap.get(selected);
                    if (maKH != null) {
                        loadGioHangByKhachHang(maKH);
                    }
                }
            }
        });
        
        // Event khi nhập mã sản phẩm
        txtMaSP.addActionListener(e -> loadThongTinSanPham());
    }

    // Load danh sách khách hàng
    private void loadKhachHang() {
        khachHangListModel.clear();
        khachHangMap.clear();
        
        List<KhachHang> listKH = khachHangDAO.getAllKhachHang();
        for (KhachHang kh : listKH) {
            String item = kh.getMaKH() + " - " + kh.getTen();
            khachHangListModel.addElement(item);
            khachHangMap.put(item, kh.getMaKH());
        }
    }

    // Load tất cả giỏ hàng
    private void loadAllGioHang() {
        gioHangTableModel.setRowCount(0);
        List<GioHang> listGH = gioHangDAO.getAllGioHang();
        
        for (GioHang gh : listGH) {
            int tongTien = gioHangDAO.tinhTongTien(gh.getMaGioHang());
            Object[] row = {
                gh.getMaGioHang(),
                gh.getMaKH(),
                gh.getTenKH(),
                gh.getNgayTao(),
                tongTien + " VNĐ"
            };
            gioHangTableModel.addRow(row);
        }
    }

    // Load giỏ hàng theo khách hàng
    private void loadGioHangByKhachHang(int maKH) {
        gioHangTableModel.setRowCount(0);
        List<GioHang> listGH = gioHangDAO.getGioHangByKhachHang(maKH);
        
        for (GioHang gh : listGH) {
            int tongTien = gioHangDAO.tinhTongTien(gh.getMaGioHang());
            Object[] row = {
                gh.getMaGioHang(),
                gh.getMaKH(),
                gh.getTenKH(),
                gh.getNgayTao(),
                tongTien + " VNĐ"
            };
            gioHangTableModel.addRow(row);
        }
    }

    // Load items trong giỏ hàng
    private void loadItemsGioHang(int maGioHang) {
        itemTableModel.setRowCount(0);
        int tongTien = 0;
        
        List<ItemGioHang> listItems = itemGioHangDAO.getItemsByGioHang(maGioHang);
        for (ItemGioHang item : listItems) {
            int thanhTien = item.getGia() * item.getSoLuong();
            tongTien += thanhTien;
            
            Object[] row = {
                item.getMaItemGioHang(),
                item.getMaSP(),
                item.getTenSP(),
                item.getGia() + " VNĐ",
                item.getSoLuong(),
                thanhTien + " VNĐ"
            };
            itemTableModel.addRow(row);
        }
        
        lblTongTien.setText("Tổng tiền: " + tongTien + " VNĐ");
    }

    // Load thông tin sản phẩm
    private void loadThongTinSanPham() {
        String maSPStr = txtMaSP.getText().trim();
        if (maSPStr.isEmpty()) return;
        
        try {
            int maSP = Integer.parseInt(maSPStr);
            SanPham sp = SanPhamDAO.getSanPhamById(maSP);
            
            if (sp != null) {
                txtTenSP.setText(sp.getTenSP());
                txtGia.setText(String.valueOf(sp.getGia()));
                
                if (sp.getSoLuong() == 0) {
                    JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!");
                txtTenSP.setText("");
                txtGia.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Tìm kiếm giỏ hàng
    private void TimKiem() {
        String keyword = JOptionPane.showInputDialog(this, "Nhập tên khách hàng:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            gioHangTableModel.setRowCount(0);
            List<GioHang> listGH = gioHangDAO.timKiemGioHang(keyword);
            
            for (GioHang gh : listGH) {
                int tongTien = gioHangDAO.tinhTongTien(gh.getMaGioHang());
                Object[] row = {
                    gh.getMaGioHang(),
                    gh.getMaKH(),
                    gh.getTenKH(),
                    gh.getNgayTao(),
                    tongTien + " VNĐ"
                };
                gioHangTableModel.addRow(row);
            }
            
            if (gioHangTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy giỏ hàng nào!");
            }
        }
    }

    // ============ CRUD GIỎ HÀNG ============
    private void TaoGioHang() {
        String selected = listKhachHang.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!");
            return;
        }
        
        Integer maKH = khachHangMap.get(selected);
        String ngayTao = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        
        GioHang gh = new GioHang(maKH, Date.valueOf(ngayTao));
        
        if (gioHangDAO.themGioHang(gh)) {
            JOptionPane.showMessageDialog(this, "Tạo giỏ hàng thành công!");
            loadAllGioHang();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi tạo giỏ hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void XoaGioHang() {
        if (txtMaGioHang.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giỏ hàng cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa giỏ hàng này?\n(Sẽ xóa cả các sản phẩm trong giỏ)", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maGioHang = Integer.parseInt(txtMaGioHang.getText().trim());
                
                if (gioHangDAO.xoaGioHang(maGioHang)) {
                    JOptionPane.showMessageDialog(this, "Xóa giỏ hàng thành công!");
                    loadAllGioHang();
                    ClearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa giỏ hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mã giỏ hàng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Thêm sản phẩm vào giỏ hàng
    private void ThemSanPham() {
        if (txtMaGioHang.getText().trim().isEmpty() ||
            txtMaSP.getText().trim().isEmpty() ||
            txtSoLuong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        try {
            int maGioHang = Integer.parseInt(txtMaGioHang.getText().trim());
            int maSP = Integer.parseInt(txtMaSP.getText().trim());
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                return;
            }
            
            // Kiểm tra tồn kho
            SanPham sp = SanPhamDAO.getSanPhamById(maSP);
            if (sp == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!");
                return;
            }
            
            if (soLuong > sp.getSoLuong()) {
                JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho! (Còn: " + sp.getSoLuong() + ")");
                return;
            }
            
            // Kiểm tra sản phẩm đã có trong giỏ chưa
            if (itemGioHangDAO.kiemTraTonTai(maGioHang, maSP)) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã có trong giỏ! Vui lòng cập nhật số lượng.");
                return;
            }
            
            // Thêm sản phẩm vào giỏ
            ItemGioHang item = new ItemGioHang(maGioHang, maSP, soLuong);
            
            if (itemGioHangDAO.themItem(item)) {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào giỏ hàng thành công!");
                loadItemsGioHang(maGioHang);
                loadAllGioHang(); // Cập nhật tổng tiền
                txtMaSP.setText("");
                txtTenSP.setText("");
                txtGia.setText("");
                txtSoLuong.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi thêm sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xóa sản phẩm khỏi giỏ hàng
    private void XoaSanPham() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng?", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maItemGioHang = Integer.parseInt(itemTableModel.getValueAt(selectedRow, 0).toString());
                
                if (itemGioHangDAO.xoaItem(maItemGioHang)) {
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
                    loadItemsGioHang(Integer.parseInt(txtMaGioHang.getText()));
                    loadAllGioHang();
                    txtMaSP.setText("");
                    txtTenSP.setText("");
                    txtGia.setText("");
                    txtSoLuong.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Cập nhật số lượng sản phẩm
    private void CapNhatSoLuong() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần cập nhật!");
            return;
        }
        
        if (txtSoLuong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!");
            return;
        }
        
        try {
            int maItemGioHang = Integer.parseInt(itemTableModel.getValueAt(selectedRow, 0).toString());
            int maSP = Integer.parseInt(txtMaSP.getText().trim());
            int soLuongMoi = Integer.parseInt(txtSoLuong.getText().trim());
            
            if (soLuongMoi <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                return;
            }
            
            // Kiểm tra tồn kho
            SanPham sp = SanPhamDAO.getSanPhamById(maSP);
            if (sp != null && soLuongMoi > sp.getSoLuong()) {
                JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho! (Còn: " + sp.getSoLuong() + ")");
                return;
            }
            
            if (itemGioHangDAO.capNhatSoLuong(maItemGioHang, soLuongMoi)) {
                JOptionPane.showMessageDialog(this, "Cập nhật số lượng thành công!");
                loadItemsGioHang(Integer.parseInt(txtMaGioHang.getText()));
                loadAllGioHang();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ClearForm() {
        txtMaGioHang.setText("");
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtNgayTao.setText("");
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtGia.setText("");
        txtSoLuong.setText("");
        lblTongTien.setText("Tổng tiền: 0 VNĐ");
        gioHangTable.clearSelection();
        itemTable.clearSelection();
        itemTableModel.setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmGioHang frm = new frmGioHang();
            frm.setVisible(true);
        });
    }
}