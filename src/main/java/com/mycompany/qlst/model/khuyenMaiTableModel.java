package com.mycompany.qlst.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class khuyenMaiTableModel extends AbstractTableModel {
    private String columns[] = {"Mã khuyến mãi", "Tên khuyến mãi", "Phần trăm giảm(%)", "Ngày hiệu lực", "Ngày kết thúc"};
    private List<KhuyenMai> data;
    
    public khuyenMaiTableModel(List<KhuyenMai> data) {
        this.data = data;
    }

    public int getColumnCount() { return columns.length; }
    public int getRowCount() { return data.size(); }

    public String getColumnName(int col) {
        return columns[col];
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    public KhuyenMai getRow(int rowIndex) {
        return data.get(rowIndex);
    }
    
    public void addRow(KhuyenMai khuyenMai) {
        data.add(khuyenMai);
        fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
    }

    public void deleteRow(int index) {
        data.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public void addAll(List<KhuyenMai> listKhuyenMai) {
        int firstRow = getRowCount();
        for (KhuyenMai khuyenMai : listKhuyenMai) {
            data.add(khuyenMai);
        }

        fireTableRowsInserted(firstRow, getRowCount()-1);
    }

    public void notifyExternalAdd(int firstRow, int lastRow) {
        fireTableRowsInserted(firstRow, lastRow);
    }
}
