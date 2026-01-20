package com.mycompany.qlst.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ApplyKMTableModel extends AbstractTableModel {
    private String columns[] = {"Mã khuyến mãi", "Tên khuyến mãi", "Phần trăm giảm(%)", "Ngày hiệu lực", "Ngày kết thúc"};
    private List<ApDungKhuyenMai> data;

    public int getColumnCount() {
        return columns.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }
}
