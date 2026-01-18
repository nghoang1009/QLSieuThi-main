package com.mycompany.qlst.frm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.mycompany.qlst.dao.KhuyenMaiDAO;
import com.mycompany.qlst.model.DefaultMenuBar;
import com.mycompany.qlst.model.KhuyenMai;
import com.mycompany.qlst.model.khuyenMaiTableModel;

public class frmKhuyenMai extends JFrame {
    private List<KhuyenMai> listKhuyenMai;
    private khuyenMaiTableModel tableModel;
    private KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();

    private JTextField tfMaKM, tfTenKM, tfPhanTramGiam, tfDayNHL, tfMonthNHL, tfYearNHL, tfDayNKT, tfMonthNKT, tfYearNKT;

    public frmKhuyenMai(String title) {
        super(title);

        // Tạo MenuBar
        var menuBar = DefaultMenuBar.createMenuBar(this);
        setJMenuBar(menuBar);

        var tabs = new JTabbedPane();
        var tabKM = new JPanel(new BorderLayout());
        var tabApDung = new JPanel(new BorderLayout());
        
        // ============ Khuyến mãi ============
        tabs.addTab("Mã giảm giá", tabKM);
        tabs.addTab("Áp dụng", tabApDung);

        var titleKM = new JLabel("QUẢN LÝ MÃ GIẢM GIÁ", JLabel.CENTER);
        var font = new Font("Arial", Font.BOLD, 20);

        listKhuyenMai = new ArrayList<>();
        tableModel = new khuyenMaiTableModel(listKhuyenMai);
        loadAllKM();

        var tableKM = new JTable(tableModel);
        var scrollTableKM = new JScrollPane(tableKM);

        var eastLayout = new JPanel(new BorderLayout());
        var pnKMTextField = new JPanel(new GridBagLayout());
        var lblMaKM = new JLabel("Mã khuyến mãi:");
        var lblTenKM = new JLabel("Tên khuyến mãi:");
        var lblPhanTramGiam = new JLabel("Phần trăm giảm(%):");
        var lblNgayHieuLuc = new JLabel("Ngày hiệu lực:");
        var lblNgayKetThuc = new JLabel("Ngày kết thúc:");

        tfMaKM = new JTextField(20);
        tfTenKM = new JTextField(20);
        tfPhanTramGiam = new JTextField(20);

        var pnNgayHieuLuc = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tfDayNHL = new JTextField(2);
        tfMonthNHL = new JTextField(2);
        tfYearNHL = new JTextField(4);

        var pnNgayKetThuc = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tfDayNKT = new JTextField(2);
        tfMonthNKT = new JTextField(2);
        tfYearNKT = new JTextField(4);

        var pnKMButton = new JPanel();
        var btnAddKM = new JButton("Thêm");
        var btnEditKM = new JButton("Sửa");
        var btnDeleteKM = new JButton("Xóa");
        var btnClearKM = new JButton("Clear");

        // ============ Áp dụng ============
        


        // ============ Chỉnh sửa các thành phần ============
        tfMaKM.setEditable(false);

        tableKM.setFillsViewportHeight(true);
        titleKM.setFont(font);
        titleKM.setForeground(Color.BLUE);

        tableKM.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // ============ Hiển thị lên màn hình ============
        // ============ Khuyến mãi ============
        tabKM.add(titleKM, BorderLayout.NORTH);
        tabKM.add(scrollTableKM, BorderLayout.CENTER);
        tabKM.add(eastLayout, BorderLayout.EAST);

        pnKMButton.add(btnAddKM);
        pnKMButton.add(btnEditKM);
        pnKMButton.add(btnDeleteKM);
        pnKMButton.add(btnClearKM);
        eastLayout.add(pnKMButton, BorderLayout.SOUTH);

        pnNgayHieuLuc.add(tfDayNHL);
        pnNgayHieuLuc.add(new JLabel(" / "));
        pnNgayHieuLuc.add(tfMonthNHL);
        pnNgayHieuLuc.add(new JLabel(" / "));
        pnNgayHieuLuc.add(tfYearNHL);

        pnNgayKetThuc.add(tfDayNKT);
        pnNgayKetThuc.add(new JLabel(" / "));
        pnNgayKetThuc.add(tfMonthNKT);
        pnNgayKetThuc.add(new JLabel(" / "));
        pnNgayKetThuc.add(tfYearNKT);

        var c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0; c.gridy = 0; pnKMTextField.add(lblMaKM, c);
                     c.gridy = 1; pnKMTextField.add(lblTenKM, c);
                     c.gridy = 2; pnKMTextField.add(lblPhanTramGiam, c);
                     c.gridy = 3; pnKMTextField.add(lblNgayHieuLuc, c);
                     c.gridy = 4; pnKMTextField.add(lblNgayKetThuc, c);

        c.gridx = 1; c.gridy = 0; pnKMTextField.add(tfMaKM, c);
                     c.gridy = 1; pnKMTextField.add(tfTenKM, c);
                     c.gridy = 2; pnKMTextField.add(tfPhanTramGiam, c);
                     c.gridy = 3; pnKMTextField.add(pnNgayHieuLuc, c);
                     c.gridy = 4; pnKMTextField.add(pnNgayKetThuc, c);
        eastLayout.add(pnKMTextField);


        add(tabs);
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);

