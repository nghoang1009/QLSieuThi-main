package com.mycompany.qlst.frm;

import com.mycompany.qlst.dao.GiaoHangDAO;
import com.mycompany.qlst.dao.ItemGiaoHangDAO;
import com.mycompany.qlst.dao.KhachHangDAO;
import com.mycompany.qlst.dao.SanPhamDAO;
import com.mycompany.qlst.model.DefaultMenuBar;
import com.mycompany.qlst.model.GiaoHang;
import com.mycompany.qlst.model.ItemGiaoHang;
import com.mycompany.qlst.model.KhachHang;
import com.mycompany.qlst.model.SanPham;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class frmGiaoHang extends JFrame {
    private JTable giaoHangTable, itemTable;
    private DefaultTableModel giaoHangTableModel, itemTableModel;
    private JTextField txtMaGiaoHang, txtMaKH, txtTenKH, txtNgayTao;
    private JComboBox<String> cboTinhTrang;
    private JTextField txtMaSP, txtTenSP, txtGia, txtSoLuong;
    private JLabel lblTongTien;
    private JList<String> listTinhTrang;
    private DefaultListModel<String> tinhTrangListModel;
    
    // DAO
    private GiaoHangDAO giaoHangDAO;
    private ItemGiaoHangDAO itemGiaoHangDAO;
    private KhachHangDAO khachHangDAO;


    public frmGiaoHang() {
        super("Quản lý giao hàng");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Tạo MenuBar
        var menuBar = DefaultMenuBar.createMenuBar(this);
        setJMenuBar(menuBar);
        
        // Khởi tạo DAO
        giaoHangDAO = new GiaoHangDAO();
        itemGiaoHangDAO = new ItemGiaoHangDAO();
        khachHangDAO = new KhachHangDAO();

        
        JLabel lbTitle = new JLabel("QUẢN LÝ GIAO HÀNG", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        //Panel Left
        JPanel pnLeft = new JPanel(new BorderLayout());
        tinhTrangListModel = new DefaultListModel<>();
        tinhTrangListModel.addElement("Tất cả đơn hàng");
        tinhTrangListModel.addElement("Chờ xử lý");
        tinhTrangListModel.addElement("Đang giao");
        tinhTrangListModel.addElement("Đã giao");
        tinhTrangListModel.addElement("Đã hủy");
        
        listTinhTrang = new JList<>(tinhTrangListModel);
        JScrollPane scrollPane = new JScrollPane(listTinhTrang);
        scrollPane.setBorder(new TitledBorder(border, "Lọc theo tình trạng"));

        JPanel pnBtn1 = new JPanel();
        JButton btnRefresh = new JButton("Làm mới");
        JButton btnSearch = new JButton("Tìm kiếm");
        
        pnBtn1.add(btnRefresh);
        pnBtn1.add(btnSearch);

        pnLeft.add(scrollPane, BorderLayout.CENTER);
        pnLeft.add(pnBtn1, BorderLayout.PAGE_END);

        //Panel Right
        JPanel pnRight = new JPanel(new BorderLayout());

        // Panel đơn giao hàng
        JPanel giaoHangPanel = new JPanel(new BorderLayout());
        giaoHangPanel.setBorder(BorderFactory.createTitledBorder("Đơn giao hàng"));

        String[] giaoHangColumns = {"Mã GH", "Mã KH", "Tên KH", "Ngày tạo", "Tình trạng", "Tổng tiền"};
        giaoHangTableModel = new DefaultTableModel(giaoHangColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        giaoHangTable = new JTable(giaoHangTableModel);
        JScrollPane giaoHangScrollPane = new JScrollPane(giaoHangTable);
        giaoHangPanel.add(giaoHangScrollPane);
        giaoHangScrollPane.setPreferredSize(new Dimension(giaoHangScrollPane.getWidth(), 120));

        pnRight.add(giaoHangPanel, BorderLayout.PAGE_START);

        // Panel items trong đơn giao hàng
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết sản phẩm trong đơn"));

        String[] itemColumns = {"Mã SP", "Tên SP", "Giá", "Số lượng", "Thành tiền"};
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
        
        JPanel pnTT = new JPanel(new GridLayout(5, 2, 5, 3));
        pnTT.setBorder(new TitledBorder(border, "Thông tin đơn hàng"));
        
        JLabel lbMaGiaoHang = new JLabel("Mã giao hàng:");
        JLabel lbMaKH = new JLabel("Mã khách hàng:");
        JLabel lbTenKH = new JLabel("Tên khách hàng:");
        JLabel lbNgayTao = new JLabel("Ngày tạo:");
        JLabel lbTinhTrang = new JLabel("Tình trạng:");

        txtMaGiaoHang = new JTextField(30);
        txtMaGiaoHang.setEditable(false);
        txtMaKH = new JTextField(30);
        txtTenKH = new JTextField(30);
        txtNgayTao = new JTextField(30);
        txtNgayTao.setEditable(false);
        
        cboTinhTrang = new JComboBox<>(new String[]{"Chờ xử lý", "Đang giao", "Đã giao", "Đã hủy"});

        pnTT.add(lbMaGiaoHang);
        pnTT.add(txtMaGiaoHang);
        pnTT.add(lbMaKH);
        pnTT.add(txtMaKH);
        pnTT.add(lbTenKH);
        pnTT.add(txtTenKH);
        pnTT.add(lbNgayTao);
        pnTT.add(txtNgayTao);
        pnTT.add(lbTinhTrang);
        pnTT.add(cboTinhTrang);

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
        JButton btTaoDon = new JButton("Tạo đơn");
        JButton btCapNhatTT = new JButton("Cập nhật TT");
        JButton btXoaDon = new JButton("Xóa đơn");
        JButton btThemSP = new JButton("Thêm SP");
        JButton btXoaSP = new JButton("Xóa SP");
        JButton btCapNhatSL = new JButton("Cập nhật SL");
        JButton btClear = new JButton("Xóa trắng");

        pnBtn2.add(btTaoDon);
        pnBtn2.add(btCapNhatTT);
        pnBtn2.add(btXoaDon);
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
        loadAllGiaoHang();

        // Event handlers
        btnRefresh.addActionListener(e -> loadAllGiaoHang());
        btnSearch.addActionListener(e -> TimKiem());
        
        btTaoDon.addActionListener(e -> TaoDonGiaoHang());
        btCapNhatTT.addActionListener(e -> CapNhatTinhTrang());
        btXoaDon.addActionListener(e -> XoaDonGiaoHang());
        btThemSP.addActionListener(e -> ThemSanPham());
        btXoaSP.addActionListener(e -> XoaSanPham());
        btCapNhatSL.addActionListener(e -> CapNhatSoLuong());
        btClear.addActionListener(e -> ClearForm());
        
        // Event khi click vào table đơn giao hàng
        giaoHangTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = giaoHangTable.getSelectedRow();
                if (row >= 0) {
                    txtMaGiaoHang.setText(giaoHangTableModel.getValueAt(row, 0).toString());
                    txtMaKH.setText(giaoHangTableModel.getValueAt(row, 1).toString());
                    txtTenKH.setText(giaoHangTableModel.getValueAt(row, 2).toString());
                    txtNgayTao.setText(giaoHangTableModel.getValueAt(row, 3).toString());
                    cboTinhTrang.setSelectedItem(giaoHangTableModel.getValueAt(row, 4).toString());
                    
                    loadItemsGiaoHang(Integer.parseInt(txtMaGiaoHang.getText()));
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
        
        // Event khi chọn tình trạng
        listTinhTrang.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = listTinhTrang.getSelectedValue();
                if (selected != null) {
                    if (selected.equals("Tất cả đơn hàng")) {
                        loadAllGiaoHang();
                    } else {
                        loadGiaoHangByTinhTrang(selected);
                    }
                }
            }
        });
        
        // Event khi nhập mã sản phẩm
        txtMaSP.addActionListener(e -> loadThongTinSanPham());
        
        // Event khi nhập mã khách hàng
        txtMaKH.addActionListener(e -> loadThongTinKhachHang());
    }

    // Load tất cả đơn giao hàng
    private void loadAllGiaoHang() {
        giaoHangTableModel.setRowCount(0);
        List<GiaoHang> listGH = giaoHangDAO.getAllGiaoHang();
        
        for (GiaoHang gh : listGH) {
            int tongTien = giaoHangDAO.tinhTongTien(gh.getMaGiaoHang());
            Object[] row = {
                gh.getMaGiaoHang(),
                gh.getMaKH(),
                gh.getTenKH(),
                gh.getNgayTao(),
                gh.getTinhTrang(),
                tongTien + " VNĐ"
            };
            giaoHangTableModel.addRow(row);
        }
    }

    // Load đơn giao hàng theo tình trạng
    private void loadGiaoHangByTinhTrang(String tinhTrang) {
        giaoHangTableModel.setRowCount(0);
        List<GiaoHang> listGH = giaoHangDAO.getGiaoHangByTinhTrang(tinhTrang);
        
        for (GiaoHang gh : listGH) {
            int tongTien = giaoHangDAO.tinhTongTien(gh.getMaGiaoHang());
            Object[] row = {
                gh.getMaGiaoHang(),
                gh.getMaKH(),
                gh.getTenKH(),
                gh.getNgayTao(),
                gh.getTinhTrang(),
                tongTien + " VNĐ"
            };
            giaoHangTableModel.addRow(row);
        }
    }

    // Load sản phẩm trong đơn giao hàng
    private void loadItemsGiaoHang(int maGiaoHang) {
        itemTableModel.setRowCount(0);
        int tongTien = 0;
        
        List<ItemGiaoHang> listItems = itemGiaoHangDAO.getItemsByGiaoHang(maGiaoHang);
        for (ItemGiaoHang item : listItems) {
            int thanhTien = item.getGia() * item.getSoLuong();
            tongTien += thanhTien;
            
            Object[] row = {
                item.getMaItemGiaoHang(),
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

    // Load thông tin khách hàng
    private void loadThongTinKhachHang() {
        String maKHStr = txtMaKH.getText().trim();
        if (maKHStr.isEmpty()) return;
        
        try {
            int maKH = Integer.parseInt(maKHStr);
            KhachHang kh = khachHangDAO.getKhachHangById(maKH);
            
            if (kh != null) {
                txtTenKH.setText(kh.getTen());
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
                txtTenKH.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    // Tìm kiếm đơn giao hàng
    private void TimKiem() {
        String keyword = JOptionPane.showInputDialog(this, "Nhập tên khách hàng:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            giaoHangTableModel.setRowCount(0);
            List<GiaoHang> listGH = giaoHangDAO.timKiemGiaoHang(keyword);
            
            for (GiaoHang gh : listGH) {
                int tongTien = giaoHangDAO.tinhTongTien(gh.getMaGiaoHang());
                Object[] row = {
                    gh.getMaGiaoHang(),
                    gh.getMaKH(),
                    gh.getTenKH(),
                    gh.getNgayTao(),
                    gh.getTinhTrang(),
                    tongTien + " VNĐ"
                };
                giaoHangTableModel.addRow(row);
            }
            
            if (giaoHangTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy đơn giao hàng nào!");
            }
        }
    }

    // ============ CRUD ĐƠN GIAO HÀNG ============
    private void TaoDonGiaoHang() {
        if (txtMaKH.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khách hàng!");
            return;
        }
        
        try {
            int maKH = Integer.parseInt(txtMaKH.getText().trim());
            
            // Kiểm tra khách hàng có tồn tại
            KhachHang kh = khachHangDAO.getKhachHangById(maKH);
            if (kh == null) {
                JOptionPane.showMessageDialog(this, "Khách hàng không tồn tại!");
                return;
            }
            
            String ngayTao = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
            String tinhTrang = (String) cboTinhTrang.getSelectedItem();
            
            GiaoHang gh = new GiaoHang(maKH, Date.valueOf(ngayTao), tinhTrang);
            
            if (giaoHangDAO.themGiaoHang(gh)) {
                JOptionPane.showMessageDialog(this, "Tạo đơn giao hàng thành công!");
                loadAllGiaoHang();
                ClearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi tạo đơn giao hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void CapNhatTinhTrang() {
        if (txtMaGiaoHang.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn giao hàng!");
            return;
        }
        
        try {
            int maGiaoHang = Integer.parseInt(txtMaGiaoHang.getText().trim());
            String tinhTrang = (String) cboTinhTrang.getSelectedItem();
            
            if (giaoHangDAO.capNhatTinhTrang(maGiaoHang, tinhTrang)) {
                JOptionPane.showMessageDialog(this, "Cập nhật tình trạng thành công!");
                loadAllGiaoHang();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật tình trạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã đơn giao hàng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void XoaDonGiaoHang() {
        if (txtMaGiaoHang.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn giao hàng cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa đơn giao hàng này?\n(Sẽ xóa cả các sản phẩm trong đơn)", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maGiaoHang = Integer.parseInt(txtMaGiaoHang.getText().trim());
                
                if (giaoHangDAO.xoaGiaoHang(maGiaoHang)) {
                    JOptionPane.showMessageDialog(this, "Xóa đơn giao hàng thành công!");
                    loadAllGiaoHang();
                    ClearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa đơn giao hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mã đơn giao hàng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Thêm sản phẩm vào đơn giao hàng
    private void ThemSanPham() {
        if (txtMaGiaoHang.getText().trim().isEmpty() ||
            txtMaSP.getText().trim().isEmpty() ||
            txtSoLuong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        try {
            int maGiaoHang = Integer.parseInt(txtMaGiaoHang.getText().trim());
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
            
            // Kiểm tra sản phẩm đã có trong đơn chưa
            if (itemGiaoHangDAO.kiemTraTonTai(maGiaoHang, maSP)) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã có trong đơn! Vui lòng cập nhật số lượng.");
                return;
            }
            
            // Thêm sản phẩm vào đơn
            ItemGiaoHang item = new ItemGiaoHang(maGiaoHang, maSP, soLuong);
            
            if (itemGiaoHangDAO.themItem(item)) {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào đơn thành công!");
                loadItemsGiaoHang(maGiaoHang);
                loadAllGiaoHang(); // Cập nhật tổng tiền
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

    // Xóa sản phẩm khỏi đơn giao hàng
    private void XoaSanPham() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa sản phẩm này khỏi đơn giao hàng?", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maItemGiaoHang = Integer.parseInt(itemTableModel.getValueAt(selectedRow, 0).toString());
                
                if (itemGiaoHangDAO.xoaItem(maItemGiaoHang)) {
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
                    loadItemsGiaoHang(Integer.parseInt(txtMaGiaoHang.getText()));
                    loadAllGiaoHang();
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
            int maItemGiaoHang = Integer.parseInt(itemTableModel.getValueAt(selectedRow, 0).toString());
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
            
            if (itemGiaoHangDAO.capNhatSoLuong(maItemGiaoHang, soLuongMoi)) {
                JOptionPane.showMessageDialog(this, "Cập nhật số lượng thành công!");
                loadItemsGiaoHang(Integer.parseInt(txtMaGiaoHang.getText()));
                loadAllGiaoHang();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ClearForm() {
        txtMaGiaoHang.setText("");
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtNgayTao.setText("");
        cboTinhTrang.setSelectedIndex(0);
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtGia.setText("");
        txtSoLuong.setText("");
        lblTongTien.setText("Tổng tiền: 0 VNĐ");
        giaoHangTable.clearSelection();
        itemTable.clearSelection();
        itemTableModel.setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmGiaoHang frm = new frmGiaoHang();
            frm.setVisible(true);
        });
    }
}