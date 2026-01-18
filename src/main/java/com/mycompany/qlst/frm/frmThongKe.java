package com.mycompany.qlst.frm;

import com.mycompany.qlst.dao.ThongKeDAO;
import com.mycompany.qlst.model.DefaultMenuBar;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

public class frmThongKe extends JFrame {
    private JTable thongKeTable;
    private DefaultTableModel tableModel;
    private JTextField txtTuNgay, txtDenNgay, txtNam;
    private JLabel lblTongQuan, lblDoanhThu;
    private JList<String> listLoaiThongKe;
    private DefaultListModel<String> loaiThongKeListModel;
    
    // DAO
    private ThongKeDAO thongKeDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public frmThongKe() {
        super("Thống kê");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Tạo MenuBar
        var menuBar = DefaultMenuBar.createMenuBar(this);
        setJMenuBar(menuBar);
        
        // Khởi tạo DAO
        thongKeDAO = new ThongKeDAO();

        JLabel lbTitle = new JLabel("THỐNG KÊ & BÁO CÁO", JLabel.CENTER);
        lbTitle.setForeground(Color.blue);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
        add(lbTitle, BorderLayout.PAGE_START);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        //Panel Left
        JPanel pnLeft = new JPanel(new BorderLayout());
        loaiThongKeListModel = new DefaultListModel<>();
        loaiThongKeListModel.addElement("Tổng quan");
        loaiThongKeListModel.addElement("Doanh thu theo tháng");
        loaiThongKeListModel.addElement("Sản phẩm bán chạy");
        loaiThongKeListModel.addElement("Tồn kho thấp");
        loaiThongKeListModel.addElement("Theo danh mục");
        loaiThongKeListModel.addElement("Theo nhân viên");
        loaiThongKeListModel.addElement("Theo khoảng thời gian");
        
        listLoaiThongKe = new JList<>(loaiThongKeListModel);
        JScrollPane scrollPane = new JScrollPane(listLoaiThongKe);
        scrollPane.setBorder(new TitledBorder(border, "Loại thống kê"));

        pnLeft.add(scrollPane, BorderLayout.CENTER);

        //Panel Right
        JPanel pnRight = new JPanel(new BorderLayout());

        // Panel tổng quan
        JPanel pnTongQuan = new JPanel(new GridLayout(2, 1, 5, 5));
        pnTongQuan.setBorder(new TitledBorder(border, "Thống kê tổng quan"));
        
        lblTongQuan = new JLabel("<html><b>Tổng quan hệ thống</b></html>");
        lblTongQuan.setFont(new Font("Arial", Font.PLAIN, 14));
        
        lblDoanhThu = new JLabel("<html><b>Tổng doanh thu: 0 VNĐ</b></html>");
        lblDoanhThu.setFont(new Font("Arial", Font.BOLD, 16));
        lblDoanhThu.setForeground(Color.RED);
        
        pnTongQuan.add(lblTongQuan);
        pnTongQuan.add(lblDoanhThu);

        pnRight.add(pnTongQuan, BorderLayout.PAGE_START);

        // Panel bảng thống kê
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Chi tiết thống kê"));

        String[] columns = {"STT", "Tên", "Giá trị"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        thongKeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(thongKeTable);
        tablePanel.add(tableScrollPane);
        
        pnRight.add(tablePanel, BorderLayout.CENTER);

        // Panel input
        JPanel pnInput = new JPanel(new GridLayout(3, 2, 5, 5));
        pnInput.setBorder(new TitledBorder(border, "Tham số thống kê"));
        
        JLabel lbTuNgay = new JLabel("Từ ngày (yyyy-MM-dd):");
        JLabel lbDenNgay = new JLabel("Đến ngày (yyyy-MM-dd):");
        JLabel lbNam = new JLabel("Năm:");

        txtTuNgay = new JTextField(20);
        txtDenNgay = new JTextField(20);
        txtNam = new JTextField(20);
        txtNam.setText(String.valueOf(java.time.Year.now().getValue()));

        pnInput.add(lbTuNgay);
        pnInput.add(txtTuNgay);
        pnInput.add(lbDenNgay);
        pnInput.add(txtDenNgay);
        pnInput.add(lbNam);
        pnInput.add(txtNam);

        pnRight.add(pnInput, BorderLayout.PAGE_END);

        //JSplitPane
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRight);
        jSplitPane.setDividerLocation(250);
        add(jSplitPane);

        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Load dữ liệu ban đầu
        thongKeTongQuan();

        // Event khi chọn loại thống kê
        listLoaiThongKe.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = listLoaiThongKe.getSelectedValue();
                if (selected != null) {
                    switch (selected) {
                        case "Tổng quan":
                            thongKeTongQuan();
                            break;
                        case "Doanh thu theo tháng":
                            thongKeDoanhThuTheoThang();
                            break;
                        case "Sản phẩm bán chạy":
                            thongKeSanPhamBanChay();
                            break;
                        case "Tồn kho thấp":
                            thongKeTonKho();
                            break;
                        case "Theo danh mục":
                            thongKeTheoDanhMuc();
                            break;
                        case "Theo nhân viên":
                            thongKeTheoNhanVien();
                            break;
                        case "Theo khoảng thời gian":
                            thongKeTheoKhoangThoiGian();
                            break;
                    }
                }
            }
        });
    }

    // Thống kê tổng quan
    private void thongKeTongQuan() {
        tableModel.setRowCount(0);
        Map<String, Integer> data = thongKeDAO.thongKeTongQuan();
        
        String html = "<html><b>Thống kê tổng quan hệ thống:</b><br>" +
                      "• Số sản phẩm: " + data.getOrDefault("soSanPham", 0) + "<br>" +
                      "• Số khách hàng: " + data.getOrDefault("soKhachHang", 0) + "<br>" +
                      "• Số hóa đơn: " + data.getOrDefault("soHoaDon", 0) + "<br>" +
                      "• Số nhân viên: " + data.getOrDefault("soNhanVien", 0) + "</html>";
        lblTongQuan.setText(html);
        
        // Hiển thị tổng doanh thu
        long tongDoanhThu = thongKeDAO.tongDoanhThu();
        lblDoanhThu.setText("<html><b>Tổng doanh thu: " + currencyFormat.format(tongDoanhThu) + "</b></html>");
        
        // Hiển thị trong bảng
        int stt = 1;
        tableModel.addRow(new Object[]{stt++, "Số sản phẩm", data.getOrDefault("soSanPham", 0)});
        tableModel.addRow(new Object[]{stt++, "Số khách hàng", data.getOrDefault("soKhachHang", 0)});
        tableModel.addRow(new Object[]{stt++, "Số hóa đơn", data.getOrDefault("soHoaDon", 0)});
        tableModel.addRow(new Object[]{stt++, "Số nhân viên", data.getOrDefault("soNhanVien", 0)});
        tableModel.addRow(new Object[]{stt++, "Tổng doanh thu", currencyFormat.format(tongDoanhThu)});
    }

    // Thống kê doanh thu theo tháng
    private void thongKeDoanhThuTheoThang() {
        tableModel.setRowCount(0);
        
        int nam;
        try {
            nam = Integer.parseInt(txtNam.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Năm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Map<String, Long> data = thongKeDAO.thongKeDoanhThuTheoThang(nam);
        
        lblTongQuan.setText("<html><b>Doanh thu theo tháng năm " + nam + "</b></html>");
        
        long tongDoanhThu = 0;
        int stt = 1;
        for (Map.Entry<String, Long> entry : data.entrySet()) {
            tableModel.addRow(new Object[]{
                stt++,
                entry.getKey(),
                currencyFormat.format(entry.getValue())
            });
            tongDoanhThu += entry.getValue();
        }
        
        lblDoanhThu.setText("<html><b>Tổng doanh thu năm " + nam + ": " + currencyFormat.format(tongDoanhThu) + "</b></html>");
    }

    // Thống kê sản phẩm bán chạy
    private void thongKeSanPhamBanChay() {
        tableModel.setRowCount(0);
        Map<String, Integer> data = thongKeDAO.thongKeSanPhamBanChay(10);
        
        lblTongQuan.setText("<html><b>Top 10 sản phẩm bán chạy nhất</b></html>");
        
        int stt = 1;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            tableModel.addRow(new Object[]{
                stt++,
                entry.getKey(),
                entry.getValue() + " sản phẩm"
            });
        }
        
        lblDoanhThu.setText("<html><b>Tổng: " + data.size() + " sản phẩm</b></html>");
    }

    // Thống kê tồn kho thấp
    private void thongKeTonKho() {
        tableModel.setRowCount(0);
        Map<String, Integer> data = thongKeDAO.thongKeTonKho();
        
        lblTongQuan.setText("<html><b>10 sản phẩm tồn kho thấp nhất</b></html>");
        
        int stt = 1;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            tableModel.addRow(new Object[]{
                stt++,
                entry.getKey(),
                entry.getValue() + " sản phẩm"
            });
        }
        
        lblDoanhThu.setText("<html><b>Cần nhập thêm hàng!</b></html>");
    }

    // Thống kê theo danh mục
    private void thongKeTheoDanhMuc() {
        tableModel.setRowCount(0);
        Map<String, Integer> data = thongKeDAO.thongKeTheoDanhMuc();
        
        lblTongQuan.setText("<html><b>Số lượng sản phẩm theo danh mục</b></html>");
        
        int stt = 1;
        int tongSP = 0;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            tableModel.addRow(new Object[]{
                stt++,
                entry.getKey(),
                entry.getValue() + " sản phẩm"
            });
            tongSP += entry.getValue();
        }
        
        lblDoanhThu.setText("<html><b>Tổng: " + tongSP + " sản phẩm</b></html>");
    }

    // Thống kê theo nhân viên
    private void thongKeTheoNhanVien() {
        tableModel.setRowCount(0);
        Map<String, Long> data = thongKeDAO.thongKeDoanhThuNhanVien();
        
        lblTongQuan.setText("<html><b>Doanh thu theo nhân viên</b></html>");
        
        int stt = 1;
        long tongDoanhThu = 0;
        for (Map.Entry<String, Long> entry : data.entrySet()) {
            tableModel.addRow(new Object[]{
                stt++,
                entry.getKey(),
                currencyFormat.format(entry.getValue())
            });
            tongDoanhThu += entry.getValue();
        }
        
        lblDoanhThu.setText("<html><b>Tổng doanh thu: " + currencyFormat.format(tongDoanhThu) + "</b></html>");
    }

    // Thống kê theo khoảng thời gian
    private void thongKeTheoKhoangThoiGian() {
        if (txtTuNgay.getText().trim().isEmpty() || txtDenNgay.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ khoảng thời gian!");
            return;
        }
        
        try {
            java.util.Date tuNgay = dateFormat.parse(txtTuNgay.getText().trim());
            java.util.Date denNgay = dateFormat.parse(txtDenNgay.getText().trim());
            
            if (denNgay.before(tuNgay)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày bắt đầu!");
                return;
            }
            
            long doanhThu = thongKeDAO.doanhThuTheoKhoangThoiGian(
                new Date(tuNgay.getTime()),
                new Date(denNgay.getTime())
            );
            
            tableModel.setRowCount(0);
            lblTongQuan.setText("<html><b>Doanh thu từ " + txtTuNgay.getText() + " đến " + txtDenNgay.getText() + "</b></html>");
            
            tableModel.addRow(new Object[]{1, "Từ ngày", txtTuNgay.getText()});
            tableModel.addRow(new Object[]{2, "Đến ngày", txtDenNgay.getText()});
            tableModel.addRow(new Object[]{3, "Doanh thu", currencyFormat.format(doanhThu)});
            
            lblDoanhThu.setText("<html><b>Doanh thu: " + currencyFormat.format(doanhThu) + "</b></html>");
            
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ! (yyyy-MM-dd)", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmThongKe frm = new frmThongKe();
            frm.setVisible(true);
        });
    }
}