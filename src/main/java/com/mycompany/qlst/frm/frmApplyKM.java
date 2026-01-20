package com.mycompany.qlst.frm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import com.mycompany.qlst.dao.ApDungKMDAO;
import com.mycompany.qlst.dao.KhuyenMaiDAO;
import com.mycompany.qlst.dao.SanPhamDAO;
import com.mycompany.qlst.model.ApDungKhuyenMai;
import com.mycompany.qlst.model.ApDungKMTableModel;
import com.mycompany.qlst.model.DefaultMenuBar;
import com.mycompany.qlst.model.KhuyenMai;
import com.mycompany.qlst.model.SanPham;

public class frmApplyKM extends JFrame {
    private DefaultListModel<KhuyenMai> listKMModel;
    private DefaultListModel<SanPham> listSPModel;
    private ApDungKMTableModel tableModel;
    private List<ApDungKhuyenMai> listApDung;

    private JList<KhuyenMai> listKhuyenMai;
    private JList<SanPham> listSanPham;
    private JTable tableApDung;

    public frmApplyKM(String title) {
        super(title);
        setLayout(new BorderLayout());

        // Tạo MenuBar
        var menuBar = DefaultMenuBar.createMenuBar(this);
        setJMenuBar(menuBar);
        
        // Title
        var formTitle = new JLabel("QUẢN LÝ SẢN PHẨM ĐƯỢC KHUYẾN MÃI", JLabel.CENTER);
        var font = new Font("Arial", Font.BOLD, 20);
        
        // Tạo danh sách sản phẩm và khuyến mãi
        var border = BorderFactory.createLineBorder(Color.BLACK, 2);
        var listKhuyenMaiBorder = new TitledBorder(border, "Khuyến mãi", TitledBorder.CENTER, TitledBorder.TOP);
        var listSanPhamBorder = new TitledBorder(border, "Sản phẩm", TitledBorder.CENTER, TitledBorder.TOP);

        var gridLayout = new JPanel(new GridLayout(1, 2, 5, 0));

        var khuyenMaiBox = new JPanel();
        var sanPhamBox = new JPanel();

        listKMModel = new DefaultListModel<KhuyenMai>();
        listSPModel = new DefaultListModel<SanPham>();

        listKhuyenMai = new JList<>(listKMModel);
        var scrollListKM = new JScrollPane(listKhuyenMai);

        listSanPham = new JList<>(listSPModel);
        var scrollListSP = new JScrollPane(listSanPham);

        var searchKM = new JButton("Tìm khuyến mãi");
        var searchSP = new JButton("Tìm sản phẩm");
        var resetKM = new JButton("Reset khuyến mãi");
        var resetSP = new JButton("Reset sản phẩm");

        // Tạo bảng áp dụng
        listApDung = ApDungKMDAO.getAll();
        tableModel = new ApDungKMTableModel(listApDung);
        tableApDung = new JTable(tableModel);
        var scrollTableKM = new JScrollPane(tableApDung);

        // Tạo nút thêm và xóa áp dụng
        var panelButton = new JPanel();
        var btnThem = new JButton("Thêm");
        var btnXoa = new JButton("Xóa");


        // ============================ Chỉnh sửa ============================
        formTitle.setFont(font);
        formTitle.setForeground(Color.BLUE);

        khuyenMaiBox.setLayout(new BoxLayout(khuyenMaiBox, BoxLayout.PAGE_AXIS));
        scrollListKM.setBorder(listKhuyenMaiBorder);
        listKMModel.addAll(KhuyenMaiDAO.getAllKhuyenMai());

        listKhuyenMai.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        sanPhamBox.setLayout(new BoxLayout(sanPhamBox, BoxLayout.PAGE_AXIS));
        scrollListSP.setBorder(listSanPhamBorder);
        listSPModel.addAll(SanPhamDAO.getAllSanPham());


        // ============================ Thêm vào frame ============================
        khuyenMaiBox.add(scrollListKM);
        khuyenMaiBox.add(searchKM);
        khuyenMaiBox.add(resetKM);
        khuyenMaiBox.add(new Box.Filler(new Dimension(listKhuyenMai.getWidth(), 0), new Dimension(listKhuyenMai.getWidth(), 100), new Dimension(listKhuyenMai.getWidth(), Short.MAX_VALUE)));

        sanPhamBox.add(scrollListSP);
        sanPhamBox.add(searchSP);
        sanPhamBox.add(resetSP);
        sanPhamBox.add(new Box.Filler(new Dimension(listKhuyenMai.getWidth(), 0), new Dimension(listKhuyenMai.getWidth(), 100), new Dimension(listKhuyenMai.getWidth(), Short.MAX_VALUE)));
        
        panelButton.add(btnThem);
        panelButton.add(btnXoa);

        gridLayout.add(khuyenMaiBox);
        gridLayout.add(sanPhamBox);


        add(formTitle, BorderLayout.NORTH);
        add(gridLayout, BorderLayout.WEST);
        add(scrollTableKM, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);


        // ============================ Hiển thị lên màn hình ============================
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        // ============================ Chức năng ============================
        searchKM.addActionListener(e -> timKhuyenMai());
        searchSP.addActionListener(e -> timSanPham());
        resetKM.addActionListener(e -> resetKM());
        resetSP.addActionListener(e -> resetSP());

        btnThem.addActionListener(e -> themApDung());
        btnXoa.addActionListener(e -> xoaApDung());
    }