        // ============ Chức năng ============
        tableKM.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting())
                return;
            loadTextField(listKhuyenMai.get(tableKM.getSelectedRow()));
        });

        btnAddKM.addActionListener(e -> themKhuyenMai());
        btnDeleteKM.addActionListener(e -> xoaKhuyenMai(tableKM.getSelectedRow()));
        btnClearKM.addActionListener(e -> clearTextField());
    }

    private void loadAllKM() {
        tableModel.addAll(khuyenMaiDAO.getAllKhuyenMai());
    }

    private void loadTextField(KhuyenMai khuyenMai) {
        tfMaKM.setText(khuyenMai.getMaKhM().toString());
        tfTenKM.setText(khuyenMai.getTenKhM());
        tfPhanTramGiam.setText(khuyenMai.getPhanTramGiam().toString());

        Calendar cal = Calendar.getInstance();
        cal.setTime(khuyenMai.getNgayHieuLuc());

        tfDayNHL.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        tfMonthNHL.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
        tfYearNHL.setText(String.valueOf(cal.get(Calendar.YEAR)));
        
        cal.setTime(khuyenMai.getNgayKetThuc());
        tfDayNKT.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        tfMonthNKT.setText(String.valueOf(cal.get(Calendar.MONTH)+1));
        tfYearNKT.setText(String.valueOf(cal.get(Calendar.YEAR)));
    }

    private KhuyenMai getTextField() {
        int maKM = Integer.parseInt(tfMaKM.getText());
        String tenKM = tfTenKM.getText();

        if (tenKM.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tên khuyến mãi không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            tfTenKM.requestFocus();
            return null;
        }

        int phanTramGiam;
        try {
            phanTramGiam = Integer.parseInt(tfPhanTramGiam.getText());
            if (phanTramGiam >= 100 || phanTramGiam <= 0) {
                JOptionPane.showMessageDialog(null, "Phần trăm phải trong khoảng từ 1 đến 99", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Sai định dạng phần trăm", "Lỗi", JOptionPane.ERROR_MESSAGE);
            tfPhanTramGiam.requestFocus();
            tfPhanTramGiam.selectAll();
            return null;
        }

        Date ngayHieuLuc;
        String day = tfDayNHL.getText();
        String month = tfMonthNHL.getText();
        String year = tfYearNHL.getText();

        try {
            ngayHieuLuc = Date.valueOf(String.format("%s-%s-%s", year, month, day));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Sai định dạng ngày tháng!");
            tfDayNHL.setText("");
            tfMonthNHL.setText("");
            tfYearNHL.setText("");
            tfDayNHL.requestFocus();
            return null;
        }

        Date ngayKetThuc;
        day = tfDayNKT.getText();
        month = tfMonthNKT.getText();
        year = tfYearNKT.getText();

        try {
            ngayKetThuc = Date.valueOf(String.format("%s-%s-%s", year, month, day));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Sai định dạng ngày tháng!");
            tfDayNKT.setText("");
            tfMonthNKT.setText("");
            tfYearNKT.setText("");
            tfDayNKT.requestFocus();
            return null;
        }

        return new KhuyenMai(maKM, tenKM, phanTramGiam, ngayHieuLuc, ngayKetThuc);
    }

    private void clearTextField() {
        tfMaKM.setText("");
        tfTenKM.setText("");
        tfPhanTramGiam.setText("");
        tfDayNHL.setText("");
        tfMonthNHL.setText("");
        tfYearNHL.setText("");
        tfDayNKT.setText("");
        tfMonthNKT.setText("");
        tfYearNKT.setText("");
    }

    private void themKhuyenMai() {
        KhuyenMai khuyenMai = getTextField();
        if (khuyenMai == null) return;
        int key = khuyenMaiDAO.themKhuyenMai(khuyenMai);

        if (key != -1) {
            khuyenMai.setMaKhM(key);
            tableModel.addRow(khuyenMai);
            System.out.println(listKhuyenMai.size());
            clearTextField();

            JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!");
        }
    }

    private void xoaKhuyenMai(int selectedRow) {
        KhuyenMai khuyenMai = getTextField();
        if (khuyenMai == null || selectedRow == -1) return;

        if (khuyenMaiDAO.xoaKhuyenMai(khuyenMai.getMaKhM())) {
            tableModel.deleteRow(selectedRow);
            clearTextField();
            JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công!");
        }
    }

    public static void main(String[] args) {
        new frmKhuyenMai("Test");
    }
}
