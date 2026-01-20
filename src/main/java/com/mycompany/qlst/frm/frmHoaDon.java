package com.mycompany.qlst.frm;

import com.mycompany.qlst.dao.HoaDonDAO;
import com.mycompany.qlst.dao.ItemHoaDonDAO;
import com.mycompany.qlst.dao.NhanVienDAO;
import com.mycompany.qlst.dao.SanPhamDAO;
import com.mycompany.qlst.model.DefaultMenuBar;
import com.mycompany.qlst.model.HoaDon;
import com.mycompany.qlst.model.ItemHoaDon;
import com.mycompany.qlst.model.NhanVien;
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

public class frmHoaDon extends JFrame {
    private JTable hoaDonTable, itemTable;
    private DefaultTableModel hoaDonTableModel, itemTableModel;
    private JTextField txtMaHoaDon, txtMaNV, txtTenNV, txtNgayThanhToan;
    private JTextField txtMaSP, txtTenSP, txtGia, txtSoLuong, txtTonKho;
    private JLabel lblTongTien;
    private JList<String> listNhanVien;
    private DefaultListModel<String> nhanVienListModel;
    
    // DAO
    private HoaDonDAO hoaDonDAO;
    private ItemHoaDonDAO itemHoaDonDAO;
    private NhanVienDAO nhanVienDAO;

    
    // Map để lưu nhân viên
    private Map<String, Integer> nhanVienMap = new HashMap<>();

