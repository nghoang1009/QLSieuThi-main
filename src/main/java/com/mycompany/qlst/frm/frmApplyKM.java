package com.mycompany.qlst.frm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.mycompany.qlst.dao.KhuyenMaiDAO;
import com.mycompany.qlst.dao.SanPhamDAO;
import com.mycompany.qlst.model.DefaultMenuBar;
import com.mycompany.qlst.model.KhuyenMai;
import com.mycompany.qlst.model.SanPham;

public class frmApplyKM extends JFrame {
    private DefaultListModel<KhuyenMai> listKMModel;
    private DefaultListModel<SanPham> listSPModel;
    private DefaultTableModel

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

        var listKhuyenMai = new JList<>(listKMModel);
        var scrollListKM = new JScrollPane(listKhuyenMai);

        var listSanPham = new JList<>(listSPModel);
        var scrollListSP = new JScrollPane(listSanPham);

        var searchKM = new JButton("Tìm khuyến mãi");
        var searchSP = new JButton("Tìm sản phẩm");        


        // ============================ Chỉnh sửa ============================
        formTitle.setFont(font);
        formTitle.setForeground(Color.BLUE);

        khuyenMaiBox.setLayout(new BoxLayout(khuyenMaiBox, BoxLayout.PAGE_AXIS));
        scrollListKM.setBorder(listKhuyenMaiBorder);
        listKMModel.addAll(KhuyenMaiDAO.getAllKhuyenMai());

        sanPhamBox.setLayout(new BoxLayout(sanPhamBox, BoxLayout.PAGE_AXIS));
        scrollListSP.setBorder(listSanPhamBorder);
        listSPModel.addAll(SanPhamDAO.getAllSanPham());


        // ============================ Thêm vào frame ============================
        khuyenMaiBox.add(scrollListKM);
        khuyenMaiBox.add(searchKM);
        khuyenMaiBox.add(new Box.Filler(new Dimension(listKhuyenMai.getWidth(), 0), new Dimension(listKhuyenMai.getWidth(), 100), new Dimension(listKhuyenMai.getWidth(), Short.MAX_VALUE)));

        sanPhamBox.add(scrollListSP);
        sanPhamBox.add(searchSP);
        sanPhamBox.add(new Box.Filler(new Dimension(listKhuyenMai.getWidth(), 0), new Dimension(listKhuyenMai.getWidth(), 100), new Dimension(listKhuyenMai.getWidth(), Short.MAX_VALUE)));
        

        gridLayout.add(khuyenMaiBox);
        gridLayout.add(sanPhamBox);


        add(formTitle, BorderLayout.NORTH);
        add(gridLayout, BorderLayout.WEST);


        // ============================ Hiển thị lên màn hình ============================
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        // ============================ Chức năng ============================
        searchKM.addActionListener(e -> timKhuyenMai());
        searchSP.addActionListener(e -> timSanPham());
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
}
