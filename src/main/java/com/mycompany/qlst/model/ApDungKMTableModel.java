package com.mycompany.qlst.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ApDungKMTableModel extends AbstractTableModel {
    private String columns[] = {"Mã khuyến mãi", "Tên khuyến mãi", "Mã sản phẩm", "Tên sản phẩm"};
    private List<ApDungKhuyenMai> data;

    public ApDungKMTableModel(List<ApDungKhuyenMai> data) {
        this.data = data;
    }
    
    public int getColumnCount() {
        return columns.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columns[col];
    }

    public ApDungKhuyenMai getRow(int index) {
        return data.get(index);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    public void add(ApDungKhuyenMai km) {
        data.add(km);
        fireTableRowsInserted(getRowCount(), getRowCount());
    }

    public void delete(int index) {
        data.remove(index);
        fireTableRowsDeleted(index, index);
    }
}