    private void timKhuyenMai() {
        String keyword = JOptionPane.showInputDialog("Nhập từ khóa cần tìm:");

        List<KhuyenMai> result = KhuyenMaiDAO.timKiemKhuyenMai(keyword);

        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy kết quả nào giống với từ khóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        listKMModel.clear();
        listKMModel.addAll(result);
    }

    private void timSanPham() {
        String keyword = JOptionPane.showInputDialog("Nhập từ khóa cần tìm:");

        List<SanPham> result = SanPhamDAO.timKiemSanPham(keyword);

        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy kết quả nào giống với từ khóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        listSPModel.clear();
        listSPModel.addAll(result);
    }

    private void resetKM() {
        listKMModel.clear();
        listKMModel.addAll(KhuyenMaiDAO.getAllKhuyenMai());
    }

    private void resetSP() {
        listSPModel.clear();
        listSPModel.addAll(SanPhamDAO.getAllSanPham());
    }

    private void themApDung() {
        List<ApDungKhuyenMai> exist = new ArrayList<>();
        KhuyenMai km = listKhuyenMai.getSelectedValue();
        List<SanPham> listSP = listSanPham.getSelectedValuesList();

        if (km == null) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn khuyến mãi nào cả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (listSP.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn sản phẩm nào cả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        for (SanPham sanPham : listSP) {
            var apDung = new ApDungKhuyenMai(km.getMaKhM(), km.getTenKhM(), sanPham.getMaSP(), sanPham.getTenSP());
            
            if (ApDungKMDAO.themApDung(apDung)) {
                tableModel.add(apDung);
            } else {
                exist.add(apDung);
            }
        }

        if (!exist.isEmpty()) {
            String notification = "Những khuyến mãi sau đã tồn tại:\n";
            for (ApDungKhuyenMai apDung : exist) {
                notification = notification.concat(String.format("%s - %s\n", apDung.getTenKhM(), apDung.getTenSP()));
            }
            JOptionPane.showMessageDialog(null, notification, "Chú ý", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void xoaApDung() {
        int[] selected = tableApDung.getSelectedRows();
        
        if (selected.length == 0) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn hàng nào để xóa cả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }


        for (int i= selected.length-1; i>=0; i--) {
            var apDung = tableModel.getRow(i);

            if (ApDungKMDAO.xoaApDung(apDung)) {
                tableModel.delete(i);
            }
        }

        tableApDung.clearSelection();
    }
}