    public frmHoaDon() {
        super("Quản lý hóa đơn");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Tạo MenuBar
        var menuBar = DefaultMenuBar.createMenuBar(this);
        setJMenuBar(menuBar);
        
        // Khởi tạo DAO
        hoaDonDAO = new HoaDonDAO();
        itemHoaDonDAO = new ItemHoaDonDAO();
        nhanVienDAO = new NhanVienDAO();
        

        JLabel lbTitle = new JLabel("QUẢN LÝ HÓA ĐƠN", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        //Panel Left
        JPanel pnLeft = new JPanel(new BorderLayout());
        nhanVienListModel = new DefaultListModel<>();
        
        listNhanVien = new JList<>(nhanVienListModel);
        JScrollPane scrollPane = new JScrollPane(listNhanVien);
        scrollPane.setBorder(new TitledBorder(border, "Danh sách nhân viên"));

        JPanel pnBtn1 = new JPanel();
        JButton btnRefresh = new JButton("Làm mới");
        JButton btnSearch = new JButton("Tìm kiếm");
        
        pnBtn1.add(btnRefresh);
        pnBtn1.add(btnSearch);

        pnLeft.add(scrollPane, BorderLayout.CENTER);
        pnLeft.add(pnBtn1, BorderLayout.PAGE_END);

        //Panel Right
        JPanel pnRight = new JPanel(new BorderLayout());

        // Panel hóa đơn
        JPanel hoaDonPanel = new JPanel(new BorderLayout());
        hoaDonPanel.setBorder(BorderFactory.createTitledBorder("Hóa đơn"));

        String[] hoaDonColumns = {"Mã HD", "Mã NV", "Tên NV", "Ngày thanh toán", "Tổng tiền"};
        hoaDonTableModel = new DefaultTableModel(hoaDonColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        hoaDonTable = new JTable(hoaDonTableModel);
        JScrollPane hoaDonScrollPane = new JScrollPane(hoaDonTable);
        hoaDonPanel.add(hoaDonScrollPane);
        hoaDonScrollPane.setPreferredSize(new Dimension(hoaDonScrollPane.getWidth(), 120));

        pnRight.add(hoaDonPanel, BorderLayout.PAGE_START);

        // Panel items trong hóa đơn
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết sản phẩm trong hóa đơn"));

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
        pnTT.setBorder(new TitledBorder(border, "Thông tin hóa đơn"));
        
        JLabel lbMaHoaDon = new JLabel("Mã hóa đơn:");
        JLabel lbMaNV = new JLabel("Mã nhân viên:");
        JLabel lbTenNV = new JLabel("Tên nhân viên:");
        JLabel lbNgayThanhToan = new JLabel("Ngày thanh toán:");

        txtMaHoaDon = new JTextField(30);
        txtMaHoaDon.setEditable(false);
        txtMaNV = new JTextField(30);
        txtTenNV = new JTextField(30);
        txtTenNV.setEditable(false);
        txtNgayThanhToan = new JTextField(30);
        txtNgayThanhToan.setEditable(false);

        pnTT.add(lbMaHoaDon);
        pnTT.add(txtMaHoaDon);
        pnTT.add(lbMaNV);
        pnTT.add(txtMaNV);
        pnTT.add(lbTenNV);
        pnTT.add(txtTenNV);
        pnTT.add(lbNgayThanhToan);
        pnTT.add(txtNgayThanhToan);

        // Panel item form
        JPanel pnItemForm = new JPanel(new GridLayout(5, 2, 5, 3));
        pnItemForm.setBorder(new TitledBorder(border, "Thêm/Sửa sản phẩm"));
        
        JLabel lbMaSP = new JLabel("Mã sản phẩm:");
        JLabel lbTenSP = new JLabel("Tên sản phẩm:");
        JLabel lbGia = new JLabel("Giá:");
        JLabel lbTonKho = new JLabel("Tồn kho:");
        JLabel lbSoLuong = new JLabel("Số lượng:");

        txtMaSP = new JTextField(30);
        txtTenSP = new JTextField(30);
        txtTenSP.setEditable(false);
        txtGia = new JTextField(30);
        txtGia.setEditable(false);
        txtTonKho = new JTextField(30);
        txtTonKho.setEditable(false);
        txtSoLuong = new JTextField(30);

        pnItemForm.add(lbMaSP);
        pnItemForm.add(txtMaSP);
        pnItemForm.add(lbTenSP);
        pnItemForm.add(txtTenSP);
        pnItemForm.add(lbGia);
        pnItemForm.add(txtGia);
        pnItemForm.add(lbTonKho);
        pnItemForm.add(txtTonKho);
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
        JButton btTaoHoaDon = new JButton("Tạo hóa đơn");
        JButton btXoaHoaDon = new JButton("Xóa hóa đơn");
        JButton btThemSP = new JButton("Thêm SP");
        JButton btXoaSP = new JButton("Xóa SP");
        JButton btCapNhatSL = new JButton("Cập nhật SL");
        JButton btClear = new JButton("Xóa trắng");

        pnBtn2.add(btTaoHoaDon);
        pnBtn2.add(btXoaHoaDon);
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
        loadNhanVien();
        loadAllHoaDon();

        // Event handlers
        btnRefresh.addActionListener(e -> {
            loadNhanVien();
            loadAllHoaDon();
        });
        btnSearch.addActionListener(e -> TimKiem());
        
        btTaoHoaDon.addActionListener(e -> TaoHoaDon());
        btXoaHoaDon.addActionListener(e -> XoaHoaDon());
        btThemSP.addActionListener(e -> ThemSanPham());
        btXoaSP.addActionListener(e -> XoaSanPham());
        btCapNhatSL.addActionListener(e -> CapNhatSoLuong());
        btClear.addActionListener(e -> ClearForm());
        
        // Event khi click vào table hóa đơn
        hoaDonTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = hoaDonTable.getSelectedRow();
                if (row >= 0) {
                    txtMaHoaDon.setText(hoaDonTableModel.getValueAt(row, 0).toString());
                    txtMaNV.setText(hoaDonTableModel.getValueAt(row, 1).toString());
                    txtTenNV.setText(hoaDonTableModel.getValueAt(row, 2).toString());
                    txtNgayThanhToan.setText(hoaDonTableModel.getValueAt(row, 3).toString());
                    
                    loadItemsHoaDon(Integer.parseInt(txtMaHoaDon.getText()));
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
                    
                    // Load tồn kho hiện tại
                    loadThongTinSanPham();
                }
            }
        });
        
