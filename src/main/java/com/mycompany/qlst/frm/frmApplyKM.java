package com.mycompany.qlst.frm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import com.mycompany.qlst.model.DefaultMenuBar;

public class frmApplyKM extends JFrame {
    public frmApplyKM(String title) {
        super(title);

        // Tạo MenuBar
        var menuBar = DefaultMenuBar.createMenuBar(this);
        setJMenuBar(menuBar);
        
        var font = new Font("Arial", Font.BOLD, 20);

        var titleApDung = new JLabel("ÁP DỤNG MÃ GIẢM GIÁ", JLabel.CENTER);
        
        var split = new JSplitPane();
        var pnChon = new JPanel(new GridBagLayout());
        var titleTenKM = new JLabel("Tên khuyến mãi");
        var titleTenSP = new JLabel("Tên sản phẩm");

        var listKM = new JList<>();
        var scrollListKM = new JScrollPane(listKM);
        var listSP = new JList<>();
        var scrollListSP = new JScrollPane(listSP);

        var tableApDung = new JTable();
        var scrollTableApDung = new JScrollPane(tableApDung);

        var pnApDungButton = new JPanel();
        var btnAddApDung = new JButton("Thêm");
        var btnDeleteApDung = new JButton("Xóa");
        var btnReloadApDung = new JButton("Tải lại");


        titleApDung.setFont(font);
        titleApDung.setForeground(Color.BLUE);

        scrollListKM.setPreferredSize(new Dimension(100, 380));
        scrollListSP.setPreferredSize(new Dimension(100, 380));


        this.add(titleApDung, BorderLayout.NORTH);

        var c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; pnChon.add(titleTenKM, c);
        c.gridx = 1;              pnChon.add(titleTenSP, c);
        c.gridx = 0; c.gridy = 1; pnChon.add(scrollListKM, c);
        c.gridx = 1;              pnChon.add(scrollListSP, c);
        split.setLeftComponent(pnChon);

        split.setRightComponent(scrollTableApDung);
        this.add(split, BorderLayout.CENTER);

        pnApDungButton.add(btnAddApDung);
        pnApDungButton.add(btnDeleteApDung);
        pnApDungButton.add(btnReloadApDung);
        this.add(pnApDungButton, BorderLayout.SOUTH);

        setSize(900, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