        // Event khi chọn nhân viên
        listNhanVien.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = listNhanVien.getSelectedValue();
                if (selected != null) {
                    Integer maNV = nhanVienMap.get(selected);
                    if (maNV != null) {
                        loadHoaDonByNhanVien(maNV);
                    }
                }
            }
        });
        
        // Event khi nhập mã sản phẩm
        txtMaSP.addActionListener(e -> loadThongTinSanPham());
        
        // Event khi nhập mã nhân viên
        txtMaNV.addActionListener(e -> loadThongTinNhanVien());
    }

    // Load danh sách nhân viên
    private void loadNhanVien() {
        nhanVienListModel.clear();
        nhanVienMap.clear();
        
        List<NhanVien> listNV = nhanVienDAO.getAllNhanVien();
        for (NhanVien nv : listNV) {
            String item = nv.getMaNV() + " - " + nv.getTen();
            nhanVienListModel.addElement(item);
            nhanVienMap.put(item, nv.getMaNV());
        }
    }

    // Load tất cả hóa đơn
    private void loadAllHoaDon() {
        hoaDonTableModel.setRowCount(0);
        List<HoaDon> listHD = hoaDonDAO.getAllHoaDon();
        
        for (HoaDon hd : listHD) {
            int tongTien = hoaDonDAO.tinhTongTien(hd.getMaHoaDon());
            Object[] row = {
                hd.getMaHoaDon(),
                hd.getMaNV(),
                hd.getTenNV(),
                hd.getNgayThanhToan(),
                tongTien + " VNĐ"
            };
            hoaDonTableModel.addRow(row);
        }
    }

    // Load hóa đơn theo nhân viên
    private void loadHoaDonByNhanVien(int maNV) {
        hoaDonTableModel.setRowCount(0);
        List<HoaDon> listHD = hoaDonDAO.getHoaDonByNhanVien(maNV);
        
        for (HoaDon hd : listHD) {
            int tongTien = hoaDonDAO.tinhTongTien(hd.getMaHoaDon());
            Object[] row = {
                hd.getMaHoaDon(),
                hd.getMaNV(),
                hd.getTenNV(),
                hd.getNgayThanhToan(),
                tongTien + " VNĐ"
            };
            hoaDonTableModel.addRow(row);
        }
    }

    // Load items trong hóa đơn
    private void loadItemsHoaDon(int maHoaDon) {
        itemTableModel.setRowCount(0);
        int tongTien = 0;
        
        List<ItemHoaDon> listItems = itemHoaDonDAO.getItemsByHoaDon(maHoaDon);
        for (ItemHoaDon item : listItems) {
            int thanhTien = item.getGia() * item.getSoLuong();
            tongTien += thanhTien;
            
            Object[] row = {
                item.getMaItemHoaDon(),
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

    // Load thông tin nhân viên
    private void loadThongTinNhanVien() {
        String maNVStr = txtMaNV.getText().trim();
        if (maNVStr.isEmpty()) return;
        
        try {
            int maNV = Integer.parseInt(maNVStr);
            NhanVien nv = nhanVienDAO.getNhanVienById(maNV);
            
            if (nv != null) {
                txtTenNV.setText(nv.getTen());
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
                txtTenNV.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
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
                txtTonKho.setText(String.valueOf(sp.getSoLuong()));
                
                if (sp.getSoLuong() == 0) {
                    JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!");
                txtTenSP.setText("");
                txtGia.setText("");
                txtTonKho.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Tìm kiếm hóa đơn
    private void TimKiem() {
        String keyword = JOptionPane.showInputDialog(this, "Nhập mã hoặc tên nhân viên:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            hoaDonTableModel.setRowCount(0);
            List<HoaDon> listHD = hoaDonDAO.timKiemHoaDon(keyword);
            
            for (HoaDon hd : listHD) {
                int tongTien = hoaDonDAO.tinhTongTien(hd.getMaHoaDon());
                Object[] row = {
                    hd.getMaHoaDon(),
                    hd.getMaNV(),
                    hd.getTenNV(),
                    hd.getNgayThanhToan(),
                    tongTien + " VNĐ"
                };
                hoaDonTableModel.addRow(row);
            }
            
            if (hoaDonTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn nào!");
            }
        }
    }

    // ============ CRUD HÓA ĐƠN ============
    private void TaoHoaDon() {
        if (txtMaNV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên!");
            return;
        }
        
        try {
            int maNV = Integer.parseInt(txtMaNV.getText().trim());
            
            // Kiểm tra nhân viên có tồn tại
            NhanVien nv = nhanVienDAO.getNhanVienById(maNV);
            if (nv == null) {
                JOptionPane.showMessageDialog(this, "Nhân viên không tồn tại!");
                return;
            }
            
            String ngayThanhToan = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
            
            HoaDon hd = new HoaDon(maNV, Date.valueOf(ngayThanhToan));
            
            if (hoaDonDAO.themHoaDon(hd)) {
                JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công!");
                loadAllHoaDon();
                ClearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi tạo hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void XoaHoaDon() {
        if (txtMaHoaDon.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa hóa đơn này?\n(Sẽ xóa cả các sản phẩm trong hóa đơn và hoàn trả tồn kho)", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maHoaDon = Integer.parseInt(txtMaHoaDon.getText().trim());
                
                if (hoaDonDAO.xoaHoaDon(maHoaDon)) {
                    JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!");
                    loadAllHoaDon();
                    ClearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mã hóa đơn không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Thêm sản phẩm vào hóa đơn (tự động trừ tồn kho)
    private void ThemSanPham() {
        if (txtMaHoaDon.getText().trim().isEmpty() ||
            txtMaSP.getText().trim().isEmpty() ||
            txtSoLuong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        try {
            int maHoaDon = Integer.parseInt(txtMaHoaDon.getText().trim());
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
            
            // Kiểm tra sản phẩm đã có trong hóa đơn chưa
            if (itemHoaDonDAO.kiemTraTonTai(maHoaDon, maSP)) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã có trong hóa đơn! Vui lòng cập nhật số lượng.");
                return;
            }
            
            // Thêm sản phẩm vào hóa đơn (tự động trừ kho)
            ItemHoaDon item = new ItemHoaDon(maHoaDon, maSP, soLuong);
            
            if (itemHoaDonDAO.themItem(item)) {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào hóa đơn thành công!\nĐã trừ " + soLuong + " sản phẩm khỏi kho.");
                loadItemsHoaDon(maHoaDon);
                loadAllHoaDon(); // Cập nhật tổng tiền
                txtMaSP.setText("");
                txtTenSP.setText("");
                txtGia.setText("");
                txtTonKho.setText("");
                txtSoLuong.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi thêm sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xóa sản phẩm khỏi hóa đơn (tự động hoàn trả tồn kho)
    private void XoaSanPham() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa sản phẩm này khỏi hóa đơn?\n(Sẽ hoàn trả số lượng vào kho)", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maItemHoaDon = Integer.parseInt(itemTableModel.getValueAt(selectedRow, 0).toString());
                int maSP = Integer.parseInt(itemTableModel.getValueAt(selectedRow, 1).toString());
                int soLuong = Integer.parseInt(itemTableModel.getValueAt(selectedRow, 4).toString());
                
                if (itemHoaDonDAO.xoaItem(maItemHoaDon, maSP, soLuong)) {
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!\nĐã hoàn trả " + soLuong + " sản phẩm vào kho.");
                    loadItemsHoaDon(Integer.parseInt(txtMaHoaDon.getText()));
                    loadAllHoaDon();
                    txtMaSP.setText("");
                    txtTenSP.setText("");
                    txtGia.setText("");
                    txtTonKho.setText("");
                    txtSoLuong.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Cập nhật số lượng sản phẩm (tự động cập nhật tồn kho)
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
            int maItemHoaDon = Integer.parseInt(itemTableModel.getValueAt(selectedRow, 0).toString());
            int maSP = Integer.parseInt(txtMaSP.getText().trim());
            int soLuongCu = Integer.parseInt(itemTableModel.getValueAt(selectedRow, 4).toString());
            int soLuongMoi = Integer.parseInt(txtSoLuong.getText().trim());
            
            if (soLuongMoi <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                return;
            }
            
            // Kiểm tra tồn kho
            SanPham sp = SanPhamDAO.getSanPhamById(maSP);
            if (sp != null) {
                // Tính số lượng có thể thêm = tồn kho hiện tại + số lượng cũ
                int soLuongCoThe = sp.getSoLuong() + soLuongCu;
                if (soLuongMoi > soLuongCoThe) {
                    JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho!\n" +
                        "(Tồn kho hiện tại: " + sp.getSoLuong() + 
                        ", Đã bán: " + soLuongCu + 
                        ", Có thể bán tối đa: " + soLuongCoThe + ")");
                    return;
                }
            }
            
            if (itemHoaDonDAO.capNhatSoLuong(maItemHoaDon, maSP, soLuongCu, soLuongMoi)) {
                int chenhLech = soLuongMoi - soLuongCu;
                String message = "Cập nhật số lượng thành công!";
                if (chenhLech > 0) {
                    message += "\nĐã trừ thêm " + chenhLech + " sản phẩm khỏi kho.";
                } else if (chenhLech < 0) {
                    message += "\nĐã hoàn trả " + Math.abs(chenhLech) + " sản phẩm vào kho.";
                }
                JOptionPane.showMessageDialog(this, message);
                loadItemsHoaDon(Integer.parseInt(txtMaHoaDon.getText()));
                loadAllHoaDon();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ClearForm() {
        txtMaHoaDon.setText("");
        txtMaNV.setText("");
        txtTenNV.setText("");
        txtNgayThanhToan.setText("");
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtGia.setText("");
        txtTonKho.setText("");
        txtSoLuong.setText("");
        lblTongTien.setText("Tổng tiền: 0 VNĐ");
        hoaDonTable.clearSelection();
        itemTable.clearSelection();
        itemTableModel.setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmHoaDon frm = new frmHoaDon();
            frm.setVisible(true);
        });
    }
}